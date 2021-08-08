package fun.fengwk.learning.algorithm.graph.undirected;

import java.util.LinkedList;
import java.util.List;

/**
 * @author fengwk
 */
public class EulerLoop {

    private UndirectedGraph G;
    private boolean hasEulerLoop;
    private LinkedList<Integer> loop = new LinkedList<>();

    public EulerLoop(UndirectedGraph G) {
        this.G = G;
        this.hasEulerLoop = hasEulerLoop0();

        if (hasEulerLoop && G.V() > 0) {

            LinkedList<Integer> curPath = new LinkedList<>();
            int cur = 0;
            curPath.push(cur);

            while (!curPath.isEmpty()) {
                if (G.degree(cur) > 0) {
                    curPath.push(cur);
                    int w = G.adj(cur).iterator().next();
                    G.removeEdge(cur, w);
                    cur = w;
                } else {
                    loop.push(cur);
                    cur = curPath.pop();
                }
            }
        }
    }

    private boolean hasEulerLoop0() {
        if (new CC(G).count() > 1) {
            return false;
        }

        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) % 2 != 0) {
                return false;
            }
        }

        return true;
    }

    public boolean hasEulerLoop() {
        return hasEulerLoop;
    }

    public List<Integer> loop() {
        return loop;
    }

}
