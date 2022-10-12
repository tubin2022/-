package com.lianwei;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class LogisticsApplicationTests {

    @Test
    void contextLoads() {
        String uuid = UUID.randomUUID().toString()+"admin";
        System.out.println(uuid);
        Md5Hash md5Hash = new Md5Hash("123321",uuid,64);
        System.out.println(md5Hash);
        System.out.println(md5Hash.toString());
    }

    @Test
    void contextLoads2() {
        System.out.println(UUID.randomUUID().toString());
    }

}
