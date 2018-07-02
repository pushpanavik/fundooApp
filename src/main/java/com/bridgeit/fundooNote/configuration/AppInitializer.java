package com.bridgeit.fundooNote.configuration;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.bridgeit.fundooNote.userservice.configuration.JMSConfig;
import com.bridgeit.fundooNote.userservice.configuration.JMSListenerConfiguration;
import com.bridgeit.fundooNote.userservice.configuration.RedisConfig;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		System.out.println("Working");
		return new Class[] { HibernateConfiguration.class,JMSConfig.class,JMSListenerConfiguration.class,RedisConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		
		return new Class[] { NoteConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		
		return new String[] { "/" };
	}

}
