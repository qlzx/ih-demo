package com.example.demo.algorithms;

/**
 * @author lh0
 * @date 2021/12/1
 * @desc https://leetcode-cn.com/problems/zuo-xuan-zhuan-zi-fu-chuan-lcof/
 * 左旋字符串
 *
 * 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
 *
 */
public class Solution_offer_58 {

    public static String reverseLeftWords(String s, int n) {
        char[] ss = s.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (int i = n; i < ss.length+n; i++) {
            sb.append(ss[i%ss.length]);
        }
        return sb.toString();
    }

}
