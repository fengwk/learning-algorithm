package fun.fengwk.learning.algorithm.playdatastruct.list;

import org.junit.Test;

/**
 * @author fengwk
 */
public class ListTest {

    @Test
    public void test() {
        List<Integer> list = new List<>();

        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        System.out.println(list);

        list.addFirst(4);
        list.addFirst(5);
        list.addFirst(6);
        System.out.println(list);

        list.add(3, 6);
        System.out.println(list);

        list.removeFirst();
        list.removeLast();
        System.out.println(list);

        list.remove(2);
        System.out.println(list);

        while (!list.isEmpty()) {
            System.out.println(list.removeFirst());
        }

        System.out.println(list);
    }

}
