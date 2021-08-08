package fun.fengwk.learning.algorithm.leetcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author fengwk
 */
public class Solution773 {

    private static final int[][] DIRS = new int[][] { {1, 0}, {0, 1}, {-1, 0}, {0, -1} };

    public int slidingPuzzle(int[][] board) {
        Map<String, Integer> visited = new HashMap<>();
        LinkedList<String> queue = new LinkedList<>();
        String boardStr = board2str(board);
        queue.offer(boardStr);
        visited.put(boardStr, 0);

        loop: while (!queue.isEmpty()) {
            String cur = queue.poll();

            if (cur.equals("123450")) {
                return visited.get(cur);
            }

            int[][] curBoard = str2Board(cur);

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    if (curBoard[i][j] == 0) {
                        for (int[] dir : DIRS) {
                            int ni = i + dir[0];
                            int nj = j + dir[1];
                            if (inArea(ni, nj)) {
                                int temp = curBoard[i][j];
                                curBoard[i][j] = curBoard[ni][nj];
                                curBoard[ni][nj] = temp;
                                String next = board2str(curBoard);
                                curBoard[ni][nj] = curBoard[i][j];
                                curBoard[i][j] = temp;
                                if (!visited.containsKey(next)) {
                                    queue.offer(next);
                                    visited.put(next, visited.get(cur) + 1);
                                }
                            }
                        }

                        continue loop;
                    }
                }
            }
        }

        return -1;
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < 2 && j >= 0 && j < 3;
    }

    private String board2str(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(board[i][j]);
            }
        }
        return sb.toString();
    }

    private int[][] str2Board(String boardStr) {
        int[][] board = new int[2][3];
        for (int i = 0; i < 6; i++) {
            board[i / 3][i % 3] = Character.digit(boardStr.charAt(i), 10);
        }
        return board;
    }

}
