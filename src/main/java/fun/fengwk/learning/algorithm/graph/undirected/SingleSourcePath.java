package fun.fengwk.learning.algorithm.graph.undirected;

import fun.fengwk.learning.algorithm.graph.Graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 单源路径
 *
 * @author fengwk
 */
public class SingleSourcePath {

    private Graph G;
    private int s;

    /**
     * pre[v]代表从s出发到v顶点的前一个顶点是谁，如果是首个顶点那么指向自己
     */
    private int[] pre;

    public SingleSourcePath(Graph G, int s) {
        G.checkVertex(s);

        int[] pre = new int[G.V()];
        for (int i = 0; i < pre.length; i++) {
            pre[i] = -1;
        }

        this.G = G;
        this.s = s;
        this.pre = pre;

        dfs(s, s);
    }

    private void dfs(int v, int prev) {
        pre[v] = prev;

        for (int w : G.adj(v)) {
            if (pre[w] == -1) {
                dfs(w, v);
            }
        }
    }

    /**
     * 获取s到v的路径，如果不存在路径返回空列表
     *
     * @param v
     * @return
     */
    public List<Integer> path(int v) {
        if (pre[v] == -1) {
            return Collections.emptyList();
        }

        LinkedList<Integer> stack = new LinkedList<>();
        while (pre[v] != v) {
            stack.push(v);
            v = pre[v];
        }
        stack.push(v);
        return stack;
    }

}
