package fun.fengwk.learning.algorithm.graph.undirectedweighted;

import java.util.Arrays;

/**
 * 迪杰斯特拉算法，不能处理负权边
 *
 * @author fengwk
 */
public class Dijkstra_NonPQ {

    private UndirectedWeightedAdjSet G;
    private boolean[] visited;
    private int[] dis;
    private int s;

    public Dijkstra_NonPQ(UndirectedWeightedAdjSet G, int s) {
        G.checkVertex(s);

        boolean[] visited = new boolean[G.V()];
        int[] dis = new int[G.V()];
        Arrays.fill(dis, Integer.MAX_VALUE);

        this.G = G;
        this.s = s;
        this.visited = visited;
        this.dis = dis;

        int v = s;
        visited[v] = true;
        dis[v] = 0;

        while (true) {
            for (int w : G.adj(v)) {
                if (!visited[w] && dis[v] + G.getWeight(v, w) < dis[w]) {
                    dis[w] = dis[v] + G.getWeight(v, w);
                }
            }

            int minDis = Integer.MAX_VALUE;
            int minV = -1;
            for (v = 0; v < G.V(); v++) {
                if (!visited[v] && dis[v] < minDis) {
                    minDis = dis[v];
                    minV = v;
                }
            }

            if (minV == -1) {
                break;
            }

            v = minV;
            visited[v] = true;
        }

    }

    public boolean isConnected(int v) {
        G.checkVertex(v);
        return visited[v];
    }

    public int dis(int v) {
        G.checkVertex(v);
        return dis[v];
    }

}
