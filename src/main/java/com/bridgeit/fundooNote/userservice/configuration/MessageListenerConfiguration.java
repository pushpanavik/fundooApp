package com.bridgeit.fundooNote.userservice.configuration;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
@EnableJms
public class MessageListenerConfiguration {

	@Autowired
	private ConnectionFactory connectionFactory;
	
	public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory()
	{
		DefaultJmsListenerContainerFactory djlcf=new DefaultJmsListenerContainerFactory();
		djlcf.setConnectionFactory(connectionFactory);
		djlcf.setConcurrency("1-1");
		return djlcf;
	}
}
