package fun.fengwk.learning.algorithm.playdatastruct.avl;

import org.junit.Test;

/**
 * @author fengwk
 */
public class AVLTest {

    @Test
    public void test() {
        AVL<Integer, Integer> avl = new AVL<>();

        for (int i = 0; i < 30; i++) {
            avl.put(i, i);
        }

        System.out.println(avl);
        System.out.println(avl.inorderTraverse());

        for (int i = 10; i < 20; i++) {
            avl.remove(i);
        }
        System.out.println(avl);
        System.out.println(avl.inorderTraverse());
    }

}
