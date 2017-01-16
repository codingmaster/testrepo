package com.springer.rest.config;

import com.springer.core.config.AsyncConfig;
import com.springer.core.config.PersistenceConfig;
import com.springer.core.domain.BaseEntity;
import com.springer.core.repository.BaseRepository;
import com.springer.core.service.WatermarkService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ActiveProfiles("test")
@Configuration
@Import(value = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, WatermarkService.class, AsyncConfig.class, PersistenceConfig.class})
@EntityScan(basePackageClasses = BaseEntity.class)
@EnableJpaRepositories(basePackageClasses = BaseRepository.class)
@EnableTransactionManagement
public class TestConfiguration
{
}
