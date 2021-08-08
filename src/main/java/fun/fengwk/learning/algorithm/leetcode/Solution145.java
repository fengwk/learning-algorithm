package fun.fengwk.learning.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengwk
 */
public class Solution145 {

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> collector = new ArrayList<>();
        doPostorderTraversal(root, collector);
        return collector;
    }

    private void doPostorderTraversal(TreeNode node, List<Integer> collector) {
        if (node != null) {
            doPostorderTraversal(node.left, collector);
            doPostorderTraversal(node.right, collector);
            collector.add(node.val);
        }
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}
