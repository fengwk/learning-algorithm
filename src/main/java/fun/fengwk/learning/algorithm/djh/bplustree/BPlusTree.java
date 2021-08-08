package fun.fengwk.learning.algorithm.djh.bplustree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author fengwk
 */
public class BPlusTree<K extends Comparable<K>, V> {


    class Node {

        IndexNode parent;
        ArrayList<K> keys = new ArrayList<>();

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (int i = 0; i < keys.size(); i++) {
                sb.append('(').append(keys.get(i))/*.append(',').append(cur.vals.get(i))*/.append(')');
                if (i < keys.size()-1) {
                    sb.append(',');
                }
            }
            sb.append(']');
            return sb.toString();
        }
    }

    class IndexNode extends Node {

        ArrayList<Node> children = new ArrayList<>();

    }

    class DataNode extends Node {

        ArrayList<V> vals = new ArrayList<>();
        DataNode prev, next;

    }

    private int degree;
    private Node root;
    private int size;

    public BPlusTree(int degree) {
        this.degree = degree;
    }

    public int size() {
        return size;
    }

    public V get(K key) {
        Objects.requireNonNull(key);

        if (root == null) {
            return null;
        }

        Node cur = root;
        while (!(cur instanceof BPlusTree.DataNode)) {
            int i = floor(cur.keys, key);
            cur = ((IndexNode) cur).children.get(i);
        }

        int i = floor(cur.keys, key);
        if (i >= 0 && cur.keys.get(i).compareTo(key) == 0) {
            return ((DataNode) cur).vals.get(i);
        }
        return null;
    }

    public V put(K key, V val) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(val);

        if (root == null) {
            DataNode newNode = new DataNode();
            newNode.keys.add(key);
            newNode.vals.add(val);
            root = newNode;
            size++;
            return null;
        }

        Node cur = root;
        while (!(cur instanceof BPlusTree.DataNode)) {
            int i = floor(cur.keys, key);
            if (i < 0) {
                cur.keys.set(0, key);
                i=0;
            }
            cur = ((IndexNode) cur).children.get(i);
        }

        V oldV = null;
        int i = floor(cur.keys, key);
        if (i < 0) {
            cur.keys.add(0, key);
            ((DataNode) cur).vals.add(0, val);
        } else {// i >= 0
            if (cur.keys.get(i).compareTo(key) < 0) {
                cur.keys.add(i+1, key);
                ((DataNode) cur).vals.add(i+1, val);
            } else {
                oldV = ((DataNode) cur).vals.get(i);
                cur.keys.set(i, key);
                ((DataNode) cur).vals.set(i, val);
            }
        }

        size++;
        resolveOverflow(cur);

        return oldV;
    }

    public V remove(K key) {
        Objects.requireNonNull(key);

        if (root == null) {
            return null;
        }

        Node cur = root;
        while (!(cur instanceof BPlusTree.DataNode)) {
            int i = floor(cur.keys, key);
            if (i < 0) {
                return null;
            }
            cur = ((IndexNode) cur).children.get(i);
        }

        int i = floor(cur.keys, key);
        if (i < 0 || cur.keys.get(i).compareTo(key) < 0) {
            return null;
        }

        cur.keys.remove(i);
        V removedV = ((DataNode) cur).vals.remove(i);
        size--;

        // 如果删除的是第一个key，向上更新
        Node c = cur;
        IndexNode p = c.parent;
        int j = i;
        while (j == 0 && p != null) {
            j = p.children.indexOf(c);
            p.keys.set(j, c.keys.get(0));
            c = p;
            p = c.parent;
        }

        resolveUnderflow(cur);

        return removedV;
    }

    private void resolveOverflow(Node cur) {
        while (cur != null && cur.keys.size() > degree) {
            IndexNode parent = cur.parent;
            if (parent == null) {
                parent = new IndexNode();
                parent.keys.add(cur.keys.get(0));
                parent.children.add(cur);
                root = parent;
            }

            // [0, m)
            // [m, size)
            int m = cur.keys.size()/2;

            Node right;
            if (cur instanceof BPlusTree.DataNode) {
                List<K> rKeys = cur.keys.subList(m, cur.keys.size());
                List<V> rVals = ((DataNode) cur).vals.subList(m, ((DataNode) cur).vals.size());

                right = new DataNode();
                right.keys = new ArrayList<>(rKeys);
                ((DataNode) right).vals = new ArrayList<>(rVals);

                rKeys.clear();
                rVals.clear();

                ((DataNode) cur).next = (DataNode) right;
                ((DataNode) right).prev = (DataNode) cur;
            } else {
                List<K> rKeys = cur.keys.subList(m, cur.keys.size());
                List<Node> rChildren = ((IndexNode) cur).children.subList(m, ((IndexNode) cur).children.size());

                right = new IndexNode();
                right.keys = new ArrayList<>(rKeys);
                ((IndexNode) right).children = new ArrayList<>(rChildren);

                // 父节点分裂完成后要更新子节点指针
                for (Node child : rChildren) {
                    child.parent = (IndexNode) right;
                }

                rKeys.clear();
                rChildren.clear();
            }

            cur.parent = parent;
            right.parent = parent;

            int i = parent.children.indexOf(cur);
            parent.keys.add(i+1, right.keys.get(0));
            parent.children.add(i+1, right);

            cur = parent;
        }
    }

    private void resolveUnderflow(Node cur) {
        while (cur != null &&
                ((cur == root && ((cur instanceof BPlusTree.IndexNode && cur.keys.size() < 2) || cur instanceof BPlusTree.DataNode && cur.keys.size() < 1))
                        || (cur != root && cur.keys.size() < (degree+1)/2))) {

            if (cur == root) {
                if (cur instanceof BPlusTree.DataNode) {
                    root = null;
                    // 忽略根节点是数据节点且有两个元素的情况
                } else {
                    root = ((IndexNode) cur).children.get(0);
                    root.parent = null;
                }
                break;
            }

            IndexNode parent = cur.parent;
            int i = parent.children.indexOf(cur);
            Node leftSibling = null;
            Node rightSibling = null;
            if (i-1 >= 0) {
                leftSibling = parent.children.get(i-1);
            }
            if (i+1 < parent.children.size()) {
                rightSibling = parent.children.get(i+1);
            }

            if (leftSibling != null && leftSibling.keys.size() > (degree+1)/2) {
                if (cur instanceof BPlusTree.DataNode) {
                    K lastK = leftSibling.keys.remove(leftSibling.keys.size()-1);
                    V lastV = ((DataNode) leftSibling).vals.remove(((DataNode) leftSibling).vals.size()-1);
                    parent.keys.set(i, lastK);
                    cur.keys.add(0, lastK);
                    ((DataNode) cur).vals.add(0, lastV);

                } else {
                    K lastK = leftSibling.keys.remove(leftSibling.keys.size()-1);
                    Node lastC = ((IndexNode) leftSibling).children.remove(((IndexNode) leftSibling).children.size()-1);
                    parent.keys.set(i, lastK);
                    cur.keys.add(0, lastK);
                    ((IndexNode) cur).children.add(0, lastC);

                    lastC.parent = (IndexNode) cur;
                }
                // 因为存在左兄弟，所以cur不是第一个，无需向上更新索引

            } else if (rightSibling != null && rightSibling.keys.size() > (degree+1)/2) {
                if (cur instanceof BPlusTree.DataNode) {
                    K firstK = rightSibling.keys.remove(0);
                    V firstV = ((DataNode) rightSibling).vals.remove(0);
                    parent.keys.set(i+1, rightSibling.keys.get(0));
                    cur.keys.add(firstK);
                    ((DataNode) cur).vals.add(firstV);

                } else {
                    K firstK = rightSibling.keys.remove(0);
                    Node firstC = ((IndexNode) rightSibling).children.remove(0);
                    parent.keys.set(i+1, rightSibling.keys.get(0));
                    cur.keys.add(firstK);
                    ((IndexNode) cur).children.add(firstC);

                    firstC.parent = (IndexNode) cur;
                }
                // 最左边的只有可能是cur，cur的索引没变，无需向上更新

            } else if (leftSibling != null) {
                if (cur instanceof BPlusTree.DataNode) {
                    ((DataNode) leftSibling).next = ((DataNode) cur).next;
                    if (((DataNode) leftSibling).next != null) {
                        ((DataNode) leftSibling).next.prev = ((DataNode) leftSibling);
                    }

                    merge((DataNode) leftSibling, (DataNode) cur);
                } else {
                    merge((IndexNode) leftSibling, (IndexNode) cur);
                }
                int j = parent.children.indexOf(cur);
                parent.keys.remove(j);
                parent.children.remove(j);
            } else {// rightSibling != null
                if (cur instanceof BPlusTree.DataNode) {
                    ((DataNode) cur).next = ((DataNode) rightSibling).next;
                    if (((DataNode) cur).next != null) {
                        ((DataNode) cur).next.prev = ((DataNode) cur);
                    }

                    merge((DataNode) cur, (DataNode) rightSibling);
                } else {
                    merge((IndexNode) cur, (IndexNode) rightSibling);
                }
                int j = parent.children.indexOf(rightSibling);
                parent.keys.remove(j);
                parent.children.remove(j);
            }

            cur = parent;
        }
    }

    // 将右边合并进左边
    private void merge(DataNode left, DataNode right) {
        left.keys.addAll(right.keys);
        left.vals.addAll(right.vals);
        right.keys.clear();
        right.vals.clear();
    }

    // 将右边合并进左边
    private void merge(IndexNode left, IndexNode right) {

        for (Node child : right.children) {
            child.parent = left;
        }

        left.keys.addAll(right.keys);
        left.children.addAll(right.children);
        right.keys.clear();
        right.children.clear();
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

            if (cur instanceof BPlusTree.IndexNode) {
                for (Node child : ((IndexNode) cur).children) {
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
