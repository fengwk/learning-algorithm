package fun.fengwk.learning.algorithm.graph.undirectedweighted;

import org.junit.Test;

/**
 * @author fengwk
 */
public class AlgorithmTest {

    @Test
    public void testKruskal() {
        Kruskal kruskal = new Kruskal(UndirectedWeightedAdjSet.read("fun/fengwk/learning/algorithm/graph/undirectedweighted/graph1"));
        System.out.println(kruskal.mst());
    }

    @Test
    public void testPrim() {
        Prim prim = new Prim(UndirectedWeightedAdjSet.read("fun/fengwk/learning/algorithm/graph/undirectedweighted/graph1"));
        System.out.println(prim.mst());
    }

    @Test
    public void testDijkstra() {
        Dijkstra dijkstra = new Dijkstra(UndirectedWeightedAdjSet.read("fun/fengwk/learning/algorithm/graph/undirectedweighted/graph2"), 0);
        System.out.println(dijkstra.dis(4));
        System.out.println(dijkstra.path(4));
    }

    @Test
    public void testDijkstra_NonPQ() {
        Dijkstra_NonPQ dijkstra = new Dijkstra_NonPQ(UndirectedWeightedAdjSet.read("fun/fengwk/learning/algorithm/graph/undirectedweighted/graph2"), 0);
        System.out.println(dijkstra.dis(4));
    }

    @Test
    public void testBellmanFord() {
        BellmanFord bf = new BellmanFord(UndirectedWeightedAdjSet.read("fun/fengwk/learning/algorithm/graph/undirectedweighted/graph2"), 0);
        System.out.println(bf.dis(4));
    }

    @Test
    public void testFloyed() {
        Floyed floyed = new Floyed(UndirectedWeightedAdjSet.read("fun/fengwk/learning/algorithm/graph/undirectedweighted/graph2"));
        System.out.println(floyed.dis(0, 4));
    }

}
