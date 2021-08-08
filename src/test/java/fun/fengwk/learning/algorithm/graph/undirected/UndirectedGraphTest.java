package fun.fengwk.learning.algorithm.graph.undirected;

import org.junit.Test;

/**
 * @author fengwk
 */
public class UndirectedGraphTest {

    private static final String CP_G1 = "fun/fengwk/learning/algorithm/graph/undirected/graph1";

    @Test
    public void test1() {
        doTest(new UndirectedGraphReader<>(UndirectedAdjMatrix::new).read(CP_G1));
    }

    @Test
    public void test2() {
        doTest(new UndirectedGraphReader<>(UndirectedAdjList::new).read(CP_G1));
    }

    @Test
    public void test3() {
        doTest(new UndirectedGraphReader<>(UndirectedAdjSet::new).read(CP_G1));
    }

    private void doTest(UndirectedGraph g) {
        System.out.println(g);
        System.out.println("---------------");
        /*
        7 6
        0 1
        0 3
        1 6
        2 5
        3 2
        3 4
        */
        assert g.V() == 7;
        assert g.E() == 6;
        assert g.hasEdge(0, 1);
        assert g.hasEdge(0, 3);
        assert g.hasEdge(1, 6);
        assert g.hasEdge(3, 2);
        assert g.hasEdge(3, 4);
        assert g.hasEdge(2, 5);
        assert g.hasEdge(1, 0);
        assert g.hasEdge(3, 0);
        assert g.hasEdge(6, 1);
        assert g.hasEdge(2, 3);
        assert g.hasEdge(4, 3);
        assert g.hasEdge(5, 2);
        assert g.degree(0) == 2;
        assert g.degree(1) == 2;
        assert g.degree(2) == 2;
        assert g.degree(3) == 3;
    }

}
