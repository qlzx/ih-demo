package com.example.demo.io.bio;

/**
 * @author lh0
 * @date 2021/8/9
 * @desc
 */
public class Solution {
    public static void main(String[] args) {

        //[1,2,3,0,0,0]
        //3
        //[2,5,6]
        //3

        int[] nums1= new int[]{1,2,3,0,0,0};
        int[] nums2 = new int[]{2,5,6};
        Solution solution = new Solution();
        solution.merge(nums1, 3, nums2, 3);
        System.out.println(nums1);

    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // nums1-> 移动位置？ nums1[m-1]作为缓存位置
        // nums2->
        int i = 0;
        int j =0;
        int[] result = new int[m+n];
        int k=0;
        while(i<m && j<n){
            if(nums1[i]!=0 && nums1[i]<=nums2[j]){
                result[k++] = nums1[i++];
            }else{
                result[k++] = nums2[j++];
            }
        }

        while(i<m){
            if(nums1[i]!=0){
                result[k++] = nums1[i++];
            }
        }

        while(j<n){
            result[k++] = nums2[j++];
        }
        for(int c=0;c<result.length;c++){
            nums1[c] = result[c];
        }
    }
}


//给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
//
// 
//
//示例 1：
//
//输入：nums = [-4,-1,0,3,10]
//输出：[0,1,9,16,100]
//解释：平方后，数组变为 [16,1,0,9,100]
//排序后，数组变为 [0,1,9,16,100]
//示例 2：
//
//输入：nums = [-7,-3,2,3,11]
//输出：[4,9,9,49,121]
// 
//
//提示：
//
//1 <= nums.length <= 104
//-104 <= nums[i] <= 104
//nums 已按 非递减顺序 排序
// 
//
//进阶：
//
//请你设计时间复杂度为 O(n) 的算法解决本问题
//
