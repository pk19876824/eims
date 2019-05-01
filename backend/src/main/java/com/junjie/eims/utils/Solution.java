package com.junjie.eims.utils;


/**
 * @author junjie.zhang
 * @since 2019/4/26 5:59 PM
 */
public class Solution {

  public static int go(int[][] grid, boolean[][] flag, int num, int x, int y, int width,
                       int height) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      return num;
    }
    if (flag[y][x]) {
      return num;
    }
    flag[y][x] = true;
    if (grid[y][x] == 0) {
      return num;
    }
    num++;

    //向上
    num = go(grid, flag, num, x, y - 1, width, height);
    //向下
    num = go(grid, flag, num, x, y + 1, width, height);
    //向左
    num = go(grid, flag, num, x - 1, y, width, height);
    //向右
    num = go(grid, flag, num, x + 1, y, width, height);

    return num;
  }

  public static int maxAreaOfIsland(int[][] grid) {
    int height = grid.length;
    int width = grid[0].length;
    boolean[][] flag = new boolean[height][width];
    int max = 0;
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (flag[y][x] || grid[y][x] == 0) {
          continue;
        }
        int num = 1;
        //向上
        num = go(grid, flag, num, x, y - 1, width, height);
        //向下
        num = go(grid, flag, num, x, y + 1, width, height);
        //向左
        num = go(grid, flag, num, x - 1, y, width, height);
        //向右
        num = go(grid, flag, num, x + 1, y, width, height);
        max = Math.max(max, num);
      }
    }

    return max;
  }


  public static void main(String[] args) {
    int[][] gird = new int[][]{
      {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
      {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
      {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}};
    //int[][] gird = new int[][]{{1}};
    System.out.println(maxAreaOfIsland(gird));
  }
}
