package com.project.attable.graphql.Mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.project.attable.dao.ChefDao;
import com.project.attable.dao.PaymentDao;
import com.project.attable.entity.Chef;
import com.project.attable.entity.ChefStatus;
import com.project.attable.entity.Payment;
import com.project.attable.entity.ReserveStatus;
import com.project.attable.entity.response.ChefEmailResponse;
import com.project.attable.entity.response.PaymentResponse;
import com.project.attable.security.responsemodel.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class PaymentMutation implements GraphQLMutationResolver {

	@Autowired
	private PaymentDao paymentDao;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public PaymentResponse updatePaymentStatus(Long paymentId,ReserveStatus status) {
		return paymentDao.updatePaymentStatus(paymentId, status);
	}
}
