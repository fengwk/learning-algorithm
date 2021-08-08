package fun.fengwk.learning.algorithm.graph.directed;

/**
 * @author fengwk
 */
public class DirectedCycleDetection {

    private DirectedAdjSet G;
    private boolean[] visited;
    private boolean[] curPath;
    private boolean hasCycle;

    public DirectedCycleDetection(DirectedAdjSet G) {

        this.G = G;
        visited = new boolean[G.V()];
        curPath = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!visited[v] && dfs(v)) {
                hasCycle = true;
                break;
            }
        }
    }

    private boolean dfs(int v) {
        visited[v] = true;
        curPath[v] = true;
        for (int w : G.adj(v)) {
            if (!visited[w]) {
                if (dfs(w)) {
                    return true;
                }
            } else if (curPath[w]) {
                return true;
            }
        }
        curPath[v] = false;
        return false;
    }

    public boolean hasCycle() {
        return hasCycle;
    }

}
