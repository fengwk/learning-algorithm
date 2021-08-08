package fun.fengwk.learning.algorithm.playdatastruct.unionfind;

/**
 * @author fengwk
 */
public class UnionFind {

    private int[] parent;
    private int[] rank;

    public UnionFind(int capacity) {
        parent = new int[capacity];
        rank = new int[capacity];

        for (int i = 0; i < capacity; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    // 查找i所属的集合
    private int find(int i) {
        while (i != parent[i]) {
            parent[i] = parent[parent[i]];
            i = parent[i];
        }
        return i;
    }

    public boolean isConnected(int i, int j) {
        return find(i) == find(j);
    }

    public void union(int i, int j) {
        int iRoot = find(i);
        int jRoot = find(j);

        if (iRoot != jRoot) {
            if (rank[iRoot] < rank[jRoot]) {
                parent[iRoot] = jRoot;
            } else if (rank[iRoot] > rank[jRoot]) {
                parent[jRoot] = iRoot;
            } else {
                parent[jRoot] = iRoot;
                rank[iRoot]++;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int v = 0; v < parent.length; v++) {
            sb.append(v);
            if (v < parent.length - 1) {
                sb.append('\t');
            }
        }
        sb.append('\n');
        for (int v = 0; v < parent.length; v++) {
            sb.append(parent[v]);
            if (v < parent.length - 1) {
                sb.append('\t');
            }
        }
        return sb.toString();
    }

}
