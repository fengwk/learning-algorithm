package fun.fengwk.learning.algorithm.playdatastruct.avl;

import fun.fengwk.learning.algorithm.playdatastruct.bst.TreePrinter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author fengwk
 */
public class AVL<K extends Comparable<K>, V> {

    class Node {

        K key;
        V val;
        Node left, right;
        int height = 1;

        public Node(K key, V val, Node left, Node right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return String.format("(%s,%s,%d)", key, val, height);
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

        Node node = findNode(key, null);

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

        LinkedList<Node> findStack = new LinkedList<>();
        Node node = findNode(key, findStack);

        if (node != null) {
            V oldVal = node.val;
            node.key = key;
            node.val = val;
            return oldVal;
        }

        Node p = findStack.peek();
        if (key.compareTo(p.key) < 0) {
            p.left = new Node(key, val, null, null);
        } else {
            p.right = new Node(key, val, null, null);
        }
        size++;

        rebalance(findStack);

        return null;
    }

    private void updateHeight(Node node) {
        if (node != null) {
            node.height = Math.max(height(node.left), height(node.right)) + 1;
        }
    }

    /*
             g                 p
            / \               /  \
           p  t4             v    g
          / \              / \    / \
         v   t3           t1 t2  t3 t4
        / \
       t1 t2
     */
    private Node rotateRight(Node g) {
        Node p = g.left;
        Node v = p.left;
        Node t3 = p.right;
        Node t4 = g.right;

        g.left = t3;
        updateHeight(g);

        p.right = g;
        updateHeight(p);

        return p;
    }

    /*
             g                   p
            / \                /   \
           t1   p             g     v
               / \           / \    / \
              t2  v         t1 t2  t3 t4
                 / \
                t3 t4
     */
    private Node rotateLeft(Node g) {
        Node p = g.right;
        Node v = p.right;
        Node t1 = g.left;
        Node t2 = p.left;

        g.right = t2;
        updateHeight(g);

        p.left = g;
        updateHeight(p);

        return p;
    }

    private void rebalance(LinkedList<Node> findStack) {
        Node child = null;
        while (!findStack.isEmpty()) {
            Node bak = findStack.pop();

            if (child != null) {
                if (child.key.compareTo(bak.key) < 0) {
                    bak.left = child;
                } else {
                    bak.right = child;
                }
                child = null;
            }

            int balanceFactor = height(bak.left) - height(bak.right);
            if (balanceFactor > 1) {// L
                if (height(bak.left.left) >= height(bak.left.right)) {
                    // LL
                    child = rotateRight(bak);
                } else {
                    // LR
                    bak.left = rotateLeft(bak.left);
                    child = rotateRight(bak);
                }

            } else if (balanceFactor < -1) {// R
                if (height(bak.right.right) >= height(bak.right.left)) {
                    // RR
                    child = rotateLeft(bak);
                } else {
                    // RL
                    bak.right = rotateRight(bak.right);
                    child = rotateLeft(bak);
                }
            } else {
                updateHeight(bak);
            }
        }

        if (child != null) {
            root = child;
        }
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    public V remove(K key) {
        Objects.requireNonNull(key);

        if (size == 1) {
            V retVal = root.val;
            root = null;
            size--;
            return retVal;
        }

        LinkedList<Node> findStack = new LinkedList<>();
        Node node = findNode(key, findStack);

        if (node == null) {
            return null;
        }

        V retVal = node.val;

        if (node.left == null) {
            Node p = findStack.peek();
            if (key.compareTo(p.key) < 0) {
                p.left = node.right;
            } else {
                p.right = node.right;
            }
        } else if (node.right == null) {
            Node p = findStack.peek();
            if (key.compareTo(p.key) < 0) {
                p.left = node.left;
            } else {
                p.right = node.left;
            }
        } else {
            Node successor = findSuccessor(node, findStack);
            node.key = successor.key;
            node.val = successor.val;
            Node p = findStack.peek();
            if (successor.key.compareTo(p.key) < 0) {
                p.left = null;
            } else {
                p.right = null;
            }
        }

        size--;

        rebalance(findStack);

        return retVal;
    }


    private Node findNode(K key, LinkedList<Node> findStack) {
        Node cur = root;
        while (cur != null) {
            int comp = key.compareTo(cur.key);
            if (comp == 0) {
                break;
            } else if (comp < 0) {
                if (findStack != null) {
                    findStack.push(cur);
                }
                cur = cur.left;
            } else {
                if (findStack != null) {
                    findStack.push(cur);
                }
                cur = cur.right;
            }
        }
        return cur;
    }

    private Node findSuccessor(Node node, LinkedList<Node> findStack) {
        if (node.right == null) {
            return node;
        }

        findStack.push(node);
        Node cur = node.right;
        while (cur.left != null) {
            findStack.push(cur);
            cur = cur.left;
        }
        return cur;
    }

    public List<K> inorderTraverse() {
        List<K> result = new ArrayList<>();
        LinkedList<Node> stack = new LinkedList<>();
        pushLeftChain(root, stack);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            result.add(cur.key);
            pushLeftChain(cur.right, stack);
        }
        return result;
    }

    private void pushLeftChain(Node node, LinkedList<Node> stack) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    @Override
    public String toString() {
        return new TreePrinter<>(Node::toString, n -> n.left, n -> n.right).toString(root);
    }

}
