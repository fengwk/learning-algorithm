package fun.fengwk.learning.algorithm.graph.undirected;

import java.util.ArrayList;
import java.util.List;

/**
 * 无向临接矩阵（只考虑简单图），Adjacent Matrix
 *
 * @author fengwk
 */
public class UndirectedAdjMatrix implements UndirectedGraph {

    /**
     * 顶点数
     */
    private int V;

    /**
     * 边数
     */
    private int E;

    /**
     * adj[i][j]为true表示顶点i与顶点j存在边，
     * 在无向图中应该满足adj[i][j]==adj[j][i]
     */
    private boolean[][] adj;

    /**
     * 构建无向邻接矩阵
     *
     * @param V
     */
    public UndirectedAdjMatrix(int V) {
        if (V <= 0) {
            throw new IllegalArgumentException("V cannot be negative");
        }

        this.V = V;
        this.adj = new boolean[V][V];
    }

    @Override
    public int V() {
        return V;
    }

    @Override
    public int E() {
        return E;
    }

    @Override
    public boolean hasEdge(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        return adj[v][w];
    }

    @Override
    public void addEdge(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        if (v == w) {
            throw new IllegalStateException("Self-looped edges are not allowed");
        }
        if (adj[v][w]) {
            throw new IllegalStateException("Parallel edges are not allowed");
        }

        adj[v][w] = adj[w][v] = true;
        E++;
    }

    @Override
    public void removeEdge(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        if (!adj[v][w]) {
            throw new IllegalStateException(String.format("Edge %d-%d doesn't exists", v, w));
        }

        adj[v][w] = adj[w][v] = false;
        E--;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        checkVertex(v);

        List<Integer> result = new ArrayList<>();
        for (int w = 0; w < V; w++) {
            if (adj[v][w]) {
                result.add(w);
            }
        }
        return result;
    }

    @Override
    public int degree(int v) {
        checkVertex(v);

        int degree = 0;
        for (int w = 0; w < V; w++) {
            if (adj[v][w]) {
                degree++;
            }
        }
        return degree;
    }

    @Override
    public void checkVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException(String.format("Vertex %d is out of bounds", v));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // 顶部
        sb.append(' ').append('\t');
        for (int v = 0; v < V; v++) {
            sb.append(v);
            if (v < V - 1) {
                sb.append('\t');
            }
        }
        sb.append('\n');

        for (int v = 0; v < V; v++) {
            // 左侧
            sb.append(v).append('\t');

            // 内容
            for (int w = 0; w < V; w++) {
                sb.append(adj[v][w] ? 1 : 0);
                if (w < V - 1) {
                    sb.append('\t');
                }
            }
            if (v < V - 1) {
                sb.append('\n');
            }
        }

        return sb.toString();
    }

}
