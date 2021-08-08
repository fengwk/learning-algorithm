package fun.fengwk.learning.algorithm.graph.directed;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fengwk
 */
public class TopoSort {

    private DirectedAdjSet G;
    private List<Integer> result = new ArrayList<>();

    public TopoSort(DirectedAdjSet G) {

        this.G = G;

        LinkedList<Integer> queue = new LinkedList<>();
        int[] inDegrees = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            inDegrees[v] = G.inDegree(v);
            if (inDegrees[v] == 0) {
                queue.offer(v);
            }
        }

        while (!queue.isEmpty()) {
            int v = queue.poll();
            result.add(v);
            for (int w : G.adj(v)) {
                if (inDegrees[w] > 0) {
                    inDegrees[w]--;
                    if (inDegrees[w] == 0) {
                        queue.offer(w);
                    }
                }
            }
        }

    }

    public boolean hasCycle() {
        return result.size() != G.V();
    }

    public List<Integer> result() {
        if (hasCycle()) {
            throw new IllegalStateException("has cycle");
        }

        return result;
    }
}
