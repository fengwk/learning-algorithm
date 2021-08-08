package fun.fengwk.learning.algorithm.graph.undirected;

import fun.fengwk.learning.algorithm.graph.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengwk
 */
public class FindBridges {

    private Graph G;
    private List<Edge> bridges = new ArrayList<>();
    private int[] ord;
    private int[] low;
    private boolean visited[];
    private int ordId;

    public FindBridges(Graph G) {

        this.G = G;

        this.ord = new int[G.V()];
        this.low = new int[G.V()];
        this.visited = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!visited[v]) {
                dfs(v, v);
            }
        }

    }

    private void dfs(int v, int parent) {
        visited[v] = true;
        ord[v] = ordId;
        low[v] = ordId;
        ordId++;

        for (int w : G.adj(v)) {
            if (!visited[w]) {
                dfs(w, v);
                if (low[v] > low[w]) {
                    low[v] = low[w];
                }

                if (low[w] > ord[v]) {
                    bridges.add(new Edge(v, w));
                }

            } else if (w != parent && low[v] > low[w]) {
                low[v] = ord[w];
            }
        }

    }

    public List<Edge> bridges() {
        return bridges;
    }

    /**
     * 边
     */
    public static class Edge {

        public int v, w;

        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public String toString() {
            return String.format("%d-%d", v, w);
        }
    }

}
