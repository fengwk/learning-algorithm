package fun.fengwk.learning.algorithm.graph.undirectedweighted;

/**
 * @author fengwk
 */
public class BellmanFord {

    private UndirectedWeightedAdjSet G;
    private int s;
    private Integer[] dis;
    private boolean hasNegativeCycle;

    public BellmanFord(UndirectedWeightedAdjSet G, int s) {
        G.checkVertex(s);

        this.G = G;
        this.s = s;

        Integer[] dis = new Integer[G.V()];
        dis[s] = 0;

        for (int i = 1; i < G.V(); i++) {

            for (int v = 0; v < G.V(); v++) {
                for (int w : G.adj(v)) {
                    // relax
                    if (dis[w] == null || (dis[v] != null && dis[v] + G.getWeight(v, w) < dis[w])) {
                        dis[w] = dis[v] + G.getWeight(v, w);
                    }
                }
            }

        }

        boolean hasNegativeCycle = false;
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (dis[w] == null || (dis[v] != null && dis[v] + G.getWeight(v, w) < dis[w])) {
                    hasNegativeCycle = true;
                }
            }
        }

        this.dis = dis;
        this.hasNegativeCycle = hasNegativeCycle;
    }

    public boolean isConnected(int v) {
        G.checkVertex(v);
        return dis[v] != null;
    }

    public int dis(int v) {
        G.checkVertex(v);

        if (hasNegativeCycle) {
            throw new IllegalStateException("exists negative cycle");
        }

        return dis[v];
    }

    public boolean hasNegativeCycle() {
        return hasNegativeCycle;
    }

}
