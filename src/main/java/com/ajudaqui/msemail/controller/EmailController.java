package com.ajudaqui.msemail.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajudaqui.msemail.dto.EmailDto;
import com.ajudaqui.msemail.entity.Email;
import com.ajudaqui.msemail.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private EmailService emailService;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/sending-email")
	public ResponseEntity<?> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
		try {
			Email email = emailService.sendEmail(modelMapper.map(emailDto, Email.class));

			return new ResponseEntity<>(email, HttpStatus.CREATED);

		} catch (MailException e) {
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> emailByUser(@PathVariable Long userId) {
		try {

			List<Email> emails = emailService.emailById(userId);

			return new ResponseEntity<>(emails, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
