package fun.fengwk.learning.algorithm.djh.brtree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author fengwk
 */
public class BTree<K extends Comparable<K>, V> {

    class Node {

        ArrayList<K> keys;
        ArrayList<V> vals;
        ArrayList<Node> children;

        Node() {
            keys = new ArrayList<>();
            vals = new ArrayList<>();
            children = new ArrayList<>();
        }

    }

    private int order;
    private Node root;
    private int size;

    public BTree(int order) {
        this.order = order;
    }

    public int size() {
        return size;
    }

    public V get(K key) {
        Node cur = root;
        while (cur != null) {
            int i = floor(cur.keys, key);
            if (i >= 0 && cur.keys.get(i).compareTo(key) == 0) {
                return cur.vals.get(i);
            } else {
                cur = cur.children.get(i+1);
            }
        }
        return null;
    }

    // 找到与target相同元素的位置
    // 若不存在相同的元素则找到小于target但索引值最大的元素
    // 所有元素都大于target则返回-1
    private int floor(ArrayList<K> keys, K target) {
        // [l, r)为未查找区间
        // 查找结束使结果满足
        // keys[0, l)   <  target
        // keys[r, len) >=  target
        // l == r
        int l = 0;
        int r = keys.size();

        while (l < r) {
            int m = (r - l) / 2 + l;
            int comp = keys.get(m).compareTo(target);
            if (comp < 0) {
                l = m+1;
            } else {
                r = m;
            }
        }

        return r < keys.size() && keys.get(r).compareTo(target) == 0 ? r : r-1;
    }


    public V put(K key, V val) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(val);

        if (root == null) {
            Node newNode = new Node();
            newNode.keys.add(key);
            newNode.vals.add(val);
            newNode.children.add(null);
            newNode.children.add(null);
            root = newNode;
            size++;
            return null;
        }

        LinkedList<Node> findStack = new LinkedList<>();
        Node cur = root;
        int i = -1;
        while (cur != null) {
            findStack.push(cur);
            i = floor(cur.keys, key);
            if (i >= 0 && cur.keys.get(i).compareTo(key) == 0) {
                V oldVal = cur.vals.get(i);
                cur.keys.set(i, key);
                cur.vals.set(i, val);
                return oldVal;
            } else {
                cur = cur.children.get(i+1);
            }
        }

        cur = findStack.peek();
        cur.keys.add(i+1, key);
        cur.vals.add(i+1, val);
        cur.children.add(null);
        size++;

        resolveOverflow(findStack);

