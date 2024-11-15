package com.sakurapuare.boatmanagement;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.sakurapuare.boatmanagement.mapper")
class BoatmanagementApplicationTests {

    @Test
    void contextLoads() {
    }

}
