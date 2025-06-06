package com.sakurapuare.boatmanagement;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BoatmanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoatmanagementApplication.class, args);
    }

}
