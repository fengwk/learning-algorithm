package fun.fengwk.learning.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengwk
 */
public class Solution144 {

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> collector = new ArrayList<>();
        doPreorderTraversal(root, collector);
        return collector;
    }

    private void doPreorderTraversal(TreeNode node, List<Integer> collector) {
        if (node != null) {
            collector.add(node.val);
            doPreorderTraversal(node.left, collector);
            doPreorderTraversal(node.right, collector);
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
