package com.example.datastructure.let;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MiceAndCheese_2611 {
    // TODO: 2023/6/7 2611
    public int miceAndCheese(int[] reward1, int[] reward2, int k) {
        int len = reward1.length;
        int res = 0;
        int[] diff = new int[len];
        for (int i=0; i< len; i++){
            res+=reward2[i];
            diff[i] = reward1[i] - reward2[i];
        }
        Arrays.sort(diff);
        for (int i=0; i< k; i++)
            res+=diff[len - i - 1];
        return res;
    }
    public int miceAndCheese2(int[] reward1, int[] reward2, int k){
        int res = 0;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        int len =reward2.length;

        for (int i=0; i< len; i++){
            res += reward2[i];
            priorityQueue.offer(reward1[i] -  reward2[i]);

            if(priorityQueue.size() > k){
                priorityQueue.poll();
            }
        }
        while (!priorityQueue.isEmpty()){
            res += priorityQueue.poll();
        }
        return res;
    }

    // TODO: 2023/6/7 2460.对数组执行操作
    public int[] applyOperations(int[] nums) {
        int len = nums.length;
        int[] array = new int[len];

        int left = 0;
        int right = len-1;
        int i=0;
        for(; i< len-1; i++){
            if(nums[i] == 0){
                array[right--] = nums[i];
            }
            else if(nums[i] == nums[i+1]){
                array[left++] = nums[i] *2;
                array[right--] = 0;
                i++;
            }else{
                array[left++]=nums[i];
            }
        }
        if(i == len-1) array[left] = nums[i];
        return array;
    }

    public int[] applyOperations2(int[] nums){
        for (int i =0,len =nums.length-1; i<len; i++){
            if(nums[i] == nums[i+1]){
                nums[i] = nums[i]*2;
                nums[i+1]=0;
            }
        }
        for (int i=0, len = nums.length-1; i<len ;i++){
            if(nums[i] == 0){
               nums[i] = nums[i] ^ nums[i+1];
               nums[i+1] = nums[i] ^ nums[i+1];
               nums[i] =nums[i] ^ nums[i+1];
            }
        }
        return nums;
    }

    // TODO: 2023/6/8  459.重复的字符串
    public boolean repeatedSubstringPattern(String s) {
        int n = s.length();
        for (int i = 1; i * 2 <= n; ++i) {
            if (n % i == 0) {
                boolean match = true;
                for (int j = i; j < n; ++j) {
                    if (s.charAt(j) != s.charAt(j - i)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String s = "abab";
        MiceAndCheese_2611 miceAndCheese_2611 = new MiceAndCheese_2611();
        boolean b = miceAndCheese_2611.repeatedSubstringPattern(s);
        System.out.println(b);
    }
}
