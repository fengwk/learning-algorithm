package fun.fengwk.learning.algorithm.graph.undirected;

import fun.fengwk.learning.algorithm.graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 深度优先遍历
 *
 * @author fengwk
 */
public class DFS {

    private Graph G;
    private boolean[] visited;
    private List<Integer> preorder = new ArrayList<>();
    private List<Integer> postorder = new ArrayList<>();

    public DFS(Graph G) {
        this.G = G;
        this.visited = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!visited[v]) {
                dfs(v);
            }
        }
    }

    private void dfs(int v) {
        visited[v] = true;
        preorder.add(v);

        for (int w : G.adj(v)) {
            if (!visited[w]) {
                dfs(w);
            }
        }

        postorder.add(v);
    }

    public List<Integer> preorder() {
        return Collections.unmodifiableList(preorder);
    }

    public List<Integer> postorder() {
        return Collections.unmodifiableList(postorder);
    }
}
