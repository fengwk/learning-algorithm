package fun.fengwk.learning.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengwk
 */
public class Solution94 {

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> collector = new ArrayList<>();
        doInorderTraversal(root, collector);
        return collector;
    }

    private void doInorderTraversal(TreeNode node, List<Integer> collector) {
        if (node != null) {
            doInorderTraversal(node.left, collector);
            collector.add(node.val);
            doInorderTraversal(node.right, collector);
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
