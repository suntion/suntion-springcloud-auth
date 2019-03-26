package com.suntion.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.suntion.core.quartz.AutowiredSpringBeanJobFactory;

/**
 * @author Suntion
 */
@Configuration
@EnableScheduling
public class QuartzConfig {

    @Bean
    public AutowiredSpringBeanJobFactory autowiredSpringBeanJobFactory() {
        return new AutowiredSpringBeanJobFactory();
    }
}
