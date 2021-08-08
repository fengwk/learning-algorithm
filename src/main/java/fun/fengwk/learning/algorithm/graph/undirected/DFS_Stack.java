package fun.fengwk.learning.algorithm.graph.undirected;

import fun.fengwk.learning.algorithm.graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 深度优先遍历
 *
 * @author fengwk
 */
public class DFS_Stack {

    private Graph G;
    private boolean[] visited;
    private List<Integer> order = new ArrayList<>();

    public DFS_Stack(Graph G) {
        this.G = G;
        this.visited = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!visited[v]) {
                dfs(v);
            }
        }
    }

    private void dfs(int v) {
        LinkedList<Integer> stack = new LinkedList<>();
        stack.push(v);
        visited[v] = true;

        while (!stack.isEmpty()) {
            int cur = stack.pop();
            order.add(cur);
            for (int next : G.adj(cur)) {
                if (!visited[next]) {
                    stack.push(next);
                    visited[next] = true;
                }
            }
        }
    }

    public List<Integer> order() {
        return Collections.unmodifiableList(order);
    }

}
