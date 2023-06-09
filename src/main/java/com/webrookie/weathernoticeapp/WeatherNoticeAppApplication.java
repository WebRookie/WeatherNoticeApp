package com.webrookie.weathernoticeapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author WebRookie
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.webrookie.weathernoticeapp.dao")
public class WeatherNoticeAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherNoticeAppApplication.class, args);
    }

}
