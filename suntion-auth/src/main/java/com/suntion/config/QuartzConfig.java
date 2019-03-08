package com.suntion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import suntion.core.quartz.AutowiredSpringBeanJobFactory;

@Configuration
@EnableScheduling
public class QuartzConfig {

	@Bean
	public AutowiredSpringBeanJobFactory AutowiredSpringBeanJobFactory() {
		return new AutowiredSpringBeanJobFactory();
	}
}
