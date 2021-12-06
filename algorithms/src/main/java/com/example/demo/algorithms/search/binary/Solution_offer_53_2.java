package com.example.demo.algorithms.search.binary;

/**
 * @author lh0
 * @date 2021/12/3
 * @desc https://leetcode-cn.com/problems/que-shi-de-shu-zi-lcof/
 * <p>
 * 一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。
 * 在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字。
 */
public class Solution_offer_53_2 {

    public int missingNumber(int[] nums) {

        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int pivot = (l + r) / 2;
            if (nums[pivot] == pivot) {
                // 左边完整，右边缺少
                l = pivot + 1;
            } else {
                // 左边缺少
                r = pivot - 1;
            }
        }
        return l;
    }

    public static void main(String[] args) {

    }

}
