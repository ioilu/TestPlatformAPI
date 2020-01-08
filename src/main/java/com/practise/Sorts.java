package com.practise;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sorts {
    public static int[] popSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
//            int max = a[i];
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] < a[j]) {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
        return a;
    }

    public static int[] searchSort(int[] a) {
        int i, j, flag;
        for (i = 0; i < a.length - 1; i++) {
            flag = i;
            for (j = i + 1; j < a.length; j++) {
                if (a[i] < a[j]) {
                    flag = j;
                }
            }
            if (flag != i) {
                int temp = a[i];
                a[i] = a[flag];
                a[flag] = temp;
            }
        }
        return a;
    }

    public static int[] insertSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int j = a[i];
            int flag = i - 1;

            while (flag >= 0 && j < a[flag]) {
                a[flag + 1] = a[flag];
                flag--;
            }
            a[flag + 1] = j;

        }
        return a;
    }

    public static void quickSort(int[] a,int i,int j){
        if (i<j) {
            int flag = a[i];
            int left = i;
            int right = j;
            while (left<right) {
                while (left<right && flag < a[right]) {
                    right--;
                }
                a[left] = a[right];
                while (left<right && flag >=a[left] ) {
                    left++;
                }
                a[right] = a[left];
            }
            a[left]=flag;
            quickSort(a,i,left-1);
            quickSort(a,left+1,j);
        }
    }



    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4, 5};
        int[] b = new int[]{5, 4, 3, 2, 1,4,1};
        int[] c = {40,66,12,2,63,99,40,30,90};
        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        Sorts.quickSort(c,0,c.length-1);
//        for (int i : c) {
//            System.out.print(i+" ");
//        }
//        Sorts.popSort(a);
//        Sorts.searchSort(a);
//        Sorts.insertSort(b);
//        for (int i = 0; i < b.length; i++) {
//            System.out.print(b[i] + "    ");
//        }
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(df.format(new Date()));





    }
}


