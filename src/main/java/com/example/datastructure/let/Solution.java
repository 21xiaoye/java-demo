package com.example.datastructure.let;

import java.util.*;

public class Solution {
    public Solution(){}
    // TODO: 2023/6/6  2352. 相等行列对
    /**
     * 暴力破解
     * */
    public int equalPairs(int[][] grid){
        int len = grid.length;
        int count = 0;
        for (int[] ints : grid) {
            int pairs = isPairs(ints, grid);
            count += pairs;
        }
        return count;
    }
    public int  isPairs(int[] arr, int[][] grid){
        int len = grid.length;
        int index =0;
        int count = 0;
        while (index < len){
            for (int i=0; i<arr.length; i++){
                if(arr[i] != grid[i][index]) break;
                if(i == arr.length-1) count++;
            }
            index++;
        }
        return count;
    }

    /**
     * 对二维数组进行转置
    * */
    public int equalPairs2(int[][] grid){
        int[][] transpose = transpose(grid);
        int count = 0;
        for (int[] value : transpose) {
            for (int[] ints : grid) {
                if (Arrays.equals(value, ints)) count++;
            }
        }
        return count;
    }
    public int[][] transpose(int[][] grid){
        int len = grid.length;
        int [][] array = new int[len][len];

        for (int i =0; i<len ;i++){
            for (int j=0; j< len; j++){
                array[j][i] = grid[i][j];
            }
        }
        return array;
    }

    /**
     * 使用哈希
     * */
    public int equalPairs3(int[][] grid) {
            int n = grid.length;
            Map<List<Integer>, Integer> cnt = new HashMap<List<Integer>, Integer>();
            for (int[] row : grid) {
                List<Integer> arr = new ArrayList<Integer>();
                for (int num : row) {
                    arr.add(num);
                }
                cnt.put(arr, cnt.getOrDefault(arr, 0) + 1);
            }

            int res = 0;
            for (int j = 0; j < n; j++) {
                List<Integer> arr = new ArrayList<Integer>();
                for (int[] ints : grid) {
                    arr.add(ints[j]);
                }
                if (cnt.containsKey(arr)) {
                    res += cnt.get(arr);
                }
            }
            return res;
        }

