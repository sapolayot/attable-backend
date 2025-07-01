package com.project.attable.dao;
import com.project.attable.entity.ReserveStatus;
import com.project.attable.entity.response.PaymentResponse;

public interface PaymentDao {
	PaymentResponse updatePaymentStatus(Long paymentId,ReserveStatus status);
}
