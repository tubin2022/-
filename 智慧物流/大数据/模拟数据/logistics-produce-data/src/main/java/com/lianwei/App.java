package com.lianwei;


import lombok.SneakyThrows;

public class App {
    public static void main(String[] args) {
        MyThread1 myThread1 =  new MyThread1();
        MyThread2 myThread2 = new MyThread2();
        MyThread3 myThread3 = new MyThread3();
        myThread1.start();
        myThread2.start();
        myThread3.start();
    }

    static class MyThread1 extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            Logistics1.run();
        }
    }

    static class MyThread2 extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            Logistics2.run();
        }
    }

    static class MyThread3 extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            Logistics3.run();
        }
    }
}
