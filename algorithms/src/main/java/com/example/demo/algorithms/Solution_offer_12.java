package com.example.demo.algorithms;

/**
 * @author lh0
 * @date 2021/12/4
 * @desc 给定一个m x n 二维字符网格board 和一个字符串单词word 。如果word 存在于网格中，返回 true ；否则，返回 false 。
 * <p>
 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
 */

public class Solution_offer_12 {
    // 连续匹配的下标
    static int x ;

    static int[] px = new int[] {0, -1, 0, 1};
    static int[] py = new int[] {-1, 0, 1, 0};

    public static boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;

        int[][] used = new int[m][n];

        char[] ss = word.toCharArray();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(dfs(i, j, m, n, board, used, ss)){
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean dfs(int startX, int startY, int m, int n, char[][] board, int[][] used,
        char[] ss) {
        if (x == ss.length - 1 && board[startX][startY] == ss[x]) {
            return true;
        }
        // 出发点 所有位置都可以
        for (int k = 0; k < 4; k++) {
            int newX = startX + px[k];
            int newY = startY + py[k];
            // 匹配
            if (newX >= 0 && newX < m && newY >= 0 && newY < n && used[newX][newY] != 1) {
                if (board[startX][startY] == ss[x]) {
                    // DFS
                    x++;
                    used[startX][startY] = 1;

                    if(dfs(newX, newY, m, n, board, used, ss)){
                        return true;
                    }

                    x--;
                    used[startX][startY] = 0;
                }
            }
        }
        return false;

    }

    public static void main(String[] args) {
        char[][] a = {{'a', 'a'}};

        //System.out.println(a[0][1]);
        //
        //int m = a.length;
        //int n = a[0].length;
        //for (int i = 0; i <m; i++) {
        //    for (int j = 0; j < n; j++) {
        //        System.out.println(a[i][j]);
        //    }
        //}

        System.out.println(exist(a, "aaa"));

    }

}
