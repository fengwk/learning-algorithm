package fun.fengwk.learning.algorithm.djh.brtree;

import org.junit.Test;

/**
 * @author fengwk
 */
public class BTreeTest {

    @Test
    public void test() {
        BTree<Integer, Integer> bTree = new BTree<>(4);

        for (int i = 0; i < 20; i++) {
            bTree.put(i, i);
        }

        System.out.println(bTree);

        System.out.println(bTree.get(10));

        bTree.remove(10);
        System.out.println(bTree);

        bTree.remove(9);
        System.out.println(bTree);

        for (int i = 0; i < 19; i++) {
            bTree.remove(i);
        }

        bTree.remove(19);

        System.out.println(bTree);
    }

}
