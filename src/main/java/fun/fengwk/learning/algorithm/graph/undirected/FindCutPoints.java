package fun.fengwk.learning.algorithm.graph.undirected;

import fun.fengwk.learning.algorithm.graph.Graph;

import java.util.HashSet;
import java.util.Set;

/**
 * @author fengwk
 */
public class FindCutPoints {

    private Graph G;
    private Set<Integer> cutPoints = new HashSet<>();
    private int[] ord;
    private int[] low;
    private boolean visited[];
    private int ordId;

    public FindCutPoints(Graph G) {

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

        int child = 0;

        for (int w : G.adj(v)) {
            if (!visited[w]) {
                dfs(w, v);
                if (low[v] > low[w]) {
                    low[v] = low[w];
                }

                if (v != parent && low[w] >= ord[v]) {
                    cutPoints.add(v);
                }

                child++;
                if (v == parent && child == 2) {
                    cutPoints.add(v);
                }

            } else if (w != parent && low[v] > low[w]) {
                low[v] = ord[w];
            }
        }

    }

    public Set<Integer> cutPoints() {
        return cutPoints;
    }
}
