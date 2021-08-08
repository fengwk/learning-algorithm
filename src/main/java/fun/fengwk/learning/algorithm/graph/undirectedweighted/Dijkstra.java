package fun.fengwk.learning.algorithm.graph.undirectedweighted;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 迪杰斯特拉算法，不能处理负权边
 *
 * @author fengwk
 */
public class Dijkstra {

    private UndirectedWeightedAdjSet G;
    private boolean[] visited;
    private int[] dis;
    private int s;
    private int[] pre;

    public Dijkstra(UndirectedWeightedAdjSet G, int s) {
        G.checkVertex(s);

        // visited[v]表示顶点v是否已计算出最短路径了
        boolean[] visited = new boolean[G.V()];
        // dis代表从s出发到顶点v的最短路径是dis[v]
        int[] dis = new int[G.V()];
        Arrays.fill(dis, Integer.MAX_VALUE);

        int[] pre = new int[G.V()];
        Arrays.fill(pre, -1);

        this.G = G;
        this.s = s;
        this.visited = visited;
        this.dis = dis;
        this.pre = pre;

        // pq存放所有可能顶点的dis值，便于选取出其中最小的
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(s, 0));
        dis[s] = 0;
        pre[s] = 0;

        while (!pq.isEmpty()) {
            int v = pq.poll().v;
            if (!visited[v]) {
                for (int w : G.adj(v)) {
                    if (!visited[w] && dis[v] + G.getWeight(v, w) < dis[w]) {
                        dis[w] = dis[v] + G.getWeight(v, w);
                        pq.offer(new Node(w, dis[w]));
                        pre[w] = v;
                    }
                }
            }
        }

    }

    public boolean isConnected(int v) {
        G.checkVertex(v);
        return visited[v];
    }

    public int dis(int v) {
        G.checkVertex(v);
        return dis[v];
    }

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

    static class Node implements Comparable<Node> {
        int v;
        int dis;

        Node(int v, int dis) {
            this.v = v;
            this.dis = dis;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(dis, o.dis);
        }
    }

}
