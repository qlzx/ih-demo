package com.example.demo.algorithms.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
        Node curr = root;
        while (curr != null) {
            if (x > curr.data) {
                if (curr.right == null) {
                    curr.right = new Node(x);
                    break;
                } else {
                    curr = curr.right;
                }
            } else {
                if (curr.left == null) {
                    curr.left = new Node(x);
                    break;
                } else {
                    curr = curr.left;
                }
            }
        }
    }

    public boolean remove(int x) {
        Node deleteNode = root;
        Node prev = null;
        while (deleteNode != null && deleteNode.data != x) {
            prev = deleteNode;
            if (deleteNode.data < x) {
                deleteNode = deleteNode.right;
            } else {
                deleteNode = deleteNode.left;
            }
        }

        if (deleteNode == null) {
            return false;
        }
        // 待删除节点有两个的情况
        if (deleteNode.left != null && deleteNode.right != null) {
            // 找到右子树的最小节点
            Node minNode = deleteNode.right;
            Node pNode = deleteNode;
            while (minNode.left != null) {
                pNode = minNode;
                minNode = minNode.left;
            }

            // 数据删除
            deleteNode.data = minNode.data;
            deleteNode = minNode;
            prev = pNode;
        }

        // 待删除节点只有一个或者没有子节点的情况
        Node child;
        if (deleteNode.left != null) {
            child = deleteNode.left;
        } else if (deleteNode.right != null) {
            child = deleteNode.right;
        } else {
            child = null;
        }

        if (prev == null) {
            root = child;
        } else if (prev.left == deleteNode) {
            prev.left = child;
        } else {
            prev.right = child;
        }
        return true;
    }

    public Node find(int x) {
        Node curr = root;
        while (curr != null) {
            if (curr.data == x) {
                return curr;
            } else if (curr.data > x) {
                curr = curr.left;
            } else {
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

    private void iter_before(Node node, List<Node> list) {
        if (node != null) {
            list.add(node);
            iter_before(node.left, list);
            iter_before(node.right, list);
        }
    }
    // parent = (i+1)/2 -1 = (i-1)/2
    // left = (i+1)*2-1 = 2*i +1
    // right = 2*i +2

    public List<Node> iterLevel() {
        List<Node> list = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            list.add(curr);

            if (curr.left != null) {
                queue.offer(curr.left);
            }

            if (curr.right != null) {
                queue.offer(curr.right);
            }
        }
        return list;
    }

    public static void main(String[] args) {

        Node root = new Node(10);

        BinaryTree binaryTree = new BinaryTree(root);
    }
}
