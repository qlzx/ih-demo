package com.example.demo.algorithms;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

/**
 * @author lh0
 * @date 2021/11/30
 * @desc
 * https://leetcode-cn.com/problems/bao-han-minhan-shu-de-zhan-lcof/
 * 包含min函数的栈
 *
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈中，调用 min、push 及 pop 的时间复杂度都是 O(1)。
 * 可能包含重复元素
 */
public class Solution_offer_30 {

    static class MinStack{
        Deque<Integer> stack1 = new LinkedList<>();
        // 辅助栈 最小元素栈
        Deque<Integer> stack2 = new LinkedList<>();

        public MinStack(){

        }

        void pop(){
            if(Objects.equals(stack1.pop(),stack2.peek())){
                stack2.pop();
            }
        }

        void push(int x){
            stack1.push(x);
            if(stack2.isEmpty() || stack2.peek()>=x){
                stack2.push(x);
            }
        }

        int top(){
            return stack1.peek();
        }

        int min(){
            return stack2.peek();
        }
    }


}
