package com.example.demo.algorithms.dp;

/**
 * @author lh0
 * @date 2021/12/7
 * @desc 给定一个数字，我们按照如下规则把它翻译为字符串：0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。一个数字可能有多个翻译。请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法。
 */
public class Solution_offer_46 {

    public static int translateNum(int num) {
        String s = String.valueOf(num);
        // f(i-2)
        int p = -1;
        // f(i-1)
        int q = 0;
        // f(i)
        int r = 1;

        char[] ss = s.toCharArray();
        for (int i = 0; i < ss.length; i++) {
            p = q;
            q = r;

            r = 0;
            r += q;

            if (i == 0) {
                continue;
            }

            String sub = s.substring(i - 1, i + 1);
            if (sub.compareTo("25") <= 0 && sub.compareTo("10") >= 0) {
                r += p;
            }
        }
        return r;
    }

    //
    public static void main(String[] args) {
        System.out.println(translateNum(1407));
    }
}
