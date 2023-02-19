package com.poshist;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableRabbit
@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args)  {
        SpringApplication.run(Application.class, args);
    }


}
