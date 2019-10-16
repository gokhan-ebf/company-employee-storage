package com.ebf.storage.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.ebf.storage")
@ComponentScan(basePackages = { "com.ebf.storage" })
@EnableJpaRepositories("com.ebf.storage")
public class CompanyEmployeeStorageAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyEmployeeStorageAppApplication.class, args);
    }

}
