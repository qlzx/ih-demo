package com.example.demo.algorithms.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author lh0
 * @date 2021/12/4
 * @desc 请实现一个函数按照之字形顺序打印二叉树，即第一行按照从左到右的顺序打印，
 * 第二层按照从右到左的顺序打印，第三行再按照从左到右的顺序打印，其他行以此类推。
 */
public class Solution_offer_32_03 {

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int j = 0;

        List<List<Integer>> result = new ArrayList<>();

        while (!queue.isEmpty()) {

            List<Integer> ans = new ArrayList<>();
            for (int i = queue.size(); i > 0; i--) {
                TreeNode node = queue.poll();

                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }

                ans.add(node.val);
            }
            if (j % 2 != 0) {
                Collections.reverse(ans);
            }
            result.add(ans);
            j++;
        }

        return result;

    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) { val = x; }
    }
}
