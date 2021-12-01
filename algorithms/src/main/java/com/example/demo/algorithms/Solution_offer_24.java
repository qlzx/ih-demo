package com.example.demo.algorithms;

/**
 * @author lh0
 * @date 2021/11/30
 * @desc https://leetcode-cn.com/problems/fan-zhuan-lian-biao-lcof/
 * 反转链表
 */
public class Solution_offer_24 {

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) { val = x; }
     * }
     */
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {val = x;}

    }

    static class Solution {

        private ListNode root = new ListNode(0);

        public ListNode reverseList(ListNode head) {
            //Deque<Integer> stack = new LinkedList<>();
            //while (head != null) {
            //    stack.push(head.val);
            //    head = head.next;
            //}
            //ListNode cur = root;
            //while (!stack.isEmpty()) {
            //    cur.next = new ListNode(stack.pop());
            //    cur = cur.next;
            //}
            //return root.next;

            ListNode prev = null;
            ListNode curr = head;
            while (curr != null) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            return prev;
        }

        private void reverseList(ListNode head, ListNode root) {
            if (head == null) {
                return;
            } else {
                reverseList(head.next, root);
                root.next = head;
            }
        }
    }

    public static void main(String[] args) {

    }
}
