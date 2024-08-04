package com.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int l = (int) 1e7;
        while (l > 0) {
            if (isLei(l)) {
                System.out.println(l);
                break;
            }
            l--;
        }
    }

    static boolean isLei(int n) {
        int sum = 0;
        int n1 = n;
        ArrayList<Integer> ys = new ArrayList<Integer>();
        while (n1 != 0) {
            ys.addFirst(n1 % 10);
            sum += n1 % 10;
            n1 /= 10;
        }
        ys.add(sum);
        int index = 0;
        while (sum <= n) {
            ys.add(sum = sum *2 - ys.get(index++));
            if (sum == n) {
                return true;
            }
        }
        return false;
    }
}