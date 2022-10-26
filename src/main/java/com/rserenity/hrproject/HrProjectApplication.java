package com.rserenity.hrproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class})
//@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class HrProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrProjectApplication.class, args);
    }

}
