package fun.fengwk.learning.algorithm.playdatastruct.array;

import org.junit.Test;

/**
 * @author fengwk
 */
public class ArrayTest {

    @Test
    public void test() {
        Array<Integer> arr = new Array<>();
        for (int i = 0; i < 16; i++) {
            arr.addLast(i);
        }
        System.out.println(arr);

        arr.addLast(16);
        System.out.println(arr);

        for (int i = 0; i < 16; i++) {
            arr.addFirst(i);
        }
        System.out.println(arr);

        for (int i = 0; i < 17; i++) {
            arr.removeLast();
        }
        System.out.println(arr);

        while (!arr.isEmpty()) {
            arr.removeFirst();
        }
        System.out.println(arr);
    }

}
