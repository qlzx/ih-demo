package com.example.demo.algorithms.graph;

/**
 * @author lh0
 * @date 2021/12/2
 * @desc
 */
public class DFS {

    static int m = 5;
    static int n = 5;
    static int[][] graph;

    static {
        graph = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = 1;
            }
        }

        graph[0][0] = 0;
        graph[1][0] = 0;
        graph[1][1] = 0;
        graph[1][2] = 0;
        graph[1][3] = 0;

        // 0 1 1 1 1
        // 0 0 0 0 1
        // 1 1 1 1 1
        // 1 1 1 1 1
        // 1 1 1 1 1

        graph[3][0] = 0;
        graph[4][1] = 0;
    }

    // 标记走过的路
    int[][] flow = new int[m][n];

    int target_x = 4;

    int target_y = 4;
    // 左，下，右，上

    int[] px = new int[] {-1, 0, 1, 0};
    int[] py = new int[] {0, 1, 0, -1};

    boolean flag = false;

    public DFS() {
    }

    public void toTarget(int x, int y) {
        if (x == target_x && y == target_y) {
            flag = true;
            return;
        }

        if (graph[x][y] == 0) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            int newX = x + px[i];
            int newY = y + py[i];

            // 新的坐标可以走通
            if (newX >= 0 && newX < m && newY >= 0 && newY < n && !flag
                && flow[newX][newY] == 0 && graph[newX][newY] !=0) {

                flow[newX][newY] = 1;

                toTarget(newX, newY);

                flow[newX][newY] = 0;
            }
        }

    }

    public boolean go(int x,int y){
        toTarget(x, y);
        return flag;
    }

    public static void main(String[] args) {
        DFS dfs = new DFS();
        System.out.println(dfs.go(0, 4));
    }

}
