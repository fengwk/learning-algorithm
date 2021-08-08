package fun.fengwk.learning.algorithm.djh.rbtree;

import org.junit.Test;

/**
 * @author fengwk
 */
public class RBTreeTest {

    @Test
    public void test() {
        RBTree<Integer, Integer> rbTree = new RBTree<>();
        for (int i = 0; i < 20; i++) {
            rbTree.put(i, i);
        }

        System.out.println(rbTree);


        for (int i = 0; i < 20; i++) {
            System.out.println("------------remove" + i);
            rbTree.remove(i);
            System.out.println(rbTree);
            System.out.println(rbTree.size());
        }

    }

}
