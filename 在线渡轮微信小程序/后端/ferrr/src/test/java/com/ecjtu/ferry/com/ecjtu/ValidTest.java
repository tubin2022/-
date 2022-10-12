package com.ecjtu.ferry.com.ecjtu;

import org.junit.Test;

public class ValidTest {
    @Test
    public void test(){
        String pattern = "^1[0-9]{10}+$";
        String phone = "22536512522";
        boolean matches = phone.matches(pattern);
        System.out.println(matches);
    }

    @Test
    public void test2(){
        String pattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        String phone = "28089635222qq.com";
        boolean matches = phone.matches(pattern);
        System.out.println(matches);
    }

    @Test
    public void test3(){
        System.out.println(" "=="");
    }
}
