package fun.fengwk.learning.algorithm.graph.undirected;

import fun.fengwk.learning.algorithm.graph.Graph;

import java.util.LinkedList;
import java.util.List;

/**
 * @author fengwk
 */
public class HamiltonLoop {

    private Graph G;
    private int[] visited;
    private LinkedList<Integer> path = new LinkedList<>();

    public HamiltonLoop(Graph G) {
        int[] visited = new int[G.V()];
        for (int v = 0; v < visited.length; v++) {
            visited[v] = -1;
        }

        this.G = G;
        this.visited = visited;

        if (G.V() > 0) {
            dfs(0, 0, G.V());
        }
    }

    // 从v出发寻找hamilton loop，如果找到则返回true
    private boolean dfs(int v, int parent, int left) {
        visited[v] = parent;
        path.addLast(v);
        left--;

        for (int w : G.adj(v)) {
            if (visited[w] == -1) {
                if (dfs(w, v, left)) {
                    return true;
                }
            } else if (w == 0 && left == 0) {
                return true;
            }
        }

        visited[v] = - 1;
        path.removeLast();
        return false;
    }

    public List<Integer> path() {
        return path;
    }

}
