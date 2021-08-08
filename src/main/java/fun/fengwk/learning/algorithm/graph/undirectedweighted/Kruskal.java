package fun.fengwk.learning.algorithm.graph.undirectedweighted;

import fun.fengwk.learning.algorithm.graph.undirected.CC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author fengwk
 */
public class Kruskal {

    private UndirectedWeightedAdjSet G;
    // Minimum spanning tree
    private List<WeightedEdge> mst = new ArrayList<>();

    public Kruskal(UndirectedWeightedAdjSet G) {
        this.G = G;

        if (new CC(G).count() == 1) {
            List<WeightedEdge> edges = new ArrayList<>();
            for (int v = 0; v < G.V(); v++) {
                for (int w : G.adj(v)) {
                    if (v < w) {
                        edges.add(new WeightedEdge(v, w, G.getWeight(v, w)));
                    }
                }
            }

            Collections.sort(edges);
            UnionFind uf = new UnionFind(G.V());
            for (WeightedEdge edge : edges) {
                if (!uf.isConnected(edge.v, edge.w)) {
                    uf.union(edge.v, edge.w);
                    mst.add(edge);
                }
            }

        }
    }

    public List<WeightedEdge> mst() {
        return mst;
    }

    public static class WeightedEdge implements Comparable<WeightedEdge> {

        int v, w;
        int weight;

        public WeightedEdge(int v, int w, int weight) {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        @Override
        public int compareTo(WeightedEdge o) {
            return Integer.compare(weight, o.weight);
        }

        @Override
        public String toString() {
            return String.format("%d-%d:%d", v, w, weight);
        }
    }

}
