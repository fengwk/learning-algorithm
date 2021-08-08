package fun.fengwk.learning.algorithm.playdatastruct.bst;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Binary Search Tree
 *
 * @author fengwk
 */
public class BST<K extends Comparable<K>, V> {

    class Node {

        K key;
        V val;
        Node left, right;

        public Node(K key, V val, Node left, Node right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return String.format("(%s,%s)", key, val);
        }
    }

    private Node root;
    private int size;

    private Node hot;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public V get(K key) {
        Objects.requireNonNull(key);

        Node node = findNode(key);

        return node == null ? null : node.val;
    }

    public V put(K key, V val) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(val);

        if (root == null) {
            root = new Node(key, val, null, null);
            size++;
            return null;
        }

        Node node = findNode(key);

        if (node != null) {
            V oldVal = node.val;
            node.key = key;
            node.val = val;
            return oldVal;
        }

        if (key.compareTo(hot.key) < 0) {
            hot.left = new Node(key, val, null, null);
        } else {
            hot.right = new Node(key, val, null, null);
        }
        size++;
        return null;
    }

    public V remove(K key) {
        Objects.requireNonNull(key);

        if (size == 1) {
            V retVal = root.val;
            root = null;
            size--;
            return retVal;
        }

        Node node = findNode(key);

        if (node == null) {
            return null;
        }

        V retVal = node.val;

        if (node.left == null) {
            int comp = key.compareTo(hot.key);
            if (comp < 0) {
                hot.left = node.right;
            } else {
                hot.right = node.right;
            }
        } else if (node.right == null) {
            int comp = key.compareTo(hot.key);
            if (comp < 0) {
                hot.left = node.left;
            } else {
                hot.right = node.left;
            }
        } else {
            Node successor = findSuccessor(node);
            node.key = successor.key;
            node.val = successor.val;

            if (successor.key.compareTo(hot.key) < 0) {
                hot.left = null;
            } else {
                hot.right = null;
            }
        }

        size--;

        return retVal;
    }

    // 如果树中存在key返回对应的val
    // 如果树中不存在key返回小于key且最大的key对应的val
    // 如果key比树中最小的key还小返回null
    public V floor(K key) {
        LinkedList<Node> stack = new LinkedList<>();

        Node cur = root;
        while (cur != null) {
            stack.push(cur);
            int comp = key.compareTo(cur.key);
            if (comp == 0) {
                return cur.val;
            } else if (comp < 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }

        V res = null;
        while (!stack.isEmpty()) {
            Node bak = stack.pop();
            if (bak.key.compareTo(key) < 0) {
                res = bak.val;
                break;
            }
        }

        return res;
    }

    private Node findNode(K key) {
        hot = null;
        Node cur = root;
        while (cur != null) {
            int comp = key.compareTo(cur.key);
            if (comp == 0) {
                break;
            } else if (comp < 0) {
                hot = cur;
                cur = cur.left;
            } else {
                hot = cur;
                cur = cur.right;
            }
        }
        return cur;
    }

    private Node findSuccessor(Node node) {
        if (node.right == null) {
            return node;
        }

        hot = node;
        Node cur = node.right;
        while (cur.left != null) {
            hot = cur;
            cur = cur.left;
        }
        return cur;
    }

    @Override
    public String toString() {
        return new TreePrinter<>(Node::toString, n -> n.left, n -> n.right).toString(root);
    }

}
