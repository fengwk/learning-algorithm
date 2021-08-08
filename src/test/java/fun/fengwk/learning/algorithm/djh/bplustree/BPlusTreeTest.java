package fun.fengwk.learning.algorithm.djh.bplustree;

import org.junit.Test;

/**
 * @author fengwk
 */
public class BPlusTreeTest {

    @Test
    public void test() {
        BPlusTree<Integer, Integer> bplusTree = new BPlusTree<>(3);

        for (int i = 0; i < 20; i++) {
            bplusTree.put(i, i);
        }
        System.out.println(bplusTree);
        System.out.println(bplusTree.size());

        bplusTree.put(-1, -1);
        System.out.println("-----------");
        System.out.println(bplusTree);

        bplusTree.put(-2, -2);
        System.out.println("-----------");
        System.out.println(bplusTree);

        for (int i = -5; i < 20; i++) {
            bplusTree.remove(i);
            System.out.println("---------------remove" + i);
            System.out.println(bplusTree);
        }



//        bplusTree.put(9, 9);
//        System.out.println(bplusTree);


    }

}
