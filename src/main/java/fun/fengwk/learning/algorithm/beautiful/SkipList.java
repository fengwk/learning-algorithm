package fun.fengwk.learning.algorithm.beautiful;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

/**
 * 17.跳表
 *
 * @author fengwk
 */
public class SkipList<K extends Comparable<K>, V> {

    class Node {
        // 如果key为null表示无穷小的哨兵
        K key;
        V val;
        Node next;

        Node(K key, V val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return String.format("(%s,%s)", key, val);
        }
    }

    class IndexNode extends Node {

        Node down;

        IndexNode(K key, V val, Node next, Node down) {
            super(key, val, next);
            this.down = down;
        }
    }

    private final int maxLevel;
    private Node head;
    private Random random;
    private int size;

    public SkipList(int maxLevel, long seed) {
        if (maxLevel < 1) {
            throw new IllegalArgumentException();
        }

        // 构建所有层级的虚拟头节点
        IndexNode dummy = new IndexNode(null, null, null, null);
        IndexNode prevHead = dummy;
        for (int l = maxLevel-1; l >= 0; l--) {
            if (l == 0) {
                Node curHead = new Node(null, null, null);
                prevHead.down = curHead;
            } else {
                IndexNode curHead = new IndexNode(null, null, null, null);
                prevHead.down = curHead;
                prevHead = curHead;
            }
        }

        this.maxLevel = maxLevel;
        this.head = dummy.down;
        this.random = new Random(seed);
    }

    public int size() {
        return size;
    }

    /**
     * 通过键查找
     *
     * @param key not null
     * @return
     */
    public V get(K key) {
        Node curFrom = head;
        Node curFound;

        while ((curFound = findFloor(curFrom, key)) instanceof SkipList.IndexNode) {
            curFrom = ((IndexNode) curFound).down;
        }

        if (compare(curFound.key, key) == 0) {
            return curFound.val;
        }

        return null;
    }

    /**
     * 向跳表中设置键值对
     *
     * @param key not null
     * @param val not null
     * @return 如果原先有值返回原先的值
     */
    public V put(K key, V val) {
        LinkedList<Node> foundStack = findChainStack(key, this::findFloor);

        Node found = foundStack.peek();
        if (compare(found.key, key) == 0) {
            V oldVal = found.val;
            found.val = val;
            return oldVal;
        }

        size++;
        int level = random.nextInt(maxLevel);
        Node down = null;
        for (int l = 0; l <= level; l++) {
            if (l == 0) {
                Node node = foundStack.pop();
                Node next = node.next;
                Node newNode = new Node(key, val, next);
                node.next = newNode;
                down = newNode;
            } else {
                IndexNode node = (IndexNode) foundStack.pop();
                IndexNode next = (IndexNode) node.next;
                IndexNode newNode = new IndexNode(key, val, next, down);
                node.next = newNode;
                down = newNode;
            }
        }

        return null;
    }

    /**
     * 删除指定key对应的节点并返回被删除的值，如果不存在返回null
     *
     * @param key
     * @return
     */
    public V remove(K key) {
        LinkedList<Node> foundStack = findChainStack(key, this::findFloor);

        // foundStack不会为空
        if (foundStack.isEmpty()) {
            throw new AssertionError();
        }

        if (compare(foundStack.peek().key, key) < 0) {
            return null;
        }

        size--;
        V retVal = foundStack.peek().val;

        LinkedList<Node> prevStack = findChainStack(key, this::findMaxLessThan);

        // 两者应当相等
        if (foundStack.size() != prevStack.size()) {
            throw new AssertionError();
        }

        while (!foundStack.isEmpty()) {
            Node node = foundStack.pop();
            if (compare(node.key, key) < 0) {
                break;
            }

            Node next = node.next;
            Node prev = prevStack.pop();
            prev.next = next;
        }

        return retVal;
    }

    private LinkedList<Node> findChainStack(K key, BiFunction<Node, K, Node> findFunction) {
        Node curFrom = head;
        Node curFound;
        // 元素查找列表
        LinkedList<Node> foundStack = new LinkedList<>();

        while ((curFound = findFunction.apply(curFrom, key)) instanceof SkipList.IndexNode) {
            foundStack.push(curFound);
            curFrom = ((IndexNode) curFound).down;
        }
        foundStack.push(curFound);

        return foundStack;
    }

    // 找到等于target或小于target但最接近的节点
    private Node findFloor(Node cur, K target) {

        while (cur.next != null && compare(cur.next.key, target) <= 0) {
            cur = cur.next;
        }
        return cur;
    }

    // 找到小于target但最接近的节点
    private Node findMaxLessThan(Node cur, K target) {

        while (cur.next != null && compare(cur.next.key, target) < 0) {
            cur = cur.next;
        }
        return cur;
    }

    private int compare(K k1, K k2) {
        if (k1 == null && k2 == null) {
            return 0;
        }

        if (k1 == null) {
            return -1;
        }

        if (k2 == null) {
            return 1;
        }

        return k1.compareTo(k2);
    }

    @Override
    public String toString() {
        List<Node> levels = new ArrayList<>(maxLevel);
        for (int i = 0; i < maxLevel; i++) {
            levels.add(null);
        }
        Node curHead = head;
        for (int l = maxLevel-1; l >= 0; l--) {
            levels.set(l, curHead);
            if (l > 0) {
                curHead = ((IndexNode) curHead).down;
            }
        }

        List<List<String>> tab = new ArrayList<>();
        for (int l = 0; l < maxLevel; l++) {
            tab.add(new ArrayList<>());
        }

        while (levels.get(0) != null) {
            K level0Key = levels.get(0).key;
            for (int l = 0; l < maxLevel; l++) {
                Node node = levels.get(l);
                if (level0Key == null) {
                    tab.get(l).add(String.format("%d Level:", l));
                    levels.set(l, node.next);
                } else {
                    if (node != null && compare(node.key, level0Key) == 0) {
                        tab.get(l).add(String.format("\t(%s,%s)", level0Key, node.val));
                        levels.set(l, node.next);
                    } else {
                        tab.get(l).add("\t\t");
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int l = maxLevel-1; l>=0; l--) {
            List<String> line = tab.get(l);
            for (String item : line) {
                sb.append(item);
            }
            if (l > 0) {
                sb.append('\n');
            }
        }

        return sb.toString();
    }
}
