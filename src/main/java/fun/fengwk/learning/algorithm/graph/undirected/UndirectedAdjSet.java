package fun.fengwk.learning.algorithm.graph.undirected;

import java.util.Collections;
import java.util.TreeSet;

/**
 *
 * @author fengwk
 */
public class UndirectedAdjSet implements UndirectedGraph {

    private int V;
    private int E;
    private TreeSet<Integer>[] adj;

    public UndirectedAdjSet(int V) {
        if (V <= 0) {
            throw new IllegalArgumentException("V cannot be negative");
        }

        TreeSet<Integer>[] adj = new TreeSet[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new TreeSet<>();
        }

        this.V = V;
        this.adj = adj;
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

    @Override
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
        adj[w].add(v);
        E++;
    }

    @Override
    public void removeEdge(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        if (!adj[v].contains(w)) {
            throw new IllegalStateException(String.format("Edge %d-%d doesn't exists", v, w));
        }

        adj[v].remove(w);
        adj[w].remove(v);
        E--;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        checkVertex(v);

        return Collections.unmodifiableSet(adj[v]);
    }

    @Override
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
