package fun.fengwk.learning.algorithm.playdatastruct.unionfind;

import org.junit.Test;

/**
 * @author fengwk
 */
public class UnionFindTest {

    @Test
    public void test() {
        // 0 1 2 3 4 5

        UnionFind uf = new UnionFind(6);
        uf.union(0, 1);
        uf.union(2, 5);
        uf.union(1, 2);

        System.out.println(uf.isConnected(0, 5));
        System.out.println(uf);
    }

}
