package fun.fengwk.learning.algorithm.graph.directed;

import fun.fengwk.learning.algorithm.graph.Graph;

import java.io.InputStream;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author fengwk
 */
public class DirectedAdjSet implements Graph {

    private int V;
    private int E;
    private TreeSet<Integer>[] adj;
    private int[] inDegree;
    private int[] outDegree;

    public DirectedAdjSet(int V) {
        if (V <= 0) {
            throw new IllegalArgumentException("V cannot be negative");
        }

        TreeSet<Integer>[] adj = new TreeSet[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new TreeSet<>();
        }

        this.V = V;
        this.adj = adj;
        this.inDegree = new int[V];
        this.outDegree = new int[V];
    }

    public static DirectedAdjSet read(String classpath) {
        return read(ClassLoader.getSystemResourceAsStream(classpath));
    }

    public static DirectedAdjSet read(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        int V = scanner.nextInt();
        int E = scanner.nextInt();

        DirectedAdjSet g = new DirectedAdjSet(V);
        for (int i = 0; i < E; i++) {
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            g.addEdge(v, w);
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

        return adj[v].contains(w);
    }

    public void addEdge(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        if (v == w) {
            throw new IllegalStateException("Self-looped edges are not allowed");
        }
        if (adj[v].contains(w)) {
            throw new IllegalStateException("Parallel edges are not allowed");
        }

        adj[v].add(w);
        outDegree[v]++;
        inDegree[w]++;
        E++;
    }

    public void removeEdge(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        if (!adj[v].contains(w)) {
            throw new IllegalStateException(String.format("Edge %d-%d doesn't exists", v, w));
        }

        adj[v].remove(w);
        outDegree[v]--;
        inDegree[w]--;
        E--;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        checkVertex(v);

        return Collections.unmodifiableSet(adj[v]);
    }

    public int outDegree(int v) {
        checkVertex(v);

        return outDegree[v];
    }

    public int inDegree(int v) {
        checkVertex(v);

        return inDegree[v];
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
            for (int w : adj[v]) {
                sb.append(w);
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
