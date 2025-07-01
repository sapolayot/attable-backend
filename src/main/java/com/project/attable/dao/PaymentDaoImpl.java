package com.project.attable.dao;

import com.project.attable.entity.Payment;
import com.project.attable.entity.ReserveStatus;
import com.project.attable.entity.SubEvent;
import com.project.attable.entity.response.PaymentResponse;
import com.project.attable.repository.PaymentRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDaoImpl implements PaymentDao {

	@Autowired
	PaymentRepository paymentRepository;
	
	@Value("${gapDay.emailConfirmSeat}")
	private int emailConfirmSeat;
	
	@Override
	public PaymentResponse updatePaymentStatus(Long paymentId,ReserveStatus status) {
		Payment payment = paymentRepository.findByIdIn(paymentId);
		SubEvent subEvent = payment.getReserve().getSubevent();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime eventDate = subEvent.getEventDate();
		long dayEventDate = now.until(eventDate, ChronoUnit.DAYS) + 1;
		if (dayEventDate < emailConfirmSeat) {
			return new PaymentResponse(false, null, "Can not upload slip at gap day");
		}
		payment.getReserve().setStatus(status);
		payment = paymentRepository.save(payment);
		return new PaymentResponse(true, payment, null);
	}

}
