package com.bridgeit.fundooNote.userservice.configuration;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.bridgeit.fundooNote.userservice.model.EmailDto;

@Service
public class MessageSender {
	@Autowired
	private static JmsTemplate jmstemplate;
	
	public static void sendMessage(final EmailDto emailDto)
	{
		jmstemplate.send(new MessageCreator() {
				
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage objectMessage=session.createObjectMessage((Serializable) emailDto);
				return objectMessage;
			}
		});
	}

}
