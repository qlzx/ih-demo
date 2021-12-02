package com.example.demo.algorithms;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lh0
 * @date 2021/12/1
 * @desc 找出数组中的重复数字
 */
public class Solution_offer_03 {

    public int findRepeatNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (!set.add(nums[i])) {
                return nums[i];
            }
        }
        return -1;
    }
}