        return null;
    }

    public V remove(K key) {
        Objects.requireNonNull(key);

        if (root == null) {
            return null;
        }

        LinkedList<Node> findStack = new LinkedList<>();
        Node cur = root;
        int i = -1;
        while (cur != null) {
            findStack.push(cur);
            i = floor(cur.keys, key);
            if (i >= 0 && cur.keys.get(i).compareTo(key) == 0) {
                break;
            } else {
                cur = cur.children.get(i+1);
            }
        }

        // 没找到直接返回
        if (cur == null) {
            return null;
        }

        V oldVal = cur.vals.get(i);

        if (cur.children.get(0) != null) {
            // 如果cur不是叶子，将其与后继交换
            Node successor = cur.children.get(i+1);
            while (successor.children.get(0) != null) {
                findStack.push(successor);
                successor = successor.children.get(0);
            }
            findStack.push(successor);

            cur.keys.set(i, successor.keys.get(0));
            cur.vals.set(i, successor.vals.get(0));
            cur = successor;
        }

        cur.keys.remove(i);
        cur.vals.remove(i);
        cur.children.remove(cur.children.size()-1);
        size--;

        resolveUnderflow(findStack);

        return oldVal;
    }

    private void resolveOverflow(LinkedList<Node> findStack) {
        Node cur = findStack.pop();
        while (cur != null && cur.children.size() > order) {
            // [0, m)
            // m
            // [m+1, size)
            int m = order/2;

            List<K> rKeys = cur.keys.subList(m + 1, cur.keys.size());
            List<V> rVals = cur.vals.subList(m + 1, cur.vals.size());
            List<Node> rChildren = cur.children.subList(m+1, cur.children.size());

            Node right = new Node();
            right.keys = new ArrayList<>(rKeys);
            right.vals = new ArrayList<>(rVals);
            right.children = new ArrayList<>(rChildren);

            rKeys.clear();
            rVals.clear();
            rChildren.clear();

            Node p;
            if (findStack.isEmpty()) {
                // 根节点上溢
                p = new Node();
                p.keys.add(cur.keys.get(m));
                p.vals.add(cur.vals.get(m));
                p.children.add(cur);
                p.children.add(right);
                root = p;
            } else {
                p = findStack.pop();
                int i = p.children.indexOf(cur)-1;
                p.keys.add(i+1, cur.keys.get(m));
                p.vals.add(i+1, cur.vals.get(m));
                p.children.add(i+2, right);
            }

            cur.keys.remove(m);
            cur.vals.remove(m);

            cur = p;
        }
    }

    private void resolveUnderflow(LinkedList<Node> findStack) {
        Node cur = findStack.pop();
        while (cur != null && ((cur == root && cur.children.size() < 2) || (cur != root && cur.children.size() < (order+1)/2))) {
            if (cur == root) {
                root = null;
                break;
            }

            Node p = findStack.pop();
            int i = p.children.indexOf(cur)-1;
            Node leftSibling = i >= 0 ? p.children.get(i) : null;
            Node rightSibling = i+2 < p.children.size() ? p.children.get(i+2) : null;

            if (leftSibling != null && leftSibling.children.size() > (order+1)/2) {
                cur.keys.add(0, p.keys.get(i));
                cur.vals.add(0, p.vals.get(i));
                cur.children.add(0, leftSibling.children.remove(leftSibling.children.size()-1));
                p.keys.set(i, leftSibling.keys.remove(leftSibling.keys.size()-1));
                p.vals.set(i, leftSibling.vals.remove(leftSibling.vals.size()-1));
            } else if (rightSibling != null && rightSibling.children.size() > (order+1)/2) {
                cur.keys.add(p.keys.get(i+1));
                cur.vals.add(p.vals.get(i+1));
                cur.children.add(rightSibling.children.remove(0));
                p.keys.set(i+1, rightSibling.keys.remove(0));
                p.vals.set(i+1, rightSibling.vals.remove(0));
            } else if (leftSibling != null) {
                leftSibling.keys.add(p.keys.get(i));
                leftSibling.vals.add(p.vals.get(i));
                leftSibling.keys.addAll(cur.keys);
                leftSibling.vals.addAll(cur.vals);
                leftSibling.children.addAll(cur.children);
                p.keys.remove(i);
                p.vals.remove(i);
                p.children.remove(i+1);
            } else {// rightSibling != null
                cur.keys.add(p.keys.get(i+1));
                cur.vals.add(p.vals.get(i+1));
                cur.keys.addAll(rightSibling.keys);
                cur.vals.addAll(rightSibling.vals);
                cur.children.addAll(rightSibling.children);
                p.keys.remove(i+1);
                p.vals.remove(i+1);
                p.children.remove(i+2);
            }

            if (p == root && p.children.size() < 2) {
                root = cur;
                break;
            } else {
                cur = p;
            }
        }

    }

    @Override
    public String toString() {
        if (root == null) {
            return "EMPTY";
        }

        StringBuilder sb = new StringBuilder();

        LinkedList<Node> queue1 = new LinkedList<>();
        LinkedList<Node> queue2 = new LinkedList<>();
        queue1.offer(root);

        while (!queue1.isEmpty()) {
            Node cur = queue1.poll();
            sb.append('[');
            for (int i = 0; i < cur.keys.size(); i++) {
                sb.append('(').append(cur.keys.get(i))/*.append(',').append(cur.vals.get(i))*/.append(')');
                if (i < cur.keys.size()-1) {
                    sb.append(',');
                }
            }
            sb.append(']');

            if (cur.children.get(0) != null) {
                for (Node child : cur.children) {
                    queue2.add(child);
                }
            }

            if (queue1.isEmpty() && !queue2.isEmpty()) {
                sb.append('\n');
                LinkedList<Node> tmp = queue1;
                queue1 = queue2;
                queue2 = tmp;
            }
        }

        return sb.toString();
    }
}
