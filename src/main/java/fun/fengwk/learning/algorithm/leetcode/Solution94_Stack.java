package fun.fengwk.learning.algorithm.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author fengwk
 */
public class Solution94_Stack {

    public List<Integer> inorderTraversal(TreeNode root) {
        LinkedList<Integer> result = new LinkedList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();
        pushLeftChain(root, stack);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            pushLeftChain(node.right, stack);
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
