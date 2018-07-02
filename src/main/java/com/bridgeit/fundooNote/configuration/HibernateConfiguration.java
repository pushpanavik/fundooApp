package com.bridgeit.fundooNote.configuration;

import static org.hibernate.cfg.Environment.*;


import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bridgeit.fundooNote.userservice.configuration.MessageSender;
import com.bridgeit.fundooNote.userservice.model.EmailDto;

@Configuration
@ComponentScan(basePackages = { "com.bridgeit.fundooNote" })
@PropertySource(value = { "classpath:application.properties" })
@EnableTransactionManagement
public class HibernateConfiguration {

	@Autowired
	private Environment environment;
	
	

	@Bean
	public LocalSessionFactoryBean sessionFactory()
	{
		 LocalSessionFactoryBean sessionFactory=new LocalSessionFactoryBean();
		
		 sessionFactory.setDataSource(dataSource());
		 
		 sessionFactory.setHibernateProperties(hibernateProperties());
			sessionFactory.setPackagesToScan("com.bridgeit.fundooNote.userservice.model");
			return sessionFactory;		
	}
	
	@Bean
	public DataSource dataSource()
	{

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
       
        dataSource.setDriverClassName( environment.getProperty("hibernate.connection.driverClassName"));
        dataSource.setUrl(environment.getProperty("hibernate.connection.url"));
        dataSource.setUsername( environment.getProperty("hibernate.connection.username"));
        dataSource.setPassword( environment.getProperty("hibernate.connection.password"));
		
        return dataSource;
		
	}
	
	private Properties hibernateProperties() {
		
		Properties properties=new Properties();
	properties.put(DIALECT, environment.getProperty("hibernate.dialect"));
	properties.put(SHOW_SQL,environment.getProperty("hibernate.show_sql"));
	properties.put(FORMAT_SQL, environment.getProperty("hibernate.format_sql"));
	properties.put(HBM2DDL_AUTO, environment.getProperty("hibernate.hbm2ddl.auto"));
	
	properties.put(C3P0_MIN_SIZE, environment.getProperty("hibernate.C3P0_MIN_SIZE"));
	properties.put(C3P0_MAX_SIZE, environment.getProperty("hibernate.C3P0_MAX_SIZE"));
	properties.put(C3P0_ACQUIRE_INCREMENT,environment.getProperty("hibernate.C3P0_ACQUIRE_INCREMENT"));
	properties.put(C3P0_TIMEOUT, environment.getProperty("hibernate.C3P0_TIMEOUT"));
	properties.put(C3P0_MAX_STATEMENTS, environment.getProperty("hibernate.C3P0_MAX_STATEMENTS"));
	return properties;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager()
	{
	 HibernateTransactionManager txManager=new HibernateTransactionManager();
	 txManager.setSessionFactory(sessionFactory().getObject());
	 return txManager;
	}
	
	@Bean
	public MessageSender messageSender()
	{
		return new MessageSender();
	}
	
	@Bean
	public EmailDto emailDto()
	{
		return new EmailDto();
	}
	
	@Bean
	public BCryptPasswordEncoder encoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	
	
}
