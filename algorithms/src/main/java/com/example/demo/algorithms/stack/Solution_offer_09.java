package com.example.demo.algorithms.stack;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author lh0
 * @date 2021/11/30
 * @desc
 * https://leetcode-cn.com/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof/
 * 使用两个栈来实现队列
 */
public class Solution_offer_09 {

    static class Cqueue{
        // 入队栈
        Deque<Integer> stack1 = new LinkedList<>();
        // 辅助栈 用于转换成先入先出
        Deque<Integer> stack2 = new LinkedList<>();

        public Cqueue(){

        }

        /**
         * 入队
         */
        void appendTail(int x){
            stack1.push(x);
        }

        /**
         * 出队
         * @return 队列空时返回-1
         */
        int deleteHead(){
            if(!stack2.isEmpty()){
                return stack2.pop();
            }else if(stack1.isEmpty()){
                return -1;
            }else{
                // stack1 搬移到 stack2
                // stack2.pop
                while(!stack1.isEmpty()){
                    stack2.push(stack1.pop());
                }
                return stack2.pop();
            }
        }
    }


}
