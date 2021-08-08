package fun.fengwk.learning.algorithm.graph.undirected;

import fun.fengwk.learning.algorithm.graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fengwk
 */
public class BFS {

    private Graph G;
    private boolean[] visited;
    private List<Integer> order = new ArrayList<>();

    public BFS(Graph G) {
        this.G = G;
        this.visited = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!visited[v]) {
                bfs(v);
            }
        }
    }

    private void bfs(int v) {
        LinkedList<Integer> queue = new LinkedList<>();
        queue.offer(v);
        visited[v] = true;

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            order.add(cur);
            for (int next : G.adj(cur)) {
                if (!visited[next]) {
                    queue.offer(next);
                    visited[next] = true;
                }
            }
        }
    }

    public List<Integer> order() {
        return Collections.unmodifiableList(order);
    }

}
