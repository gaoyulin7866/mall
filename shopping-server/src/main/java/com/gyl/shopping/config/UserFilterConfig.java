package com.gyl.shopping.config;

import com.gyl.shopping.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserFilterConfig {

    @Bean
    public UserFilter getUserFilter(){
        return new UserFilter();
    }

    @Bean(name = "userFilterConf")
    public FilterRegistrationBean userFilterConf(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(getUserFilter());
        filterRegistrationBean.addUrlPatterns("/user/*");
        filterRegistrationBean.addUrlPatterns("/cart/*");
        filterRegistrationBean.setName("userFilterConf");
        return filterRegistrationBean;
    }
}
