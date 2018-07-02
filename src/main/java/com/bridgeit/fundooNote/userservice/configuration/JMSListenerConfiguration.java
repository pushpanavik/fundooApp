package com.bridgeit.fundooNote.userservice.configuration;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
@EnableJms
public class JMSListenerConfiguration {

	@Autowired
	ConnectionFactory connectionFactory;
	
	@Bean
	DefaultJmsListenerContainerFactory jmsListenerContainerFactory()
	{
	 DefaultJmsListenerContainerFactory factory=new DefaultJmsListenerContainerFactory();
	 factory.setConnectionFactory(connectionFactory);
	 factory.setConcurrency("1-1");
	 return factory;
	}
	
}