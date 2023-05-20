package com.ajudaqui.msemail.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ajudaqui.msemail.entity.Email;
import com.ajudaqui.msemail.entity.StatusEmail;
import com.ajudaqui.msemail.repository.EmailRepository;

@Service
public class EmailService {

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	Environment environment;

	private String sender = "ajudaquicom@hotmail.com";

	public Email sendEmail(Email email) {

		email.setSendDateEmail(LocalDateTime.now());
		email.setEmailFrom(sender);
		try {

			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(email.getEmailFrom());
			message.setTo(email.getEmailTo());
			message.setSubject(email.getSubject());
			message.setText(email.getText());

			javaMailSender.send(message);

			email.setStatusEmail(StatusEmail.SENT);

		} catch (MailException e) {
			System.err.println(e.getMessage());
			email.setStatusEmail(StatusEmail.ERROR);
			new RuntimeException(e.getMessage());
		}

		return emailRepository.save(email);

	}

	public String sendingEmailFile(Email email, MultipartFile file) throws IOException {

		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(sender);// "Quem esta enviando"
			helper.setTo(email.getEmailTo());// "Para"
//			helper.setCc(emailDetails.getInCopy()); // Copia
//			helper.setBcc(emailDetails.getInBlindCopy());// Copia oculta
			helper.setSubject(email.getSubject()); // "Titulo do e-mail"
			helper.setText(email.getText(), true); // "Corpo da mensagem"

			byte[] bytes = file.getBytes();

			ByteArrayResource resource = new ByteArrayResource(bytes);

			helper.addAttachment(file.getOriginalFilename(), resource);

			javaMailSender.send(message);

			return "Mail Sent Successfully...";

		} catch (MessagingException e) {
			return e.getMessage();
		} catch (MailException e) {
			return "Error while Sending Mail";
		}
	}

	public List<Email> emailById(Long userId) {

		return emailRepository.emailByUser(userId);

	}

}
