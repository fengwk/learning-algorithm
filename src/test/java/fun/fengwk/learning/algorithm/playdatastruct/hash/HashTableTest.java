package fun.fengwk.learning.algorithm.playdatastruct.hash;

import org.junit.Test;

/**
 * @author fengwk
 */
public class HashTableTest {

    @Test
    public void test() {
        HashTable<Integer, Integer> tab = new HashTable<>();
        for (int i = 0; i < 16; i++) {
            tab.put(i, i);
        }
        System.out.println(tab);

    }

}
