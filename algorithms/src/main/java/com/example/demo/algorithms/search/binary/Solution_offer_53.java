package com.example.demo.algorithms.search.binary;

/**
 * @author lh0
 * @date 2021/12/3
 * @desc 统计一个数字在排序数组中出现的次数。
 */
public class Solution_offer_53 {

    /**
     * 二分查找
     * 1。找到等于target的位置
     * 2。找到第一个大于等于target的位置 rightIdx
     * 3。找到第一个小于等于target的位置 leftIdx
     * 结果等于 rightIdx - leftIdx + 1
     *
     * @param nums
     * @param target
     * @return
     */
    public int search2(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;

        while (l <= r) {
            int pivot = (l + r) / 2;
            if (nums[pivot] == target) {
                break;
            } else if (nums[pivot] > target) {
                r = pivot - 1;
            } else {
                l = pivot + 1;
            }
        }

        if (l > r) {
            return 0;
        }

        return l;
    }

    public static int searchOne(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;

        while (l <= r) {
            int pivot = (l + r) / 2;

            if (nums[pivot] == target) {
                return pivot;
            } else if (nums[pivot] > target) {
                r = pivot - 1;
            } else {
                l = pivot + 1;
            }
        }
        return -1;
    }

    /**
     * 找到第一个等于target的下标
     *
     * @param nums
     * @param target
     * @return
     */
    public static int searchFirst(int[] nums, int target) {
        int l = 0;

        int r = nums.length - 1;

        while (l <= r) {
            int pivot = (l + r) / 2;

            if (nums[pivot] > target) {
                r = pivot - 1;
            } else if (nums[pivot] < target) {
                l = pivot + 1;
            } else {
                if (pivot == 0 || nums[pivot - 1] != target) {
                    return pivot;
                } else {
                    r = pivot - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 找到最后一个等于target的下标
     *
     * @param nums
     * @param target
     * @return
     */
    public static int searchFinal(int[] nums, int target) {
        int l = 0;

        int r = nums.length - 1;

        while (l <= r) {
            int pivot = (l + r) / 2;

            if (nums[pivot] > target) {
                r = pivot - 1;
            } else if (nums[pivot] < target) {
                l = pivot + 1;
            } else {
                if (pivot == nums.length - 1 || nums[pivot + 1] != target) {
                    return pivot;
                } else {
                    l = pivot + 1;
                }
            }
        }
        return -1;
    }

    /**
     * 找到第一个大于等于target的下标
     *
     * @param nums
     * @param target
     * @return
     */
    public static int searchRightIdx(int[] nums, int target) {
        int l = 0;

        int r = nums.length - 1;

        while (l <= r) {
            int pivot = (l + r) / 2;

            if (nums[pivot] < target) {
                l = pivot + 1;
            } else {
                if (pivot == 0 || nums[pivot - 1] < target) {
                    return pivot;
                } else {
                    r = pivot - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 找到最后一个小于等于target的下标
     *
     * @param nums
     * @param target
     * @return
     */
    public static int searchLeftIdx(int[] nums, int target) {
        int l = 0;

        int r = nums.length - 1;

        while (l <= r) {
            int pivot = (l + r) / 2;

            if (nums[pivot] > target) {
                r = pivot - 1;
            } else {
                // <=
                if (pivot == nums.length - 1 || nums[pivot + 1] > target) {
                    return pivot;
                } else {
                    l = pivot + 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int i = searchOne(new int[] {1, 3, 5, 7, 9}, 9);
        System.out.println(i);

        int ii = searchFirst(new int[] {3, 3, 3, 3, 5, 6, 9}, 3);
        System.out.println(ii);

        int iii = searchFinal(new int[] {3, 3, 3, 3, 5, 6, 9}, 3);
        System.out.println(iii);

        int iiii = searchRightIdx(new int[] {3, 3, 3, 3, 5, 6, 9}, 2);
        System.out.println(iiii);

        int iiiii = searchLeftIdx(new int[] {3, 3, 3, 3, 5, 6, 9}, 5);
        System.out.println(iiiii);

    }
}