    // TODO: 2023/6/10  1170.比较字符串最小字母出现频次
    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        int[] ints = numList(queries);
        int[] ints1 = numList(words);
        Arrays.sort(ints1);
        for (int i=0; i < ints.length; i++ ){
            int count = ints[i];
            int j=0;
            for (; j < ints1.length; j++){
                if(count < ints1[j]) break;
            }
            ints[i] = ints1.length - j;
        }
        return ints;
    }

    public static int[] numList(String[] words){
        int[] wordsList = new int[words.length];
        int index = 0;
        for (String str : words){
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            int count = 1;
            char ch = chars[0];
            for (int i=1; i<chars.length; i++){
                if(ch == chars[i]){
                    ++count;
                }else break;
            }
            wordsList[index++] = count;
        }
        return wordsList;
    }

    // TODO: 2023/6/10  2559. 统计范围内的元音字符串数
    public int[] vowelStrings(String[] words, int[][] queries) {
        int n = words.length;
        int[] prefixSums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            int value = isVowelString(words[i]) ? 1 : 0;
            prefixSums[i + 1] = prefixSums[i] + value;
        }
        int q = queries.length;
        int[] ans = new int[q];
        for (int i = 0; i < q; i++) {
            int start = queries[i][0], end = queries[i][1];
            ans[i] = prefixSums[end + 1] - prefixSums[start];
        }
        return ans;
    }

    public boolean isVowelString(String word) {
         return isVowelLetter(word.charAt(0)) && isVowelLetter(word.charAt(word.length() - 1));
    }

    public boolean isVowelLetter(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    // TODO: 2023/6/11 6461. 判断一个数是否迷人
    public boolean isFascinating(int n) {
        String str = String.valueOf(2 * n).concat(String.valueOf(3 * n)).concat(String.valueOf(n));
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        String s = new String(chars);

        return s.equals("123456789");
    }

    // TODO: 2023/6/11 6425. 找到最长的半重复子字符串
    public int longestSemiRepetitiveSubstring(String s) {
        char[] chars = new char[10];
        int left = 0 ;
        int right = 0;

        while (left < s.length()  && right <= left){
            left++;
            right++;
        }
        return 0;
    }

    // TODO: 2023/6/11  303. 区域和检索 - 数组不可变
    int[] array;
    public Solution(int[] nums) {
        array = new int[nums.length+1];
        for(int i=1; i<=nums.length; i++){
            array[i] = array[i-1] + nums[i-1];
        }
    }

    public int sumRange(int left, int right) {
        left++;right++;
        return array[right] - array[left-1];
    }

    // TODO: 2023/6/12  76. 最小覆盖子串
    public String minWindow(String s, String t) {
        if(s.length()<t.length()){
            return "";
        }
        HashMap<Character,Integer> map=new HashMap<Character,Integer>();
        for(char ch:t.toCharArray()){
            map.put(ch,map.getOrDefault(ch,0)+1);
        }
        int end=0;
        int start=0;
        int len=map.size();
        int minlen=Integer.MAX_VALUE;
        int strEnd=0;
        int strStrat=0;
        while(end<s.length()|| (len==0 && end==s.length())){
            if(len>0){
                char chEnd=s.charAt(end);
                if(map.containsKey(chEnd)){
                    map.put(chEnd,map.get(chEnd)-1);
                    if(map.get(chEnd)==0){
                        len--;
                    }
                }
                end++;
            }else{
                if(end-start<minlen){
                    minlen=end-start;
                    strEnd=end;
                    strStrat=start;
                }
                char chStart=s.charAt(start);
                if(map.containsKey(chStart)){
                    map.put(chStart,map.get(chStart)+1);
                    if(map.get(chStart)==1){
                        len++;
                    }
                }
                start++;
            }
        }
        return s.substring(strStrat,strEnd);
    }

    // TODO: 2023/6/14 1375. 二进制字符串前缀一致的次数
    public int numTimesAllBlue(int[] flips) {
        int res = 0;
        int len = flips.length;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2 - o1;
                    }
                }
        );

        for (int flip : flips) {
            priorityQueue.offer(flip);
            Integer so = priorityQueue.peek();
            assert so !=null;
            if (so == priorityQueue.size()) res++;
        }
        return res;
    }


    public int missingNumber(int[] nums) {
        int xor = 0;
        int n = nums.length;
        for (int num : nums) {
            xor ^= num;
        }
        for (int i = 0; i <= n; i++) {
            xor ^= i;
        }
        return xor;
    }

    // TODO: 2023/6/15 1177.构建回文串检测
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        ArrayList<Boolean> list = new ArrayList<>();

        for (int[] query : queries) {
            String word = s.substring(query[0], query[1] + 1);
            int k = query[2];
            if (isPoli(word, k)) {
                list.add(true);
            } else list.add(false);
        }
        return list;
    }
    public boolean isPoli(String word, int k){
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        System.out.println(Arrays.toString(chars));
        if(word.length() == 1) return true;
        HashMap<Character, Integer>map = new HashMap<>();
        int index = 0;
        while (index < word.length()){
            map.put(word.charAt(index),map.getOrDefault(word.charAt(index),0)+1);
            index++;
        }
        int i=0;
        int count =0;
        while (i<word.length()){
           char ch = word.charAt(i);
           if(map.get(ch)!= null && map.get(word.charAt(i)) %2 !=0 ){
                map.remove(word.charAt(i));
                count++;
           }
            i++;
        }
        return count - k * 2 == 1 || count - k * 2 == 0;
    }

    // TODO: 2023/6/26  2485. 找出中枢整数
    public int pivotInteger(int n) {
        if(n == 1) return 1;
        for (int i =2; i <=n; i++){
            int sum = sum(1, i-1);
            int sum1 = sum(i+1,n);
            if(sum == sum1) return i;
        }
        return -1;
    }
    public int sum(int left, int right){
        int sum = 0;

        for(int i= left; i<=right; i++) sum+=i;

        return sum;
    }

    // TODO: 2023/6/26  2485. 找出中枢整数 ---> 前缀和
    public int pivotInteger1(int n) {
        int[] rank = new int[n+1];
        Arrays.fill(rank,0);
        int sum = 0;
        for (int i=0; i<= n; i++){
            sum+=i;
            rank[i]=sum;
        }
        int record  = 0;
        for (int i=n; i>=0; i--){
            record +=i;
            if(rank[i] == record) return i;
        }
        return -1;
    }
    public static void main(String[] args) {
        Solution solution = new Solution();
        int i = solution.pivotInteger(8);
        System.out.println(i);
    }
}

