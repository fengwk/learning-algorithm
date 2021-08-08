package fun.fengwk.learning.algorithm.graph.undirectedweighted;

import java.util.Arrays;

/**
 * @author fengwk
 */
public class Floyed {

    private UndirectedWeightedAdjSet G;
    private int[][] dis;
    private boolean hasNegativeCycle = false;

    public Floyed(UndirectedWeightedAdjSet G) {

        this.G = G;

        int[][] dis = new int[G.V()][G.V()];
        for (int i = 0; i < dis.length; i++) {
            Arrays.fill(dis[i], Integer.MAX_VALUE);
        }
        for (int v = 0; v < G.V(); v++) {
            dis[v][v] = 0;
            for (int w : G.adj(v)) {
                dis[v][w] = G.getWeight(v, w);
            }
        }

        for (int t = 0; t < G.V(); t++) {
            for (int v = 0; v < G.V(); v++) {
                for (int w = 0; w < G.V(); w++) {
                    if (dis[v][t] != Integer.MAX_VALUE && dis[t][w] != Integer.MAX_VALUE && dis[v][t] + dis[t][w] < dis[v][w]) {
                        dis[v][w] = dis[v][t] + dis[t][w];
                    }
                }
            }
        }

        for (int v = 0; v < G.V(); v++) {
            if (dis[v][v] < 0) {
                hasNegativeCycle = true;
            }
        }

        this.dis = dis;
    }

    public boolean isConnected(int v, int w) {
        G.checkVertex(v);
        return dis[v][w] != Integer.MAX_VALUE;
    }

    public int dis(int v, int w) {
        G.checkVertex(v);

        if (hasNegativeCycle) {
            throw new IllegalStateException("exists negative cycle");
        }

        return dis[v][w];
    }

    public boolean hasNegativeCycle() {
        return hasNegativeCycle;
    }

}
