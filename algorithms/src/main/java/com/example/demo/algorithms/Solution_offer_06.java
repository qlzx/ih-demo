package com.example.demo.algorithms;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author lh0
 * @date 2021/11/30
 * @desc https://leetcode-cn.com/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof/
 * 从尾到头打印链表
 */
public class Solution_offer_06 {

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

    class Solution {
        public int[] reversePrint(ListNode head) {
            Deque<Integer> stack = new LinkedList<>();

            while (head != null) {
                stack.push(head.val);
                head = head.next;
            }

            int[] result = new int[stack.size()];
            for (int i = 0; !stack.isEmpty(); i++) {
                result[i] = stack.pop();
            }
            return result;
        }
    }
}
