package com.project.attable.graphql.Mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.project.attable.dao.RefundDao;
import com.project.attable.entity.AccountForRefund;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.response.RefundResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class RefundMutation implements GraphQLMutationResolver {

	@Autowired
	private RefundDao refundDao;

	@PreAuthorize("hasAnyRole('ROLE_DINER,ROLE_CHEF')")
	public RefundResponse addBankaccountForRefund(Reservation reserve,String authToken) {
		return refundDao.addBankaccountForRefund(reserve, authToken);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public RefundResponse updateBankAccountForRefund(Reservation reserve){
		return refundDao.updateBankAccountForRefund(reserve);
	}
}
