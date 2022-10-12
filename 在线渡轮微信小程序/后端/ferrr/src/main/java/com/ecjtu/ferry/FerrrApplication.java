package com.ecjtu.ferry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class FerrrApplication {

    public static void main(String[] args) {
        SpringApplication.run(FerrrApplication.class, args);
    }

}
