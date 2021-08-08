package fun.fengwk.learning.algorithm.djh.rbtree;

import fun.fengwk.learning.algorithm.playdatastruct.bst.TreePrinter;

import java.util.Objects;

/**
 * @author fengwk
 */
public class RBTree<K extends Comparable<K>, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    class Node {
        K key;
        V val;
        Node left, right, parent;
        boolean color;
        int height;// 黑高

        Node(K key, V val, boolean color, Node parent) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.height = color == RED ? 1 : 2;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return String.format("(%s,%s)", key, color == RED ? "R" : "B");
        }
    }

    private Node root;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public V get(K key) {
        Objects.requireNonNull(key);

        Node cur = root;
        while (cur != null) {
            int comp = key.compareTo(cur.key);
            if (comp == 0) {
                return cur.val;
            } else if (comp < 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }

        return null;
    }

    public V put(K key, V val) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(val);

        if (root == null) {
            root = new Node(key, val, BLACK, null);
            size++;
            return null;
        }

        Node p = null;
        Node cur = root;
        while (cur != null) {
            int comp = key.compareTo(cur.key);
            if (comp == 0) {
                V oldVal = cur.val;
                cur.key = key;
                cur.val = val;
                return oldVal;
            } else if (comp < 0) {
                p = cur;
                cur = cur.left;
            } else {
                p = cur;
                cur = cur.right;
            }
        }

        Node x = new Node(key, val, RED, p);
        if (key.compareTo(p.key) < 0) {// key < cur.key
            p.left = x;
        } else {// key > cur.key
            p.right = x;
        }
        size++;

        resolveDoubleRed(x);

        return null;
    }

    public V remove(K key) {
        Objects.requireNonNull(key);

        if (root == null) {
            return null;
        }

        Node cur = root;
        while (cur != null) {
            int comp = key.compareTo(cur.key);
            if (comp == 0) {
                break;
            } else if (comp < 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }

        if (cur == null) {
            return null;
        }

        V oldVal = cur.val;
        if (cur.left != null && cur.right != null) {
            Node successor = cur.right;
            while (successor.left != null) {
                successor = successor.left;
            }

            cur.key = successor.key;
            cur.val = successor.val;
            cur = successor;
        }

        // 此时cur最多有一个孩子
        assert !(cur.left != null && cur.right != null);

        Node p = cur.parent;
        Node r = cur.left != null ? cur.left : cur.right;

        if (r != null) {
            r.parent = p;
        }

        if (p == null) {
            root = r;
        } else if (p.left == cur) {
            p.left = r;
        } else {
            p.right = r;
        }

        size--;

        if (color(cur) != color(r)) {
            // 如果cur和r一个为红一个为黑，那么只要将r染为黑色黑高就不会变
            if (r != null) {
                r.color = BLACK;
            }
        } else {
            // 首次下溢发生一定是因为cur.left与cur.right都是空，否则难以满足B树性质
            assert r == null;
            resolveDoubleBlack(p, r);
        }

        return oldVal;
    }

    private void resolveDoubleRed(Node x) {
        Node p;
        while (color(x) == RED && color(p = x.parent) == RED) {
            Node g = p.parent;// 如果p是RED，g一定存在，且g为黑
            assert g != null;
            assert color(g) == BLACK;
            Node u = g.left == p ? g.right : g.left;
            Node newp;
            Node gp = g.parent;// 注意要在拓扑结构为开始时保存gp指针
            if (color(u) == BLACK) {
                // RR-1

                if (g.left == p && p.left == x) {
                    /*
                     *      g(黑)        p(黑)
                     *     /           /     \
                     *    p(红)       x(红)   g(红)
                     *   /
                     *  x(红)
                     */
                    p.color = BLACK;
                    updateHeight(p);
                    g.color = RED;
                    updateHeight(g);

                    newp = rotateRight(g);
                }

                else if (g.left == p && p.right == x) {
                    /*
                     *      g(黑)        p(黑)
                     *     /           /     \
                     *    p(红)       x(红)   g(红)
                     *     \
                     *      x(红)
                     */
                    p.color = BLACK;
                    updateHeight(p);
                    g.color = RED;
                    updateHeight(g);

                    g.left = rotateLeft(p);
                    newp = rotateRight(g);
                }

                else if (g.right == p && p.right == x) {
                    /*
                     *  g(黑)          p(黑)
                     *   \            /     \
                     *    p(红)       x(红)   g(红)
                     *     \
                     *      x(红)
                     */
                    p.color = BLACK;
                    updateHeight(p);
                    g.color = RED;
                    updateHeight(g);

                    newp = rotateLeft(g);
                }

                else {// g.right == p && p.left == x
                    assert g.right == p;
                    assert p.left == x;

                    /*
                     *   g(黑)           p(黑)
                     *     \            /     \
                     *      p(红)     x(红)   g(红)
                     *      /
                     *   x(红)
                     */
                    p.color = BLACK;
                    updateHeight(p);
                    g.color = RED;
                    updateHeight(g);

                    g.right = rotateRight(p);
                    newp = rotateLeft(g);
                }

                x = null;// 下一次循环直接结束

            } else {
                // RR-2
                // u为红，等价于B树上溢
                // 此时u一定不为null
                if (g.left == p && p.left == x) {
                    /*
                     *      g(黑)             p(红)
                     *     /    \            /    \
                     *    p(红)  u(红)      x(黑)   g(黑)
                     *   /                           \
                     *  x(红)                         u(红)
                     */
                    x.color = BLACK;
                    updateHeight(x);

                    newp = rotateRight(g);
                }

                else if (g.left == p && p.right == x) {
                    /*
                     *      g(黑)             p(红)
                     *     /    \            /    \
                     *    p(红)  u(红)      x(黑)   g(黑)
                     *     \                         \
                     *     x(红)                      u(红)
                     */
                    x.color = BLACK;
                    updateHeight(x);

                    g.left = rotateLeft(p);
                    newp = rotateRight(g);
                }

                else if (g.right == p && p.right == x) {
                    /*
                     *      g(黑)             p(红)
                     *     /    \            /    \
                     *   u(红)  p(红)      g(黑)   x(黑)
                     *            \       /
                     *           x(红)   u(红)
                     */
                    x.color = BLACK;
                    updateHeight(x);

                    newp = rotateLeft(g);
                }

                else {// g.right == p && p.left == x
                    assert g.right == p;
                    assert p.left == x;

                    /*
                     *      g(黑)             p(红)
                     *     /    \            /    \
                     *   u(红)  p(红)      g(黑)   x(黑)
                     *           /         /
                     *         x(红)     u(红)
                     */
                    x.color = BLACK;
                    updateHeight(x);

                    g.right = rotateRight(p);
                    newp = rotateLeft(g);
                }

                x = newp;

            }

            newp.parent = gp;
            if (gp == null) {
                // 如果上溢发生到根节点，那么要注意根要变为BLACK
                // 这个变换在B树中也是合理的
                newp.color = BLACK;
                root = newp;
            } else if (gp.left == g) {
                gp.left = newp;
            } else {
                assert gp.right == g;
                gp.right = newp;
            }
        }

    }

    private void resolveDoubleBlack(Node p, Node x) {
        // p颜色不确定
        while (p != null) {

            Node s = p.left == x ? p.right : p.left;
            // x一定存在兄弟，否则B树性质不满足
            assert s != null;

            if (color(s) == BLACK && (color(s.left) == RED || color(s.right) == RED)) {
                // BB-1：s为黑，且至少有一个红孩子t

                Node t = color(s.left) == RED ? s.left : s.right;
                Node news;
                Node pp = p.parent;

                if (color(p.left) == BLACK && p.right == s && s.right == t) {
                    /*
                     *       p                    s
                     *     /   \                /   \
                     *  x(黑)    s(黑)          p(黑) t(黑)
                     *            \           /
                     *             t(红)     x(不存在)
                     */

                    s.color = p.color;
                    p.color = BLACK;
                    t.color = BLACK;
                    updateHeight(t);
                    updateHeight(s);
                    updateHeight(p);

                    news = rotateLeft(p);
                } else if (color(p.left) == BLACK && p.right == s && s.left == t) {
                    /*
                     *       p                    t
                     *     /   \                /   \
                     *  x(黑)   s(黑)          p(黑) s(黑)
                     *          /             /
                     *        t(红)         x(不存在)
                     */

                    t.color = p.color;
                    p.color = BLACK;
                    s.color = BLACK;
                    updateHeight(t);
                    updateHeight(s);
                    updateHeight(p);

                    p.right = rotateRight(s);
                    news = rotateLeft(p);
                } else if (color(p.right) == BLACK && p.left == s && s.left == t) {
                    /*
                     *          p                    s
                     *        /   \                /   \
                     *     s(黑)    x(黑)         t(黑) p(黑)
                     *     /                              \
                     *   t(红)                           x(不存在)
                     */
                    s.color = p.color;
                    p.color = BLACK;
                    t.color = BLACK;
                    updateHeight(t);
                    updateHeight(s);
                    updateHeight(p);

                    news = rotateRight(p);
                } else {// color(p.right) == BLACK && p.left == s && s.right == t
                    assert color(p.right) == BLACK && p.left == s && s.right == t;
                    /*
                     *          p                    t
                     *        /   \                /   \
                     *     s(黑)    x(黑)         s(黑) p(黑)
                     *       \                             \
                     *      t(红)                        x(不存在)
                     */
                    t.color = p.color;
                    p.color = BLACK;
                    s.color = BLACK;
                    updateHeight(t);
                    updateHeight(s);
                    updateHeight(p);

                    p.left = rotateLeft(s);
                    news = rotateRight(p);
                }

                news.parent = pp;
                if (pp == null) {
                    root = news;
                } else if (pp.left == p) {
                    pp.left = news;
                } else {
                    assert pp.right == p;
                    pp.right = news;
                }

                // 向兄弟借节点后全局恢复
                p = null;
            }

            else if (color(p) == RED && color(s) == BLACK && color(s.left) == BLACK && color(s.right) == BLACK) {
                // BB-2R：s为黑，s的孩子都为黑，p为红

                /*
                 *       p(红)                 p(黑)
                 *     /      \                /   \
                 *  x(不存在)    s(黑)      x(不存在) s(红)
                 */
                p.color = BLACK;
                s.color = RED;
                updateHeight(s);
                updateHeight(p);

                // 由于p原先是红的，因此变黑后原先对应B树节点依然有元素，因此可以全局恢复
                p = null;
            }

            else if (color(p) == BLACK && color(s) == BLACK && color(s.left) == BLACK && color(s.right) == BLACK) {
                // BB-2B
                /*
                 *       p(黑)                 p(黑)
                 *     /      \                /   \
                 *  x(不存在)    s(黑)      x(不存在) s(红)
                 */
                s.color = RED;
                updateHeight(s);
                updateHeight(p);

                // p下溢
                Node pp = p.parent;
                if (pp != null) {
                    x = p;
                    p = pp;
                } else {
                    p = null;
                }

            }

            else {
                // BB-3
                assert color(s) == RED && color(p) == BLACK;

                s.color = BLACK;
                p.color = RED;

                Node pp = p.parent;// 顺序不可交换
                Node news = p.left == s ? rotateRight(p) : rotateLeft(p);
                news.parent = pp;
                if (pp == null) {
                    root = news;
                } else if (pp.left == p) {
                    pp.left = news;
                } else {
                    assert pp.right == p;
                    pp.right = news;
                }

            }
        }

    }

    /**
     *       g                 p
     *      / \               /  \
     *     p  t4             v    g
     *    / \              / \    / \
     *   v   t3           t1 t2  t3 t4
     *  / \
     * t1 t2
     *
     * @param g
     * @return
     */
    private Node rotateRight(Node g) {
        Node p = g.left;
        Node t3 = p.right;

        g.left = t3;
        if (t3 != null) {
            t3.parent = g;
        }
        updateHeight(g);

        p.right = g;
        g.parent = p;
        updateHeight(p);

        return p;
    }

    /**
     *   g                   p
     *  / \                /   \
     * t1   p             g     v
     *     / \           / \    / \
     *    t2  v         t1 t2  t3 t4
     *       / \
     *      t3 t4
     *
     * @param g
     * @return
     */
    private Node rotateLeft(Node g) {
        Node p = g.right;
        Node t2 = p.left;

        g.right = t2;
        if (t2 != null) {
            t2.parent = g;
        }
        updateHeight(g);

        p.left = g;
        g.parent = p;
        updateHeight(p);

        return p;
    }

    private boolean color(Node node) {
        return node == null ? BLACK : node.color;
    }

    private void updateHeight(Node node) {
        if (node != null) {
            node.height = Math.max(height(node.left), height(node.right)) + ((color(node) == BLACK) ? 1 : 0);
        }
    }

    private int height(Node node) {
        return node == null ? 1 : node.height;
    }

    @Override
    public String toString() {
        return new TreePrinter<>(Node::toString, n -> n.left, n -> n.right).toString(root);
    }

}
