package fun.fengwk.learning.algorithm.leetcode;

/**
 * @author fengwk
 */
public class Solution695 {

    private static final int[][] NEXTS = new int[][] { {1, 0}, {0, 1}, {-1, 0}, {0, -1} };

    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int area = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    area = Math.max(area, dfs(i, j, grid, visited));
                }
            }
        }

        return area;
    }

    private int dfs(int i, int j, int[][] grid, boolean[][] visited) {
        visited[i][j] = true;
        int area = 1;

        for (int[] next : NEXTS) {
            int ni = i + next[0];
            int nj = j + next[1];
            if (inArea(ni, nj, grid) && grid[ni][nj] == 1 && !visited[ni][nj]) {
                area += dfs(ni, nj, grid, visited);
            }
        }

        return area;
    }

    private boolean inArea(int i, int j, int[][] grid) {
        return i >= 0 && i < grid.length && j >= 0 && j < grid[i].length;
    }

}
