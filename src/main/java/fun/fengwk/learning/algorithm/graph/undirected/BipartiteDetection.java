package fun.fengwk.learning.algorithm.graph.undirected;

import fun.fengwk.learning.algorithm.graph.Graph;

/**
 * 二分图检测
 *
 * @author fengwk
 */
public class BipartiteDetection {

    private Graph G;
    private int[] colors;
    private boolean isBipartite;

    public BipartiteDetection(Graph G) {
        int[] colors = new int[G.V()];

        this.G = G;
        this.colors = colors;
        this.isBipartite = true;

        for (int v = 0; v < G.V(); v++) {
            if (colors[v] == 0) {
                if (!(isBipartite = dfs(v, 1))) {
                    break;
                }
            }
        }
    }

    // 发现不满足二分图返回false
    private boolean dfs(int v, int color) {
        colors[v] = color;
        for (int w : G.adj(v)) {
            if (colors[w] == 0) {
                if (!dfs(w, 3 - color)) {
                    return false;
                }
            } else if (colors[w] == color) {
                return false;
            }
        }
        return true;
    }

    public boolean isBipartite() {
        return isBipartite;
    }

}
