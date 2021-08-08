package fun.fengwk.learning.algorithm.playdatastruct.queue;

import org.junit.Test;

/**
 * @author fengwk
 */
public class QueueTest {

    @Test
    public void test() {

        Queue<Integer> queue = new Queue<>();
        queue.enqueue(0);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        System.out.println(queue);

        System.out.println(queue.front());

        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
    }

}
