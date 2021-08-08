package fun.fengwk.learning.algorithm.playdatastruct.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fengwk
 */
public class Trie {

    static class Node {

        boolean isWord = false;
        Map<Character, Node> children = new HashMap<>();
    }

    private Node root = new Node();
    private int size;

    public void add(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.computeIfAbsent(word.charAt(i), k -> new Node());
        }
        cur.isWord = true;
        size++;
    }

    public boolean contains(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!cur.children.containsKey(c)) {
                return false;
            }
            cur = cur.children.get(c);
        }
        return cur.isWord;
    }


    public boolean prefix(String pre) {
        Node cur = root;
        for (int i = 0; i < pre.length(); i++) {
            char c = pre.charAt(i);
            if (!cur.children.containsKey(c)) {
                return false;
            }
            cur = cur.children.get(c);
        }
        return true;
    }

}
