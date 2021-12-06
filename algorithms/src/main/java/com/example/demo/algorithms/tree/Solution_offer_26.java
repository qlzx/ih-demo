package com.example.demo.algorithms.tree;

/**
 * @author lh0
 * @date 2021/12/6
 * @desc 数的子结构
 * https://leetcode-cn.com/problems/shu-de-zi-jie-gou-lcof/
 * <p>
 * 输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
 * <p>
 * B是A的子结构， 即 A中有出现和B相同的结构和节点值。
 */
public class Solution_offer_26 {

    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null) {
            return false;
        }
        return recur(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    private boolean recur(TreeNode treeNode, TreeNode b) {
        if (b == null) {
            return true;
        }
        if (treeNode == null || treeNode.val!=b.val) {
            return false;
        }
        return recur(treeNode.left, b.left) && recur(treeNode.right, b.right);

    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) { val = x; }
    }
}

