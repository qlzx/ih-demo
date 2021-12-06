package com.example.demo.algorithms.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author lh0
 * @date 2021/12/2
 * @desc
 */
public class BFS {

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
    //int[][] flow = new int[m][n];

    int target_x = 4;

    int target_y = 4;
    // 左，下，右，上

    int[] px = new int[] {-1, 0, 1, 0};
    int[] py = new int[] {0, 1, 0, -1};

    boolean flag = false;

    public BFS() {
    }

    Queue<Integer> queue = new LinkedList<>();

    public void toTarget(int x, int y) {
        queue.offer(x);
        queue.offer(y);

        while (!queue.isEmpty()) {
            int curry = queue.poll();
            int currx = queue.poll();

            if (curry == target_x && currx == target_y) {
                flag = true;
                return;
            }

            if (graph[currx][curry] == 0) {
                return;
            }

            for (int i = 0; i < 4; i++) {
                int newX = currx + px[i];
                int newY = curry + py[i];

                if(newX >=0 && newX<m && newY>=0 && newY<n && graph[newX][newY]!=0) {
                    queue.offer(newX);
                    queue.offer(newY);
                }
            }
        }

    }

    public boolean go(int x,int y){
        toTarget(x, y);
        return flag;
    }

    public static void main(String[] args) {
        BFS dfs = new BFS();
        System.out.println(dfs.go(0, 4));
    }

}
