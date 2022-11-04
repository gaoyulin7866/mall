package com.gyl.shopping;


import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@EnableDubboConfig
@EnableAutoConfiguration
@ServletComponentScan
@ComponentScan(basePackages = {"com.gyl.shopping"})
@MapperScan("com.gyl.shopping.dao")
public class ShoppingApplication {
    private static final Logger log = LoggerFactory.getLogger(ShoppingApplication.class);
    public static void main(String[] args) {

        try {
            SpringApplication.run(ShoppingApplication.class, args);
            log.info("========================服务启动成功========================");
        } catch (Exception e) {
            log.error("========================服务启动失败========================");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
