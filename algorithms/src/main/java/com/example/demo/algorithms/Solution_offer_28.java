package com.example.demo.algorithms;

/**
 * @author lh0
 * @date 2021/12/6
 * @desc 对称的二叉树
 * https://leetcode-cn.com/problems/dui-cheng-de-er-cha-shu-lcof/
 *
 * 请实现一个函数，用来判断一棵二叉树是不是对称的。如果一棵二叉树和它的镜像一样，那么它是对称的。
 */
public class Solution_offer_28 {

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return false;
        }
        return recure(root.left, root.right);

    }

    private boolean recure(TreeNode left,TreeNode right){
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null || left.val != right.val) {
            return false;
        }
        return recure(left.left, right.right) && recure(left.right, right.left);
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) { val = x; }
    }
}
