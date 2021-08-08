package fun.fengwk.learning.algorithm.playdatastruct.trie;

import org.junit.Test;

/**
 * @author fengwk
 */
public class TrieTest {

    @Test
    public void test() {
        Trie t = new Trie();
        t.add("hello");
        t.add("hi");

        System.out.println(t.contains("hello"));
        System.out.println(t.contains("hi"));
        System.out.println(t.contains("he"));
        System.out.println(t.prefix("h"));
        System.out.println(t.prefix("hello"));
    }


}
