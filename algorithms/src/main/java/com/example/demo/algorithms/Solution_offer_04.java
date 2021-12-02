package com.example.demo.algorithms;

/**
 * @author lh0
 * @date 2021/12/1
 * @desc 二维数组中的查找
 * https://leetcode-cn.com/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof/
 */
public class Solution_offer_04 {
    // 从matrix中找到对应的target
    // 二维数组每行 每列有序
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        // 行数
        int m = matrix.length;
        if (m == 0) {
            return false;
        }
        // 列数
        int n = matrix[0].length;
        // 从右上角开始线性寻找
        int x = 0;
        int y = n-1;

        while (x < n && y >= 0) {
            int val = matrix[x][y];
            if (val == target) {
                return true;
            } else if (val > target) {
                y = y - 1;
            }else{
                x = x + 1;
            }
        }
        return false;
    }

}
