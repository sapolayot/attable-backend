package com.project.attable.afteraspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.project.attable.emailwithform.dto.MailRequest;
import com.project.attable.emailwithform.service.EmailWithFormService;
import com.project.attable.entity.ContactUs;

@Aspect
@Configuration
public class ContactUsAspect {

	@Autowired
	EmailWithFormService emailwithformService;

	@Value("${admin.email.senderEmail}")
	String senderEmail;

	@Value("${emailform.template.contactus}")
	String template;

	@Value("${emailform.subject.contactus}")
	String subject;
	
	@Value("${linkEmail}")
	String linkEmail;

	@AfterReturning(value = "execution (* com.project.attable.graphql.Mutation.ContactUsMutaion.replyContact(..))", returning = "returnObject")
	public void sendEmailForContactUs(JoinPoint joinPoint, Object returnObject) {
		ContactUs contact = (ContactUs) returnObject;
		MailRequest request = new MailRequest();
		request.setFrom(senderEmail);
		request.setTo(contact.getEmail() );
		request.setTemplate(template);
		String subject = "At Table's Reply to Subject: "+contact.getSubject();
		request.setSubject(subject);
		Map<String, Object> model = new HashMap<>();
		model.put("name", contact.getName());
		model.put("message", contact.getMessages());
		model.put("subject", subject);
		model.put("link", linkEmail);
		emailwithformService.sendEmail(request, model);

	}

}
