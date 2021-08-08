package fun.fengwk.learning.algorithm.leetcode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fengwk
 */
public class Solution144_Stack {

    public List<Integer> preorderTraversalWithRecursive(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }

        LinkedList<Integer> result = new LinkedList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return result;
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
