package com.trunk.demo;

import com.trunk.demo.Interceptor.Interceptor;
import com.trunk.demo.Util.DateUtil;
import com.trunk.demo.repository.UsersRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan
@EnableCaching
@EnableTransactionManagement
@EnableMongoRepositories(basePackages = "com.trunk.demo.repository")
public class SmartReconcileApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartReconcileApplication.class, args);
    }

}
