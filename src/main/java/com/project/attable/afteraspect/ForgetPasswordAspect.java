package com.project.attable.afteraspect;

import java.util.HashMap;
import java.util.Map;

import com.project.attable.emailwithform.dto.MailRequest;
import com.project.attable.emailwithform.service.EmailWithFormService;
import com.project.attable.security.JwtTokenProvider;
import com.project.attable.security.responsemodel.ForgotPasswordResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Aspect
@Configuration
public class ForgetPasswordAspect {

	@Autowired
	EmailWithFormService emailwithformService;

	@Value("${admin.email.senderEmail}")
	String senderEmail;

	@Value("${emailform.template.forgetpassword}")
	String template;

	@Value("${emailform.subject.forgetpassword}")
	String subject;

	@Value("${linkEmail}")
	String linkEmail;

	@Autowired
	JwtTokenProvider tokenProvider;

	@AfterReturning(value = "execution (* com.project.attable.dao.UserDaoImpl.forgetPassword(..))", returning = "returnObject")
	public void sendEmailForgotPassword(JoinPoint joinPoint, Object returnObject) {
		try {
			ForgotPasswordResponse response = (ForgotPasswordResponse) returnObject;
			if (response.getSuccess()) {
				MailRequest request = new MailRequest();
				request.setFrom(senderEmail);
				request.setTo(response.getEmail());
				request.setSubject(subject);
				request.setTemplate(template);

				Map<String, Object> model = new HashMap<>();
				model.put("linkClick",
						linkEmail + "/re-password?token=" + response.getToken() + "&email=" + response.getEmail());
				model.put("link", linkEmail);
				model.put("firstName", response.getFirstName());
				emailwithformService.sendEmail(request, model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
