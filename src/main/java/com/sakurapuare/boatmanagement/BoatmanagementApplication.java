package com.sakurapuare.boatmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sakurapuare.boatmanagement.mapper")
public class BoatmanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoatmanagementApplication.class, args);
    }

}
