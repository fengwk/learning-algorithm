package fun.fengwk.learning.algorithm.playdatastruct.bst;

import java.util.Objects;

/**
 * Binary Search Tree
 *
 * @author fengwk
 */
public class BST_Recursive<K extends Comparable<K>, V> {

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

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public V get(K key) {
        Objects.requireNonNull(key);

        Node node = doGet(root, key);
        return node == null ? null : node.val;
    }

    private Node doGet(Node node, K key) {
        if (node == null) {
            return null;
        }

        int comp = key.compareTo(node.key);
        if (comp == 0) {
            return node;
        } else if (comp < 0) {
            return doGet(node.left, key);
        } else {
            return doGet(node.right, key);
        }
    }

    public V put(K key, V val) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(val);

        V oldV = get(key);

        root = doPut(root, key, val);

        return oldV;
    }

    private Node doPut(Node node, K key, V val) {
        if (node == null) {
            size++;
            return new Node(key, val, null, null);
        }

        int comp = key.compareTo(node.key);
        if (comp == 0) {
            node.key = key;
            node.val = val;
            return node;
        } else if (comp < 0) {
            node.left = doPut(node.left, key, val);
        } else {
            node.right = doPut(node.right, key, val);
        }

        return node;
    }

    private Node doRemove(Node node, K key) {
        if (node == null) {
            return null;
        }

        int comp = key.compareTo(node.key);
        if (comp == 0) {
            if (node.left == null) {
                size--;
                return node.right;
            } else if (node.right == null) {
                size--;
                return node.left;
            } else {
                Node successor = node.right;
                while (successor.left != null) {
                    successor = successor.left;
                }
                node.key = successor.key;
                node.val = successor.val;
                node.right = doRemove(node.right, successor.key);
                return node;
            }
        } else if (comp < 0) {
            node.left = doRemove(node.left, key);
        } else {
            node.right = doRemove(node.right, key);
        }
        return node;
    }

    public V remove(K key) {
        Objects.requireNonNull(key);

        V oldV = get(key);

        root = doRemove(root, key);

        return oldV;
    }

    private Node doFloor(Node node, K key) {
        if (node == null) {
            return null;
        }

        int comp = key.compareTo(node.key);
        if (comp == 0) {
            return node;
        } else if (comp < 0) {
            return doFloor(node.left, key);
        } else {
            Node found = doFloor(node.right, key);
            if (found == null) {
                return node;
            } else {
                return found;
            }
        }
    }

    public V floor(K key) {
        Node node = doFloor(root, key);
        return node == null ? null : node.val;
    }

    @Override
    public String toString() {
        return new BTPrinter<Node>(n -> n.left, n -> n.right).print(root);
    }
}
