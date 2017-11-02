package com.hotpatch.trans;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class TestMainInJar {
    public static void main(String[] args) {
        System.out.println(new TransClass().getNumber());
        int count = 0;
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            int number = new TransClass().getNumber();
            System.out.println(number);
            if (3 == number || count >= 10) {
                break;
            }
        }
    }
}
