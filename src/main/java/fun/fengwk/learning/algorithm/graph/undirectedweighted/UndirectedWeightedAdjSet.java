package fun.fengwk.learning.algorithm.graph.undirectedweighted;

import fun.fengwk.learning.algorithm.graph.Graph;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author fengwk
 */
public class UndirectedWeightedAdjSet implements Graph {

    private int V;
    private int E;
    private TreeMap<Integer, Integer>[] adj;

    public UndirectedWeightedAdjSet(int V) {
        if (V <= 0) {
            throw new IllegalArgumentException("V cannot be negative");
        }

        TreeMap<Integer, Integer>[] adj = new TreeMap[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new TreeMap<>();
        }

        this.V = V;
        this.adj = adj;
    }

    public static UndirectedWeightedAdjSet read(String classpath) {
        return read(ClassLoader.getSystemResourceAsStream(classpath));
    }

    public static UndirectedWeightedAdjSet read(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        int V = scanner.nextInt();
        int E = scanner.nextInt();

        UndirectedWeightedAdjSet g = new UndirectedWeightedAdjSet(V);
        for (int i = 0; i < E; i++) {
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            int weight = scanner.nextInt();
            g.addEdge(v, w, weight);
        }

        return g;
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

        return adj[v].containsKey(w);
    }

    public void addEdge(int v, int w, int weight) {
        checkVertex(v);
        checkVertex(w);

        if (v == w) {
            throw new IllegalStateException("Self-looped edges are not allowed");
        }
        if (adj[v].containsKey(w)) {
            throw new IllegalStateException("Parallel edges are not allowed");
        }

        adj[v].put(w, weight);
        adj[w].put(v, weight);
        E++;
    }

    public void removeEdge(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        if (!adj[v].containsKey(w)) {
            throw new IllegalStateException(String.format("Edge %d-%d doesn't exists", v, w));
        }

        adj[v].remove(w);
        adj[w].remove(v);
        E--;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        checkVertex(v);

        return Collections.unmodifiableSet(adj[v].keySet());
    }

    /**
     * 获取顶点v与顶点w的权值
     *
     * @param v
     * @param w
     * @return
     */
    public int getWeight(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        return adj[v].get(w);
    }

    public int degree(int v) {
        checkVertex(v);

        return adj[v].size();
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

        for (int v = 0; v < V; v++) {
            sb.append(v).append(':').append('\t');
            int i = 0;
            for (Map.Entry<Integer, Integer> e : adj[v].entrySet()) {
                sb.append('(').append(e.getKey()).append(':').append(e.getValue()).append(')');
                if (i < adj[v].size() - 1) {
                    sb.append('\t');
                }
                i++;
            }
            if (v < V - 1) {
                sb.append('\n');
            }
        }

        return sb.toString();
    }

}
