package fun.fengwk.learning.algorithm.graph.directed;

import org.junit.Test;

/**
 * @author fengwk
 */
public class AlgorithmTest {

    @Test
    public void testDirectedCycleDetection() {
        DirectedCycleDetection directedCycleDetection = new DirectedCycleDetection(DirectedAdjSet.read("fun/fengwk/learning/algorithm/graph/directed/graph1"));
        System.out.println(directedCycleDetection.hasCycle());

        directedCycleDetection = new DirectedCycleDetection(DirectedAdjSet.read("fun/fengwk/learning/algorithm/graph/directed/graph2"));
        System.out.println(directedCycleDetection.hasCycle());

        DirectedAdjSet directedAdjSet = DirectedAdjSet.read("fun/fengwk/learning/algorithm/graph/directed/graph1");
        System.out.println(directedAdjSet);
        for (int v = 0; v < directedAdjSet.V(); v++) {
            System.out.println(String.format("%d\t%d\t%d", v, directedAdjSet.inDegree(v), directedAdjSet.outDegree(v)));
        }
    }

    @Test
    public void testTopoSort() {
        TopoSort topoSort = new TopoSort(DirectedAdjSet.read("fun/fengwk/learning/algorithm/graph/directed/graph1"));
        System.out.println(topoSort.hasCycle());
        System.out.println(topoSort.result());
    }

}
