package fun.fengwk.learning.algorithm.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author fengwk
 */
public class Solution145_Stack2 {

    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> result = new LinkedList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode prev = null;
        pushLeftChain(root, stack);

        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            if (node.right == null || node.right == prev) {
                stack.pop();
                result.add(node.val);
                prev = node;
            } else {
                pushLeftChain(node.right, stack);
            }
        }

        return result;
    }

    private void pushLeftChain(TreeNode node, LinkedList<TreeNode> stack) {
        while (node != null) {
            stack.push(node);
            node = node.left;
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
