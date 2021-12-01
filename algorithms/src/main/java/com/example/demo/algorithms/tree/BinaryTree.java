package com.example.demo.algorithms.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh0
 * @date 2021/11/30
 * @desc
 */
public class BinaryTree {

    private Node root;

    static class Node {
        private int data;

        private Node left;

        private Node right;

        public Node(int cal) {
            this.data = cal;
        }
    }

    public BinaryTree(Node node) {
        this.root = node;
    }

    public void add(int x) {
        Node insertNode = root;
        while (insertNode != null) {
            if (x <= insertNode.data) {
                if (insertNode.left == null) {
                    insertNode.left = new Node(x);
                    return;
                }else{
                    insertNode = insertNode.left;
                }
            }else{
                if (insertNode.right == null) {
                    insertNode.right = new Node(x);
                    return;
                }else{
                    insertNode = insertNode.right;
                }
            }
        }

    }

    public boolean remove(int x) {
        return false;
    }

    public Node find(int x) {
        Node curr = root;
        while (curr != null) {
            if (curr.data == x) {
                return curr;
            } else if (curr.data > x) {
                curr = curr.left;
            }else {
                curr = curr.right;
            }
        }
        return null;
    }

    public List<Node> iter1() {
        List<Node> list = new ArrayList<>();
        iter_before(root, list);
        return list;
    }

    private void iter_before(Node node,List<Node> list){
        if (node != null) {
            list.add(node);
            iter_before(node.left, list);
            iter_before(node.right, list);
        }
    }


    public static void main(String[] args) {

        Node root = new Node(10);

        BinaryTree binaryTree = new BinaryTree(root);
    }
}
