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
import com.project.attable.entity.Chef;
import com.project.attable.entity.response.ChefEmailResponse;

@Aspect
@Configuration
public class UpdateStatusChefAspect {

	@Autowired
	EmailWithFormService emailwithformService;

	@Value("${admin.email.senderEmail}")
	String senderEmail;

	@Value("${emailform.template.update_chef}")
	String template;

	@Value("${linkEmail}")
	String linkEmail;

	@AfterReturning(value = "execution (* com.project.attable.dao.ChefDaoImpl.editChefStatus(..))", returning = "returnObject")
	public void updateStatusChef(JoinPoint joinPoint, Object returnObject) {
		ChefEmailResponse response = (ChefEmailResponse) returnObject;
		Chef chef = response.getChef() != null ? response.getChef() : new Chef();
		String status = chef.getStatus() != null ? chef.getStatus().toString() : "";
		if (response.isSuccess() && (status.equals("Approved") || status.equals("Reject"))) {
			MailRequest request = new MailRequest();
			request.setFrom(senderEmail);
			request.setTemplate(template);
			request.setTo(chef.getUser().getEmail());
			String subject = "";
			if (status.equals("Approved")) {
				subject = "Congratulations! Chef " + chef.getUser().getFirstName();
			} else if (status.equals("Reject")) {
				subject = "Clarification Request - At Table's Chef Application";
			}
			request.setSubject(subject);
			Map<String, Object> model = new HashMap<>();
			model.put("firstName", chef.getUser().getFirstName());
			model.put("status", status);
			model.put("detail", response.getReject()!=null?response.getReject().getRejectText():null);
			model.put("link", linkEmail);
			model.put("subject", subject);
			emailwithformService.sendEmail(request, model);
		}

	}

}
