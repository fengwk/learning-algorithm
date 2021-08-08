package fun.fengwk.learning.algorithm.playdatastruct.tree;

import fun.fengwk.learning.algorithm.playdatastruct.bst.BST;
import org.junit.Test;

import java.util.Random;

/**
 * @author fengwk
 */
public class BSTTest {

    @Test
    public void test() {
        BST<Integer, Integer> bst = new BST<>();
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
        System.out.println("----------------------------------------------");

        bst.remove(7);
        bst.remove(5);

        System.out.println(bst);
        System.out.println(bst.size());
    }

    @Test
    public void test2() {
        BST<Integer, Integer> bst = new BST<>();

        Random random = new Random(1L);

        for (int i = 0; i < 20; i++) {
            int n = random.nextInt(10000);
            bst.put(n, n);
        }

        System.out.println(bst);

        System.out.println(bst.floor(2170));
    }

}
