package fun.fengwk.learning.algorithm.playdatastruct.bst;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * @author fengwk
 */
public class BTPrinter<N> {

    private Function<N, N> getLeft;
    private Function<N, N> getRight;

    public BTPrinter(Function<N, N> getLeft, Function<N, N> getRight) {
        this.getLeft = getLeft;
        this.getRight = getRight;
    }

    public String print(N node) {
        if (node == null) {
            return "EMPTY";
        }

        int maxLen = 0;
        List<List<String>> layers = new ArrayList<>();
        layers.add(new ArrayList<>());
        LinkedList<N> queue1 = new LinkedList<>();
        LinkedList<N> queue2 = new LinkedList<>();
        queue1.offer(node);
        while (!queue1.isEmpty()) {
            N cur = queue1.poll();
            if (cur == null) {
                layers.get(layers.size()-1).add(null);
                queue2.offer(null);
                queue2.offer(null);
            } else {
                String format = String.valueOf(cur);
                layers.get(layers.size()-1).add(format);
                maxLen = Math.max(format.length(), maxLen);
                queue2.offer(getLeft.apply(cur));
                queue2.offer(getRight.apply(cur));
            }

            if (queue1.isEmpty()) {
                boolean hasNext = false;
                for (N item : queue2) {
                    if (item != null) {
                        hasNext = true;
                        break;
                    }
                }

                if (hasNext) {
                    LinkedList<N> temp = queue1;
                    queue1 = queue2;
                    queue2 = temp;
                    layers.add(new ArrayList<>());
                }
            }
        }

        int colSize = pow(2, layers.size()) - 1;
        String[][] tab = new String[layers.size()][colSize];
        for (int i = 0; i < layers.size(); i++) {
            // i == l-1
            int step = (colSize - (pow(2, i) - 1)) / pow(2, i) / 2;
            // j指向下一个要填充的位置
            int j = step;
            for (String str : layers.get(i)) {
                if (str != null) {
                    tab[i][j] = str;
                }
                j += (step + 1 + step + 1);
            }
        }

        // 移动到左侧
//        for (int j = 0; j < tab[0].length; j++) {// j为列
//            for (int i = tab.length-1; i >= 0; i--) {// i为行
//                int mov = 0;
//                moveLoop: for (int k = j-1; k >= 0; k--) {// k为列
//                    for (int l = 0; l < tab.length; l++) {// l为行
//                        if (tab[l][k] != null) {
//                            break moveLoop;
//                        }
//                    }
//                    mov++;
//                }
//
//                if (mov > 0) {
//                    tab[i][j-mov] = tab[i][j];
//                    tab[i][j] = null;
//                }
//            }
//        }

        // 清理右侧
        int[] lens = new int[tab.length];
        for (int i = 0; i < tab.length; i++) {
            for (int j = tab[i].length-1; j >= 0; j--) {
                if (tab[i][j] != null) {
                    lens[i] = j+1;
                    break;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < lens[i]; j++) {
                if (tab[i][j] == null) {
                    sb.append(fill("", maxLen, ' '));
                } else {
                    sb.append(fill(tab[i][j], maxLen, ' '));
                }
            }
            if (i < tab.length - 1) {
                sb.append('\n');
            }
        }

        return sb.toString();
    }

    // a^b
    private int pow(int a, int b) {
        int res = 1;
        for (int i = 0; i < b; i++) {
            res *= a;
        }
        return res;
    }

    private String fill(String str, int len, char symbol) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        for (int l = str.length(); l <= len; l++) {
            sb.append(symbol);
        }
        return sb.toString();
    }

}
