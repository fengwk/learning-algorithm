package fun.fengwk.learning.algorithm.graph.undirected;

import fun.fengwk.learning.algorithm.graph.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Connected Component
 *
 * @author fengwk
 */
public class CC {

    private Graph G;
    private int[] visited;
    private int ccCount;

    public CC(Graph G) {
        this.G = G;
        this.visited = new int[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (visited[v] == 0) {
                dfs(v, ++ccCount);
            }
        }
    }

    private void dfs(int v, int c) {
        visited[v] = c;

        for (int w : G.adj(v)) {
            if (visited[w] == 0) {
                dfs(w, c);
            }
        }
    }

    /**
     * 获取联通分量数量
     *
     * @return
     */
    public int count() {
        return ccCount;
    }

    /**
     * 判断顶点v和顶点w是否联通
     *
     * @param v
     * @param w
     * @return
     */
    public boolean isConnected(int v, int w) {
        G.checkVertex(v);
        G.checkVertex(w);

        return visited[v] == visited[w];
    }

    /**
     * 获取所有联通分量
     *
     * @return
     */
    public List<List<Integer>> components() {
        List<List<Integer>> components = new ArrayList<>();
        for (int i = 0; i < ccCount; i++) {
            components.add(new ArrayList<>());
        }

        for (int v = 0; v < visited.length; v++) {
            components.get(visited[v] - 1).add(v);
        }

        return components;
    }

}
