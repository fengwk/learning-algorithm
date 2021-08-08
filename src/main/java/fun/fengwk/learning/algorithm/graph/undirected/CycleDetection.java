package fun.fengwk.learning.algorithm.graph.undirected;

import fun.fengwk.learning.algorithm.graph.Graph;

/**
 * 环检测
 *
 * @author fengwk
 */
public class CycleDetection {

    private Graph G;
    private boolean[] visited;
    private boolean hasCycle;

    public CycleDetection(Graph G) {
        boolean[] visited = new boolean[G.V()];

        this.G = G;
        this.visited = visited;

        for (int v = 0; v < G.V(); v++) {
            if (!visited[v]) {
                if (hasCycle = dfs(v, v)) {
                    break;
                }
            }
        }
    }

    private boolean dfs(int v, int parent) {
        visited[v] = true;
        for (int w : G.adj(v)) {
            if (!visited[w]) {
                if (dfs(w, v)) {
                    return true;
                }
            } else if (w != parent) {// visited[w] && w != parent
                return true;
            }
        }
        return false;
    }

    public boolean hasCycle() {
        return hasCycle;
    }

}
