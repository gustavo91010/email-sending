package com.ajudaqui.msemail.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ajudaqui.msemail.entity.Email;
import com.ajudaqui.msemail.entity.StatusEmail;
import com.ajudaqui.msemail.repository.EmailRepository;

@Service
public class EmailService {

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	public Email sendEmail(Email email) {

		email.setSendDateEmail(LocalDateTime.now());
		email.setEmailFrom("ajudaquicom@hotmail.com");
		try {

			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(email.getEmailFrom());
			message.setTo(email.getEmailTo());
			message.setSubject(email.getSubject());
			message.setText(email.getText());

			javaMailSender.send(message);
			email.setStatusEmail(StatusEmail.SENT);

		} catch (MailException e) {
			email.setStatusEmail(StatusEmail.ERROR);
		}
		return emailRepository.save(email);

	}

	public List<Email> emailById(Long userId) {
		return emailRepository.emailByUser(userId);

	}

}
