package com.bridgeit.fundooNote.userservice.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.bridgeit.fundooNote.userservice.model.EmailDto;
import com.bridgeit.fundooNote.userservice.service.IUserService;
import com.bridgeit.fundooNote.utilservice.SendingMail;

@Service
public class MessageReciever {

	@Autowired
	IUserService userService;
	
	static final Logger LOG=LoggerFactory.getLogger(MessageReciever.class);
	private static final String ORDER_QUEUE = "my_queue";
	
	@JmsListener(destination=ORDER_QUEUE)
	public void receiveMessage(EmailDto emailDto)throws JmsException {
		System.out.println(emailDto.getName());
		SendingMail.sendMail(emailDto.getName(),emailDto.getMailTo(),emailDto.getToken());
		
	}
	
}
