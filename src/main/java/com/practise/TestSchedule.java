package com.practise;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TestSchedule {
    private int count=0;

    @Scheduled(cron="*/6 * * * * ?")
    private void process(){
        System.out.println(Thread.currentThread().getName());
        System.out.println("this is scheduler task runing  "+(count++));
    }

    @Component
    class Test2{
        private  final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        @Scheduled(fixedRate = 6000)
        public void reportCurrentTime() {
            System.out.println(Thread.currentThread().getName());

            System.out.println("现在时间：" + dateFormat.format(new Date()));
        }
    }

    public int[] quickSort(int[] a){
        int flag = a[0];
        for (int i = 1;i<a.length;i++){
            for (int j = 1; j<a.length; j++){
                if (a[i] < flag){
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                    i++;
                 }
            }
            int temp2 = a[i-1];
            a[i-1] = flag;
            flag = temp2;

        }
        return a;
    }

    public static void main(String[] args) {
        int i = 0;
        int[] a = {1,2,3};
        changeValue(a);
        System.out.println(a[2]);
        System.out.println(Thread.currentThread());
        new Thread(new MyRunnable()).start();
        new MyThread().start();
    }

    public static void changeValue(int[] a){
        a[2] =110;
    }



}
class MyRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread());
    }
}

class MyThread extends Thread {
    @Override
    public void run(){
        System.out.println(Thread.currentThread());
    }
}

