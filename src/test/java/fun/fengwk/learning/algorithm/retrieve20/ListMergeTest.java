package fun.fengwk.learning.algorithm.retrieve20;

import org.junit.Test;

import java.util.List;

/**
 * @author fengwk
 */
public class ListMergeTest {


    @Test
    public void testDiff() {
        List<Integer> intersection = ListMerge.diff(new int[]{1, 3, 4, 4, 5, 6}, new int[]{3, 4, 4, 6, 7});
        System.out.println(intersection);
    }


    @Test
    public void testUnion() {
        List<Integer> intersection = ListMerge.union(new int[]{1, 3, 4, 4, 5, 6}, new int[]{3, 4, 4, 6, 7});
        System.out.println(intersection);
    }

    @Test
    public void testIntersection() {
        List<Integer> intersection = ListMerge.intersection(new int[]{1, 3, 4, 4, 5, 6}, new int[]{3, 4, 4, 6, 7});
        System.out.println(intersection);
    }

}
