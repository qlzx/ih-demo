package com.example.demo.algorithms.dp;

/**
 * @author lh0
 * @date 2021/12/6
 * @desc 股票的最大利润
 *
 * 假设把某股票的价格按照时间先后顺序存储在数组中，请问买卖该股票一次可能获得的最大利润是多少？
 *
 * https://leetcode-cn.com/problems/gu-piao-de-zui-da-li-run-lcof/
 */
public class Solution_offer_63 {
        // 暴力枚举 || 遍历

    // [7,1,5,3,6,4]

    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int max = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            }
            if (prices[i] - minPrice > max) {
                max = prices[i] - minPrice;
            }
        }
        return max;
    }

    public static void main(String[] args) {

    }
}
