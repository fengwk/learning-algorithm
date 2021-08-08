package fun.fengwk.learning.algorithm.graph.undirectedweighted;

import org.junit.Test;

/**
 * @author fengwk
 */
public class UndirectedWeightedAdjSetTest {

    @Test
    public void test() {
        UndirectedWeightedAdjSet g = UndirectedWeightedAdjSet.read("fun/fengwk/learning/algorithm/graph/undirectedweighted/graph1");
        System.out.println(g);
    }

}
