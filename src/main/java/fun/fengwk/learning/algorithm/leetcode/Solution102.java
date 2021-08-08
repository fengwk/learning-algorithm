package fun.fengwk.learning.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fengwk
 */
public class Solution102 {

    public List<List<Integer>> inorderTraversal(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }

        List<List<Integer>> result = new LinkedList<>();
        List<Integer> layer = new LinkedList<>();
        LinkedList<TreeNode> queue1 = new LinkedList<>();
        LinkedList<TreeNode> queue2 = new LinkedList<>();
        queue1.offer(root);

        while (!queue1.isEmpty()) {
            TreeNode node = queue1.poll();
            layer.add(node.val);
            if (node.left != null) {
                queue2.offer(node.left);
            }
            if (node.right != null) {
                queue2.offer(node.right);
            }

            if (queue1.isEmpty()) {
                result.add(layer);
                layer = new ArrayList<>();
                LinkedList<TreeNode> temp = queue1;
                queue1 = queue2;
                queue2 = temp;
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
