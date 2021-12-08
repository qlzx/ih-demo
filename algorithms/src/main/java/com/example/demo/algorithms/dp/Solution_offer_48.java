package com.example.demo.algorithms.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lh0
 * @date 2021/12/8
 * @desc
 */
public class Solution_offer_48 {
    public static int lengthOfLongestSubstring(String s) {
        // f(i) 表示以i结尾的最大子串
        // 固定右边界 j ，
        // f(i) = f(i-1)+1  if(j-i>f(i-1))
        // f(i) = j-i if(j-i<=f(i-1))

        int pre = 0,res = 0;
        Map<Character, Integer> dic = new HashMap<>();

        for (int j = 0; j < s.length(); j++) {

            Integer integer = dic.getOrDefault(s.charAt(j), -1);
            if (integer == -1) {
                dic.put(s.charAt(j), j);
            }
            // i表示
            int i = s.lastIndexOf(s.charAt(j),j-1);
            pre = j - i > pre ? pre + 1 : j - i;

            res = Math.max(res, pre);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("bbacbb"));
    }
}
