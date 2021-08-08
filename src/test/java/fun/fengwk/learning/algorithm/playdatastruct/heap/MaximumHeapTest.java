package fun.fengwk.learning.algorithm.playdatastruct.heap;

import org.junit.Test;

/**
 * @author fengwk
 */
public class MaximumHeapTest {

    @Test
    public void test() {
        MaximumHeap<Integer> heap = new MaximumHeap<>();

        for (int i = 0; i < 20; i++) {
            heap.offer(i);
        }

        System.out.println(heap);

        while (!heap.isEmpty()) {
            System.out.println(heap.poll());
        }

    }

}
