package fun.fengwk.learning.algorithm.playdatastruct.tree;

import fun.fengwk.learning.algorithm.playdatastruct.bst.BST_Recursive;
import org.junit.Test;

/**
 * @author fengwk
 */
public class BST_RecursiveTest {

    @Test
    public void test() {
        BST_Recursive<Integer, Integer> bst = new BST_Recursive<>();
        bst.put(5, 5);
        bst.put(2, 2);
        bst.put(7, 7);
        bst.put(1, 1);
        bst.put(3, 3);
        System.out.println(bst);
        System.out.println(bst.size());
        System.out.println(bst.floor(6));
        System.out.println(bst.floor(0));
        System.out.println(bst.floor(4));
        System.out.println("----------------------------------------------");

        bst.remove(2);
        System.out.println(bst);
        System.out.println("----------------------------------------------");

        System.out.println(bst.get(7));
        System.out.println(bst.size());
        System.out.println("----------------------------------------------");

        bst.remove(5);
        bst.remove(3);
        bst.remove(1);
        System.out.println(bst);
        System.out.println(bst.size());
        System.out.println("----------------------------------------------");

        bst.remove(7);
        bst.remove(5);

        System.out.println(bst);
        System.out.println(bst.size());
    }
}
