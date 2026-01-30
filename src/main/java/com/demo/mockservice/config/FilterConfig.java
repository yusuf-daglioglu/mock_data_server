package com.demo.mockservice.config;

import com.demo.mockservice.filter.DynamicResponseFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class FilterConfig {

    @Bean
    public FilterRegistrationBean<DynamicResponseFilter> automaticResponseGeneratorFilter() {
        FilterRegistrationBean<DynamicResponseFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new DynamicResponseFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
