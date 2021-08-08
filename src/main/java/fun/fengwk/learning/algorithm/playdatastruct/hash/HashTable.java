package fun.fengwk.learning.algorithm.playdatastruct.hash;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author fengwk
 */
public class HashTable<K, V> {

    class Node {

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

    private Node[] tab;
    private int size;

    public HashTable() {
        this.tab = new HashTable.Node[16];
    }

    private void rehash(int newCapacity) {
        Node[] tab = this.tab;
        int capacity = tab.length;
        Node[] newTab = new HashTable.Node[newCapacity];

        for (int i = 0; i < capacity; i++) {
            Node node = tab[i];
            Node prev1 = null;
            Node prev2 = null;
            while (node != null) {
                int offset = hash(node.key) % newTab.length;
                if (offset == i) {
                    if (prev1 == null) {
                        prev1 = newTab[offset] = node;
                    } else {
                        prev1.next = node;
                    }
                } else {
                    if (prev2 == null) {
                        prev2 = newTab[offset] = node;
                    } else {
                        prev2.next = node;
                    }
                }
                node = node.next;
            }
        }

        this.tab = newTab;
    }

    public V put(K key, V val) {

        Objects.requireNonNull(key);
        Objects.requireNonNull(val);

        if (size / (float)tab.length > 0.75) {
            rehash(tab.length * 2);
        }

        int offset = hash(key) % tab.length;
        Node node = tab[offset];
        if (node == null) {
            tab[offset] = new Node(key, val, null);
        } else {
            while (node.next != null) {
                if (key.equals(node.key)) {
                    V oldV = node.val;
                    node.key = key;
                    node.val = val;
                    return oldV;
                }

                node = node.next;
            }
            node.next = new Node(key, val, null);
        }

        size++;

        return null;
    }

    public V get(K key) {
        Objects.requireNonNull(key);

        int offset = hash(key) % tab.length;
        Node node = tab[offset];
        if (node == null) {
            return null;
        }

        while (node.next != null) {
            if (key.equals(node.key)) {
                return node.val;
            }
        }

        return null;
    }

    private int hash(K key) {
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tab.length; i++) {
            sb.append(i).append(':');
            Node node = tab[i];
            if (node != null) {
                sb.append(node);
                node = node.next;
            }
            while (node != null) {
                sb.append("->").append(node);
                node = node.next;
            }
            if (i < tab.length - 1) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }
}
