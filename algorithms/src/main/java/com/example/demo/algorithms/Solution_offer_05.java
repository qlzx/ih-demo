package com.example.demo.algorithms;

/**
 * @author lh0
 * @date 2021/12/1
 * @desc https://leetcode-cn.com/problems/ti-huan-kong-ge-lcof/
 * 替换空格
 * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
 */
public class Solution_offer_05 {

    public static String replaceSpace(String s) {
        if (s == null) {
            return null;
        }
        char[] ss = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <ss.length; i++) {
            if(ss[i]!=' '){
                sb.append(ss[i]);
            }else{
                sb.append("%20");
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        String s = replaceSpace("1237 91231");
        System.out.println(s);
    }
}
