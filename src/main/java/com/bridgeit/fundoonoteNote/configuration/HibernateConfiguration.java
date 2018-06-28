package com.bridgeit.fundoonoteNote.configuration;
/*package com.bridgeit.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import static org.hibernate.cfg.Environment.*;


public class HibernateConfiguration {

	@Autowired
	private Environment environment;
	
	@Bean
	public LocalSessionFactoryBean sessionFactory()
	{
		 LocalSessionFactoryBean factoryBean=new LocalSessionFactoryBean();
		 Properties properties=new Properties();
		 
		 properties.put(DRIVER, environment.getProperty("mysql.driverClassName"));
		 properties.put(URL,environment.getProperty("mysql.url"));
			properties.put(USER, environment.getProperty("mysql.username"));
			properties.put(PASS, environment.getProperty("mysql.password"));
			
			properties.put(DIALECT, environment.getProperty("hibernate.dialect"));
			properties.put(SHOW_SQL,environment.getProperty("hibernate.show_sql"));
			properties.put(FORMAT_SQL, environment.getProperty("hibernate.format_sql"));
			properties.put(HBM2DDL_AUTO, environment.getProperty("hibernate.hbm2ddl.auto"));
			
			properties.put(C3P0_MIN_SIZE, environment.getProperty("hibernate.C3P0_MIN_SIZE"));
			properties.put(C3P0_MAX_SIZE, environment.getProperty("hibernate.C3P0_MAX_SIZE"));
			properties.put(C3P0_ACQUIRE_INCREMENT,environment.getProperty("hibernate.C3P0_ACQUIRE_INCREMENT"));
			properties.put(C3P0_TIMEOUT, environment.getProperty("hibernate.C3P0_TIMEOUT"));
			properties.put(C3P0_MAX_STATEMENTS, environment.getProperty("hibernate.C3P0_MAX_STATEMENTS"));
				
			factoryBean.setHibernateProperties(properties);
			factoryBean.setPackagesToScan("com.bridgeit.model");
			return factoryBean;
	}
	@Bean
	public HibernateTransactionManager getTransaction()
	{
	 HibernateTransactionManager transactionManager=new HibernateTransactionManager();
	 transactionManager.setSessionFactory(sessionFactory().getObject());
	 return transactionManager;
	}
}
*/