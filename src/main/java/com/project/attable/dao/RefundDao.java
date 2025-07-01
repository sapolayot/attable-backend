package com.project.attable.dao;

import com.project.attable.entity.Reservation;
import com.project.attable.entity.response.RefundResponse;

public interface RefundDao {

	RefundResponse addBankaccountForRefund(Reservation reserve, String authToken);
	RefundResponse updateBankAccountForRefund(Reservation reserve);
}
