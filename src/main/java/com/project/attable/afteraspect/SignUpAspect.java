package com.project.attable.afteraspect;

import java.util.HashMap;
import java.util.Map;

import com.project.attable.emailwithform.dto.MailRequest;
import com.project.attable.emailwithform.service.EmailWithFormService;
import com.project.attable.security.JwtTokenProvider;
import com.project.attable.security.responsemodel.SignUpResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;


@Aspect
@Configuration
public class SignUpAspect {

	@Autowired
	EmailWithFormService emailwithformService;

	@Value("${admin.email.senderEmail}")
	String senderEmail;

	@Value("${emailform.template.signupdiner}")
	String template1;

	@Value("${emailform.template.signupchef}")
	String template2;

	@Value("${emailform.subject.signup}")
	String subject;

	@Value("${linkEmail}")
	String linkEmail;

	@Autowired
	JwtTokenProvider tokenProvider;

	@AfterReturning(value = "execution (* com.project.attable.security.controller.AuthenController.signup(..))", returning = "returnObject")
	public void sendEmail(JoinPoint joinPoint, Object returnObject) throws Exception {
		ResponseEntity<?> returnObj = (ResponseEntity<?>) returnObject;
		SignUpResponse response = (SignUpResponse) returnObj.getBody();
		if (tokenProvider.validateToken(response.getJwt())) {
			MailRequest request = new MailRequest();
			request.setFrom(senderEmail);
			request.setTo(response.getEmail());
			request.setSubject(subject);
			String role = "";
			if (response.getRoles().toString().contains("ROLE_DINER")) {
				request.setTemplate(template1);
				role = "diner";
			} else if (response.getRoles().toString().contains("ROLE_CHEF")) {
				request.setTemplate(template2);
				role = "chef";
			}

			Map<String, Object> model = new HashMap<>();
			model.put("firstName", response.getFirstName());
			model.put("linkurl", linkEmail + "/profile/" + role + "/update?token="+response.getJwt()+"&email="+response.getEmail());
			model.put("link", linkEmail);
			model.put("isFacebook", response.isFacebookUser());
			model.put("subject", subject);
			emailwithformService.sendEmail(request, model);
		}else {
			throw new Exception("Email Signup not sent");
		}
	}

	@AfterReturning(value = "execution (* com.project.attable.security.controller.AuthenController.authenticationByFacebook(..))", returning = "returnObject")
	public void sendEmailFromFacebook(JoinPoint joinPoint, Object returnObject) {
		try {
			ResponseEntity<?> returnObj = (ResponseEntity<?>) returnObject;
			SignUpResponse response = (SignUpResponse) returnObj.getBody();
			if (tokenProvider.validateToken(response.getJwt())) {
				MailRequest request = new MailRequest();
				request.setFrom(senderEmail);
				request.setTo(response.getEmail());
				request.setSubject(subject);
				String role = "";
				if (response.getRoles().toString().contains("ROLE_DINER")) {
					request.setTemplate(template1);
					role = "diner";
				} else if (response.getRoles().toString().contains("ROLE_CHEF")) {
					request.setTemplate(template2);
					role = "chef";
				}

				Map<String, Object> model = new HashMap<>();
				model.put("firstName", response.getFirstName());
				model.put("linkurl", linkEmail + "/profile/" + role + "/update?token="+response.getJwt()+"&email="+response.getEmail());
				model.put("isFacebook", response.isFacebookUser());
				model.put("link", linkEmail);
				model.put("subject", subject);
				emailwithformService.sendEmail(request, model);
			}else {
				throw new Exception("Email Signup not sent");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
