package com.example.demo.algorithms;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lh0
 * @date 2021/12/3
 * @desc https://leetcode-cn.com/problems/di-yi-ge-zhi-chu-xian-yi-ci-de-zi-fu-lcof/
 * 第一个只出现一次的字符
 *
 * 在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。 s 只包含小写字母。
 */
public class Solution_offer_50 {

    // 先存储hash 统计
    public char firstUniqChar(String s) {
        if (s == null) {
            return ' ';
        }
        Map<Character, Integer> map = new LinkedHashMap<>();

        char[] ss = s.toCharArray();
        for (int i = 0; i < ss.length; i++) {
            Integer cnt = map.get(ss[i]);
            if(cnt==null){
                map.put(ss[i], 1);
            }else {
                map.put(ss[i], cnt + 1);
            }
        }

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return ' ';
    }
}
