package fun.fengwk.learning.algorithm.leetcode;

import java.util.LinkedList;

/**
 * @author fengwk
 */
public class Solution785 {

    public boolean isBipartite(int[][] graph) {
        if (graph == null || graph.length == 0) {
            return false;
        }

        int[] colors = new int[graph.length];
        LinkedList<Integer> stack = new LinkedList<>();

        for (int r = 0; r < graph.length; r++) {
            if (colors[r] == 0) {
                stack.offer(r);
                colors[r] = 1;
                while (!stack.isEmpty()) {
                    int v = stack.pop();
                    for (int w : graph[v]) {
                        if (colors[w] == 0) {
                            stack.offer(w);
                            colors[w] = 3 - colors[v];
                        } else if (colors[w] == colors[v]) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(new Solution785().isBipartite(new int[][] {{4},{},{4},{4},{0,2,3}}));
    }

}
