package com.depanker.config;

import com.depanker.util.MyLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by depankersharma on 03/11/16.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.depanker"})
public class AssetConfig {
    @Bean
    public MyLogger myLogger(){
        return new MyLogger();
    }
}