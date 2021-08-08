package fun.fengwk.learning.algorithm.graph.undirectedweighted;

/**
 * @author fengwk
 */
public class UnionFind {

    private int[] uf;

    public UnionFind(int V) {
        uf = new int[V];
        for (int v = 0; v < V; v++) {
            uf[v] = v;
        }
    }

    public void union(int v, int w) {
        int vr = find(v);
        int wr = find(w);

        uf[wr] = vr;
    }

    public boolean isConnected(int v, int w) {
        return find(v) == find(w);
    }

    public int find(int v) {
        if (uf[v] == v) {
            return v;
        }

        uf[v] = find(uf[v]);
        return uf[v];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int v = 0; v < uf.length; v++) {
            sb.append(v);
            if (v < uf.length - 1) {
                sb.append('\t');
            }
        }
        sb.append('\n');
        for (int v = 0; v < uf.length; v++) {
            sb.append(uf[v]);
            if (v < uf.length - 1) {
                sb.append('\t');
            }
        }
        return sb.toString();
    }
}
