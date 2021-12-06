package com.example.demo.algorithms.dp;

/**
 * @author lh0
 * @date 2021/12/6
 * @desc 斐波那契数列
 *
 * 斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。
 *
 * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
 *
 *
 */
public class Solution_offer_10 {

    final int MOD = 1000000007;

    public int fib(int n) {
        if (n < 2) {
            return n;
        }
        int p = 0, q = 0, r = 1;
        // 滚动数组
        for (int i = 2; i <= n; ++i) {
            p = q;
            q = r;
            r = (p + q) % MOD;
        }
        return r;
    }
}
