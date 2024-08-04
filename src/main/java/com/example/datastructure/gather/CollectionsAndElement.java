package com.example.datastructure.gather;

import java.util.ArrayList;
import java.util.List;

// 集合与元素
/**
 * 集合{1,3,4,8}可以表示为01 00 01 10 10
 * 元素与集合的操作：
 *  a | (1 << i) 把i插入到集合当中
 *  a & ~(1 << i) 把i从集合中删除
 *  a & (1<<i) 判断i是否属于集合
 * */
public class CollectionsAndElement {
    // TODO: 2023/6/24 78. 子集  位运算
    public List<List<Integer>> traverseCollections(int[] nums){
        ArrayList<Integer> integers = new ArrayList<>();
        ArrayList<List<Integer>> lists = new ArrayList<>();

        int n = nums.length;

        for(int i=0; i< (1 << n) ; i++){
            integers.clear();

            for (int j=0; j < n; j++){
                if((i & (1 << j))!=0){
                    integers.add(nums[j]);
                }
            }
            lists.add(new ArrayList<>(integers));
        }
        return lists;
    }

    // TODO: 2023/6/24 77. 组合 位运算
    public List<List<Integer>> combine(int n, int k) {
        ArrayList<List<Integer>> lists = new ArrayList<List<Integer>>();

        ArrayList<Integer> list = new ArrayList<Integer>();

        for(int i=0; i< (1 << n) ; i++){
            list.clear();

            for (int j=0; j < n; j++){
                if((i & (1 << j))!=0)
                    list.add(j+1);
            }
            if(list.size()  ==  k )
                lists.add(new ArrayList<>(list));
        }
        return lists;
    }

    public List<List<Integer>> permute(int[] nums) {
        ArrayList<List<Integer>> lists = new ArrayList<List<Integer>>();
        ArrayList<Integer> list = new ArrayList<Integer>();

        int len = nums.length;
        int temp = 0;
        for (int num : nums) {
            temp |= 1 << (num);
        }
        for (int mask = temp; mask != 0; mask = ((mask - 1) & temp)) {
            return null;
        }
        return lists;
    }
        public static int minOperations(int num1, int num2) {
            int ans = helper(num1, num2, 0);
            return ans == Integer.MAX_VALUE ? -1 : ans;
        }

        private static int helper(int num1, int num2, int depth) {
            if (num1 == 0) {
                return depth;
            }

            if (depth == 60) {
                return Integer.MAX_VALUE;
            }

            int minSteps = Integer.MAX_VALUE;
            for (int i = 0; i <= 60; i++) {
                int newNum1 = num1 - ((1 << i) + num2);
                int steps = helper(newNum1, num2, depth + 1);
                minSteps = Math.min(minSteps, steps);
            }

            return minSteps;
        }

        public static void main(String[] args) {
            System.out.println(minOperations(3, -2)); // 输出：3
            System.out.println(minOperations(5, 7)); // 输出：-1
        }

//    public static void main(String[] args) {
//
//        CollectionsAndElement collectionsAndElement = new CollectionsAndElement();
//        int tmp = 0;
//        tmp |= 1 << 1;
//        tmp |= 1 << 2;
//        tmp |= 1 << 3;
//
//        // 遍历集合中的所有元素
//        for (int i=0 ;i<10; i++){
//            if((tmp &(1 << i))!=0)
//                System.out.println(i+1);
//        }
//
//        // 枚举遍历 tmp 中所有的 非空子集
//        for (int mask = tmp; mask != 0; mask = ((mask - 1) & tmp)) {
//            for (int i=0; i< mask; i++){
//                if((mask & (1 << i))!=0){
//                    System.out.println(mask+"--->"+i);
//                }
//            }
//        }
//        int s = tmp;
//        do {
//            s = tmp & (s - 1);
//            System.out.println("======>"+s);
//        }while (s!=tmp);
//        System.out.println(tmp);
//
//        int[] nums = {1,2,3};
//        collectionsAndElement.permute(nums);
//    }
}
