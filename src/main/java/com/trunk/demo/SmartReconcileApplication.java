package com.trunk.demo;

import com.trunk.demo.repository.UsersRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan
@EnableCaching
@EnableTransactionManagement
@EnableMongoRepositories(basePackageClasses = UsersRepository.class)
public class SmartReconcileApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartReconcileApplication.class, args);
    }

}
