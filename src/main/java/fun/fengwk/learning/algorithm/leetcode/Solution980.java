package fun.fengwk.learning.algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author fengwk
 */
public class Solution980 {

    private static final int[][] DIRS = new int[][] { {1, 0}, {0, 1}, {-1, 0}, {0, -1} };

    public int uniquePathsIII(int[][] grid) {
        int R = grid.length;
        int C = grid[0].length;
        int begin = -1, end = -1;
        int left = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (grid[i][j] == 1) {
                    begin = i * C + j;
                } else if (grid[i][j] == 2) {
                    end = i * C + j;
                }

                if (grid[i][j] != -1) {
                    left++;
                }
            }
        }

        Set<Integer> visited = new HashSet<>();
        return dfs(begin, end, left, R, C, grid, visited);
    }

    // dfs从v到end并且访问所有点的路径有几条
    private int dfs(int v, int end, int left, int R, int C, int[][] grid, Set<Integer> visited) {
        if (v == end && left == 1) {
            return 1;
        }

        visited.add(v);
        left--;
        int count = 0;

        int x = v / C;
        int y = v % C;
        for (int[] dir : DIRS) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (nx >= 0 && nx < R && ny >= 0 && ny < C && grid[nx][ny] != -1) {
                int w = nx * C + ny;
                if (!visited.contains(w)) {
                    count += dfs(w, end, left, R, C, grid, visited);
                }
            }
        }

        visited.remove(v);
        return count;
    }

}
