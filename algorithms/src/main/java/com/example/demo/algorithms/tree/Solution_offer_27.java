package com.example.demo.algorithms.tree;

/**
 * @author lh0
 * @date 2021/12/6
 * @desc 二叉树的镜像
 * https://leetcode-cn.com/problems/er-cha-shu-de-jing-xiang-lcof/
 */
public class Solution_offer_27 {

    // 递归  栈｜队列
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        mirrorTree(root.left);
        mirrorTree(root.right);

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        return root;
    }

    //public TreeNode mirrorTree(TreeNode root) {
    //    if(root==null){
    //        return null;
    //    }
    //    Queue<TreeNode> queue= new LinkedList<>();
    //
    //    queue.offer(root);
    //    while(!queue.isEmpty()){
    //        TreeNode node = queue.poll();
    //
    //        if(node.right!=null){
    //            queue.offer(node.right);
    //        }
    //        if(node.left!=null){
    //            queue.offer(node.left);
    //        }
    //
    //        TreeNode temp = node.left;
    //        node.left = node.right;
    //        node.right = temp;
    //    }
    //    return root;
    //}

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) { val = x; }
    }
}
