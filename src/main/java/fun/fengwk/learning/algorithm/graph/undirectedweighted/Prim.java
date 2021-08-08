package fun.fengwk.learning.algorithm.graph.undirectedweighted;

import fun.fengwk.learning.algorithm.graph.undirected.CC;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author fengwk
 */
public class Prim {

    private UndirectedWeightedAdjSet G;
    // Minimum spanning tree
    private List<WeightedEdge> mst = new ArrayList<>();

    public Prim(UndirectedWeightedAdjSet G) {
        this.G = G;

        if (new CC(G).count() == 1) {
            PriorityQueue<WeightedEdge> pq = new PriorityQueue<>();
            boolean[] visited = new boolean[G.V()];
            visited[0] = true;
            for (int w : G.adj(0)) {
                pq.offer(new WeightedEdge(0, w, G.getWeight(0, w)));
            }

            while (!pq.isEmpty()) {
                WeightedEdge edge = pq.poll();
                if (visited[edge.v] != visited[edge.w]) {
                    visited[edge.w] = true;
                    mst.add(edge);
                    for (int nextW : G.adj(edge.w)) {
                        if (!visited[nextW]) {
                            pq.offer(new WeightedEdge(edge.w, nextW, G.getWeight(edge.w, nextW)));
                        }
                    }
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
