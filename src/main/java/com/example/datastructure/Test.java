package com.example.datastructure;

public class Test {
    //判断子字符串str是否在字符串str中出现
    public static boolean SearchString(String strs, String str) {
        int strsLen=strs.length();
        int strLen=str.length();
        int i=0;
        int j=0;
        while (i<strsLen && j<strLen){
            if(strs.charAt(i)==str.charAt(j)){
                i++;
                j++;
            }else{
                i=i-j+1;
                j=0;
            }
        }
        return j==strLen;
    }
    // 字符串匹配
    public static boolean searchString(String strs,String str){
        int strsLen= strs.length();
        int strLen=str.length();
        if(strsLen < strLen) return false;
        int i=0;
        for (int j=0 ; j<strsLen;j++){
            int index=j;
            while(index<strsLen && i<strLen){
                if(strs.charAt(index)==str.charAt(i)){
                    index++;
                    i++;
                }else {
                    i=0;
                    break;
                }
            }
            if(i==strLen) return true;
        }
        return false;
    }
    public static int SearchStrings(String strs, String str) {
        int strsLen=strs.length();
        int strLen=str.length();
        int i=0;
        int j=0;
        while (i<strsLen && j<strLen){
            if(strs.charAt(i)==str.charAt(j)){
                i++;
                j++;
            }else{
                i=i-j+1;
                j=0;
            }
        }
        return j==strLen ? i-j : 0;
    }
    public static int[] getNext(char[] p) {
        // 已知next[j] = k,利用递归的思想求出next[j+1]的值
        // 如果已知next[j] = k,如何求出next[j+1]呢?具体算法如下:
        // 1. 如果p[j] = p[k], 则next[j+1] = next[k] + 1;
        // 2. 如果p[j] != p[k], 则令k=next[k],如果此时p[j]==p[k],则next[j+1]=k+1,
        // 如果不相等,则继续递归前缀索引,令 k=next[k],继续判断,直至k=-1(即k=next[0])或者p[j]=p[k]为止
        int pLen = p.length;
        int[] next = new int[pLen];
        int k = -1;
        int j = 0;
        next[0] = -1; // next数组中next[0]为-1
        while (j < pLen - 1) {
            if (k == -1 || p[j] == p[k]) {
                k++;
                j++;
                if(p[j]!=p[k])
                    next[j]=k;
                else
                    next[j]=next[k];
            }
            else
                k = next[k];
        }
        return next;
    }
    //字符串匹配KMP算法
    public static int indexOf(String source, String pattern) {
        int i = 0, j = 0;
        char[] src = source.toCharArray();
        char[] ptn = pattern.toCharArray();
        int sLen = src.length;
        int pLen = ptn.length;
        int[] next = getNext(ptn);
        while (i < sLen && j < pLen) {
            // 如果j = -1,或者当前字符匹配成功(src[i] = ptn[j]),都让i++,j++
            if (j == -1 || src[i] == ptn[j]) {
                i++;
                j++;
            } else {
                // 如果j!=-1且当前字符匹配失败,则令i不变,j=next[j],即让pattern模式串右移j-next[j]个单位
                j = next[j];
            }
        }
        if (j == pLen)
            return i - j;
        return -1;
    }
    // 冒泡排序
    public static int[] bubblesort(int [] array){
        int len=array.length;

        for (int i = 0; i < len; i++){
            for (int j = 0; j < len-i-1; j++){
                if(array[j] > array[j+1]){
//                    array[j] = array[j] ^ array[j+1];
//                    array[j+1] = array[j] ^ array[j+1];
//                    array[j] = array[j] ^ array[j+1];
                    swap(array,j,j+1);
                }
            }
        }
        return array;
    }

