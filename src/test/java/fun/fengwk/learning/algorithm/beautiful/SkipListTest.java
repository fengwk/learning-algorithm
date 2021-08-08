package fun.fengwk.learning.algorithm.beautiful;

import org.junit.Test;

/**
 * @author fengwk
 */
public class SkipListTest {

    @Test
    public void test() {
        SkipList<Integer, Integer> sl = new SkipList<>(8, System.currentTimeMillis());

        sl.put(1, 1);
        sl.put(2, 2);
        sl.put(3, 3);

        sl.put(6, 6);
        sl.put(5, 5);
        sl.put(4, 4);

        System.out.println(sl);
        System.out.println("--------");

        sl.remove(3);
        sl.remove(4);
        sl.remove(5);
        System.out.println(sl);
        System.out.println("--------");

        sl.remove(1);
        sl.remove(2);
        sl.remove(6);
        System.out.println(sl);
    }

}
