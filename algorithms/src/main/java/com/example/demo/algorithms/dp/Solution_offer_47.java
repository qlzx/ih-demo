package com.example.demo.algorithms.dp;

/**
 * @author lh0
 * @date 2021/12/7
 * @desc 礼物的最大价值
 * 在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。
 * 你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直到到达棋盘的右下角。
 * 给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/li-wu-de-zui-da-jie-zhi-lcof
 */
public class Solution_offer_47 {

    // f(i,j) = max(f(i-1,j),f(i,j-1)) + grid[i][j];
    // f(0,0) = grid[0][0]
    // f(0,1) = grid[0]

    public static int maxValue(int[][] grid) {
        if (grid.length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        // 向右 或者向下
        int[][] ff = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int right ;
                // 0,3
                if (i == 0 && j == 0) {
                    ff[i][j] = grid[i][j];
                }
                else if (i == 0) {
                    ff[i][j] = ff[i][j - 1] + grid[i][j];
                } else if (j == 0) {
                    ff[i][j] = ff[i - 1][j] + grid[i][j];
                } else {
                    ff[i][j] = Math.max(ff[i - 1][j], ff[i][j - 1]) + grid[i][j];
                }
            }
        }

        return ff[m-1][n-1];
    }

    public static void main(String[] args) {
        int i = maxValue(new int[][] {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}});
        System.out.println(i);
    }


}