    //插入排序
    public static int[] insertSort(int[] arr){

        for (int i = 1; i < arr.length; i++) {

            int tmp = arr[i];
            int j = i;

            while (j > 0 && tmp < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }

            if (j != i)
                arr[j] = tmp;
        }
        return arr;
    }
    //插入排序
    public static int[] insertSorts(int[] arr){
        for (int i=1; i<arr.length;i++){
            int temp=arr[i];
            int j=i-1;
            while(j>=0 && temp<arr[j]){
                arr[j+1]=arr[j];
                j--;
            }
            arr[j+1]=temp;
        }
        return arr;
    }
    public static int[] insertSort2(int[] array) {
        for (int i=1; i< array.length; i++){
            int numMinIndex=i;
            while(numMinIndex-1>=0 && array[numMinIndex-1] >array[numMinIndex]){
                swap(array,numMinIndex,numMinIndex-1);
                numMinIndex--;
            }
        }
        return array;
    }
    //优化插入排序
    public static int[] insertSort3(int[] array) {
        int len=array.length;
        for (int i=1; i<len; i++) {
            for (int j=i; j>0 && array[j-1] > array[j];j--){
                swap(array,j,j-1);
            }
        }
        return array;
    }
    //交换数组中两个元素的位置
    public static void swap(int[] array, int left, int right){
        array[left]=array[left] ^ array[right];
        array[right]=array[left] ^ array[right];
        array[left]=array[left] ^ array[right];
    }

    //选择排序
    public static int[] selectionSort(int[] arr){
        for (int i=0; i<arr.length-1; i++){
            int minIndex=i;
            for (int j=i+1;j<arr.length; j++)
                if(arr[j]<arr[minIndex])
                    minIndex=j;

            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        return arr;
    }

    public static char[] manacherString(String str){
        char[] chars = str.toCharArray();
        int len=chars.length;
        char [] newChars=new char[len*2+1];

        int index=0;
        for (int i = 0; i < newChars.length; i++)
            newChars[i]=(i & 1) == 0 ? '#' : chars[index++];
        return newChars;
    }
    // BF暴力求解最长回文子串长度
    public static int longestpalindrome(String str) {
        if (str.length() == 0) return 0;

        char[] chars = manacherString(str);
        int len = chars.length;
        int count = Integer.MIN_VALUE;

        for (int i = 1; i < len; i++) {
            int left = i - 1;
            int right = i + 1;
            int ans = 1;
            while (left >= 0 && right < len) {
                if (chars[left] == chars[right]) {
                    left--;
                    right++;
                    ans += 2;
                } else
                    break;
            }
            count = Math.max(ans,count);
        }
        return count/2;
    }
    //统一转成大写：ch & 0b11011111 简写：ch & 0xDF
    //统一转成小写：ch | 0b00100000 简写：ch | 0x20
    //转换字符串为大写
    public static String capital(String str){
        char[] chars = str.toCharArray();
        for (int i=0; i<chars.length; i++)
            chars[i] = capitalChar(chars[i]);
        return new String(chars);
    }
    //转换字符为大写
    public static char capitalChar(char ch){
        return (char) (ch & 0XDF);
    }
    //转换字符为小写
    public static char lowerCaseChar(char ch){
        return (char) (ch | 0X20);
    }
    //转换字符串为小写
    public static String lowerCase(String str){
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++)
            chars[i]=lowerCaseChar(chars[i]);
        return new String(chars);
    }
    /**
     * 判断一个字符串是否为回文串
     * 该字符串包含大小写,将字符串转换为大写或者转换为小写
     * */
    public static boolean isPalindromic(String str){
        int len=str.length();
        for (int i=0; i < len/2; i++){
//            if((str.charAt(i) & 0xDF) != (str.charAt(len - i - 1) & 0xDF)) return false;
            if(lowerCaseChar(str.charAt(i)) != lowerCaseChar(str.charAt(len-i-1))) return false;
        }
        return true;
    }

    /**
     * 判断字符串是否为回文串，字符串包含大小写字符和数字以外还包含其它字符如"#,%,￥...."；
     * 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
     *
     * 字母和数字都属于字母数字字符。
     * @param // STOPSHIP: 2023/5/28
     */
    public static boolean isPalindromics(String s){
        return s.replaceAll("[^0-9a-zA-Z]","").toLowerCase().equals(
                //逆转字符串reverse()
                        new StringBuilder(
                                s.replaceAll("[^0-9a-zA-Z]","").toLowerCase()
                        ).reverse().toString()
                );
    }
    // manacher算法求解最长回文子串

    public static void main(String[] args) {
//        String str="abaaaaaaaaaaaaaaaaa";
//
//        String longestpalindrome = longestpalindrome(str);
//        System.out.println(longestpalindrome);
        int n=10;
        for (int s= 0; s < (1<<n);s++){
            System.out.println(s);
        }
    }
}
