package com.zubarev.lab3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ApplicationPlace {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationPlace.class, args);
    }

}