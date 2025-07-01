package com.project.attable.emailwithform.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.project.attable.emailwithform.dto.MailRequest;
import com.project.attable.emailwithform.dto.MailResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@Service
public class EmailWithFormService {
	
	@Autowired
	private JavaMailSender sender;

	@Autowired
	private Configuration config;

	public MailResponse sendEmail(MailRequest request, Map<String, Object> model) {
		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Template template = config.getTemplate(request.getTemplate());
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
			helper.setTo(request.getTo());
			helper.setText(html, true);
			helper.setSubject(request.getSubject());
			helper.setFrom(request.getFrom());
			sender.send(message);
			
			response.setMessage("mail send to : "+ request.getTo());
			response.setStatus(Boolean.TRUE);
		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail sending failure"+ e.getCause());
			response.setStatus(Boolean.FALSE);
		}

		return response;
	}
}
