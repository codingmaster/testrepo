package com.springer.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig
{
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public PlatformTransactionManager transactionManager()
	{
		final JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setDataSource(dataSource);
		return txManager;
	}
	
	
	@Bean
	public TransactionTemplate transactionTemplate()
	{
		return new TransactionTemplate(transactionManager());
	}
}
