package com.bridgeit.fundooNote.configuration;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.bridgeit.fundooNote.userservice.model.EmailDto;
import com.bridgeit.fundooNote.utilservice.SendingMail;



@Component
public class MessageReciever {
	private final String EMAIL_RESPONSE_QUEUE = "mail.queue";

	@JmsListener(destination = EMAIL_RESPONSE_QUEUE)
	public void receiverMessage(final Message<EmailDto> message) {
		EmailDto emailDto = message.getPayload();

		System.out.println("Mail-Id : " + emailDto.getMailto());
		System.out.println("Subject : " + emailDto.getSubject());
		System.out.println("URL     : " + emailDto.getUrl());

		try {

			SendingMail.sendMail(emailDto.getMailto(),emailDto.getSubject(),emailDto.getUrl());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}