package fun.fengwk.learning.algorithm.graph.undirected;

import org.junit.Test;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

/**
 * @author fengwk
 */
public class AlgorithmTest {

    @Test
    public void testDFS() {
        DFS dfs = new DFS(new UndirectedGraphReader<>(UndirectedAdjSet::new).read("fun/fengwk/learning/algorithm/graph/undirected/graph2"));
        assert dfs.preorder().equals(Arrays.asList(0, 1, 3, 2, 6, 5, 4));
        System.out.println(dfs.postorder());
    }

    @Test
    public void testDFS_Stack() {
        DFS_Stack dfs = new DFS_Stack(new UndirectedGraphReader<>(UndirectedAdjSet::new).read("fun/fengwk/learning/algorithm/graph/undirected/graph2"));
        assert dfs.order().equals(Arrays.asList(0, 2, 6, 5, 3, 1, 4));
    }

    @Test
    public void testCC() {
        CC cc = new CC(new UndirectedGraphReader<>(UndirectedAdjSet::new).read("fun/fengwk/learning/algorithm/graph/undirected/graph3"));

        for (List<Integer> component : cc.components()) {
            System.out.println(component);
        }

        assert cc.count() == 2;
        assert cc.isConnected(1, 2);
        assert !cc.isConnected(1, 5);
    }

    @Test
    public void testSingleSourcePath() {
        SingleSourcePath ssp = new SingleSourcePath(
                new UndirectedGraphReader<>(UndirectedAdjSet::new).read("fun/fengwk/learning/algorithm/graph/undirected/graph3"),
                0);

        assert ssp.path(6).equals(Arrays.asList(0, 1, 3, 2, 6));
        assert ssp.path(5).equals(Collections.emptyList());
    }

    @Test
    public void testCycleDetection() {
        CycleDetection cd = new CycleDetection(new UndirectedGraphReader<>(UndirectedAdjSet::new).read("fun/fengwk/learning/algorithm/graph/undirected/graph3"));
        assert cd.hasCycle();
    }

    @Test
    public void testBipartiteDetection() {
        BipartiteDetection bd = new BipartiteDetection(new UndirectedGraphReader<>(UndirectedAdjSet::new).read("fun/fengwk/learning/algorithm/graph/undirected/graph4"));
        System.out.println(bd.isBipartite());
    }

    @Test
    public void testBFS() {
        BFS bfs = new BFS(new UndirectedGraphReader<>(UndirectedAdjSet::new).read("fun/fengwk/learning/algorithm/graph/undirected/graph4"));
        assert bfs.order().equals(Arrays.asList(0, 1, 2, 3, 4, 6, 5));
    }

    @Test
    public void testUSSSPath() {
        USSSPath usssPath = new USSSPath(new UndirectedGraphReader<>(UndirectedAdjSet::new).read("fun/fengwk/learning/algorithm/graph/undirected/graph4"), 0);
        assert usssPath.path(5).equals(Arrays.asList(0, 2, 6, 5));
    }

    @Test
    public void testFindBridge() {
        FindBridges fb = new FindBridges(new UndirectedGraphReader<>(UndirectedAdjSet::new).read("fun/fengwk/learning/algorithm/graph/undirected/graph5"));
        System.out.println(fb.bridges());

        fb = new FindBridges(new UndirectedGraphReader<>(UndirectedAdjSet::new).read("fun/fengwk/learning/algorithm/graph/undirected/graph6"));
        System.out.println(fb.bridges());
    }

    @Test
    public void testFindCutPoints() {
        FindCutPoints fcp = new FindCutPoints(new UndirectedGraphReader<>(UndirectedAdjSet::new).read("fun/fengwk/learning/algorithm/graph/undirected/graph5"));
        System.out.println(fcp.cutPoints());

        fcp = new FindCutPoints(new UndirectedGraphReader<>(UndirectedAdjSet::new).read("fun/fengwk/learning/algorithm/graph/undirected/graph6"));
        System.out.println(fcp.cutPoints());

        fcp = new FindCutPoints(new UndirectedGraphReader<>(UndirectedAdjSet::new).read("fun/fengwk/learning/algorithm/graph/undirected/graph7"));
        System.out.println(fcp.cutPoints());
    }

    @Test
    public void testHamiltonLoop() {
        HamiltonLoop hl = new HamiltonLoop(new UndirectedGraphReader<>(UndirectedAdjSet::new).read("fun/fengwk/learning/algorithm/graph/undirected/graph8"));
        System.out.println(hl.path());
    }

    @Test
    public void testBitMap() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(100000);
        System.out.println(bs.get(0));
        System.out.println(bs.get(1));
        System.out.println(bs.get(100000));
    }

    @Test
    public void testEulerLoop() {
        EulerLoop el = new EulerLoop(new UndirectedGraphReader<>(UndirectedAdjSet::new).read("fun/fengwk/learning/algorithm/graph/undirected/graph9"));
        System.out.println(el.loop());
    }

}
