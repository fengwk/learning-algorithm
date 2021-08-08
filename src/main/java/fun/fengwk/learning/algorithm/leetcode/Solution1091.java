package fun.fengwk.learning.algorithm.leetcode;

import java.util.LinkedList;

/**
 * @author fengwk
 */
public class Solution1091 {

    private static final int[][] DIRS = new int[][] { {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1} };

    public int shortestPathBinaryMatrix(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0 || grid[0][0] != 0) {
            return -1;
        }

        boolean[][] visited = new boolean[grid.length][grid[0].length];
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(new Node(0, 0, 1));
        visited[0][0] = true;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur.i == grid.length - 1 && cur.j == grid[cur.i].length - 1) {
                return cur.len;
            }

            for (int[] dir : DIRS) {
                int ni = cur.i + dir[0];
                int nj = cur.j + dir[1];
                if (inArea(ni, nj, grid) && grid[ni][nj] == 0 && !visited[ni][nj]) {
                    queue.offer(new Node(ni, nj, cur.len + 1));
                    visited[ni][nj] = true;
                }
            }
        }

        return -1;
    }

    private boolean inArea(int i, int j, int[][] grid) {
        return i >= 0 && i < grid.length && j >= 0 && j < grid[i].length;
    }

    static class Node {

        int i, j;
        int len;

        public Node(int i, int j, int len) {
            this.i = i;
            this.j = j;
            this.len = len;
        }
    }

}
