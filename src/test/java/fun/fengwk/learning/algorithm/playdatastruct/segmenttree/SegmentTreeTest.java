package fun.fengwk.learning.algorithm.playdatastruct.segmenttree;

import org.junit.Test;

import java.util.function.Function;

/**
 * @author fengwk
 */
public class SegmentTreeTest {

    @Test
    public void test() {
        Integer[] data = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        SegmentTree<Integer, Integer> st = new SegmentTree<>(data, Function.identity(), Integer::sum);
        System.out.println(st);

        System.out.println(st.range(3, 7));
    }

}
