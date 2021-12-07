package com.example.demo.algorithms.dp;

/**
 * @author lh0
 * @date 2021/12/7
 * @desc https://leetcode-cn.com/problems/lian-xu-zi-shu-zu-de-zui-da-he-lcof/
 * <p>
 * 输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
 * <p>
 * 要求时间复杂度为O(n)。
 */
public class Solution_offer_42 {

    public static int maxSubArray(int[] nums) {
        // f(i) = Math.max(f(i-1)+nums[i],nums[i]);

        int[] f = new int[nums.length];
        f[0] = nums[0];
        int max = f[0];
        for (int i = 1; i < nums.length; i++) {
            f[i] = Math.max(f[i - 1] + nums[i], nums[i]);

            if (f[i] > max) {
                max = f[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
    }
}
