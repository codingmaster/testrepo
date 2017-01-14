package com.springer.rest;

import com.springer.core.domain.BaseEntity;
import com.springer.core.repository.BaseRepository;
import com.springer.core.service.BaseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = { BaseService.class })
@EntityScan(basePackageClasses = BaseEntity.class)
@EnableJpaRepositories(basePackageClasses = BaseRepository.class)
public class WatermarkApplication extends SpringBootServletInitializer
{

	public static void main(String[] args) {
		SpringApplication.run(WatermarkApplication.class, args);
	}
}
