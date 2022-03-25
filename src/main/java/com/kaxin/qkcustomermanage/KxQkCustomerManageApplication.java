package com.kaxin.qkcustomermanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.kaxin.qkcustomermanage.mapper")
public class KxQkCustomerManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(KxQkCustomerManageApplication.class, args);
    }

}
