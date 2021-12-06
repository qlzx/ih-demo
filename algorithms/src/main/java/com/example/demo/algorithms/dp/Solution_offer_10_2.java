package com.example.demo.algorithms.dp;

/**
 * @author lh0
 * @date 2021/12/6
 * @desc 青蛙跳台阶问题
 *
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n级的台阶总共有多少种跳法。
 *
 * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/qing-wa-tiao-tai-jie-wen-ti-lcof
 */
public class Solution_offer_10_2 {

    final int MOD = 1000000007;

    // f(1) = 1 f(2) = 2

    public int numWays(int n) {
        if (n == 0) {
            return 1;
        }
        if (n <= 2) {
            return n;
        }
        int p = 0, q = 1, r = 2;
        // 滚动数组
        for (int i = 3; i <= n; ++i) {
            p = q;
            q = r;
            r = (p + q) % MOD;
        }
        return r;
    }
}
