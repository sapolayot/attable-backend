package com.project.attable.security.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.attable.entity.Payment;
import com.project.attable.entity.PaymentMethod;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.ReserveStatus;
import com.project.attable.repository.ReservationRepository;
import com.project.attable.security.requestmodel.OmiseRequest;
import com.project.attable.security.responsemodel.OmiseResponse;

import co.omise.Client;
import co.omise.ClientException;
import co.omise.models.*;
import co.omise.requests.Request;

@RestController
@RequestMapping("/omise")
public class OmiseController {

	@Value("${omise.omisekey}")
	private String OMISE_SKEY;
	
	@Autowired
	ReservationRepository reservationRepository;

//	private static final String OMISE_SKEY = "skey_test_5gwxqp61kiy214mn2la";

	@PostMapping("/charge")
	public OmiseResponse charge(@Valid @RequestBody OmiseRequest omise) throws IOException, OmiseException, ClientException {
		Request<Charge> createChargeRequest = new Charge.CreateRequestBuilder().amount(omise.getAmount()) // 1,000 THB
				.currency("thb").card(omise.getToken()).build();
		Charge charge = client().sendRequest(createChargeRequest);
		if(charge.getStatus().equals(ChargeStatus.Successful)) {
			Reservation reserve = reservationRepository.findByIdIn(omise.getReserveId());
			if (reserve != null) {
				reserve.setReserveDate(LocalDateTime.now());
				reserve.setRefundable(true);
				Payment payment = new Payment();
				payment.setPaymentMethod(PaymentMethod.CREDIT_CARD);
				payment.setPrice(omise.getAmount()/100);
				payment.setRefId(charge.getId());
				DateTime paymentDate = charge.getCreated();
				ZoneId oldZone = ZoneId.of("UTC");
				ZoneId newZone = ZoneId.of("Asia/Bangkok");
				LocalDateTime oldDateTime = LocalDateTime.of(paymentDate.getYear(),
						paymentDate.getMonthOfYear(), 
						paymentDate.getDayOfMonth(), 
						paymentDate.getHourOfDay(), 
						paymentDate.getMinuteOfHour(), 
						paymentDate.getSecondOfMinute());
				LocalDateTime newDateTime = oldDateTime.atZone(oldZone)
                        .withZoneSameInstant(newZone)
                        .toLocalDateTime();
				payment.setPaymentDate(newDateTime);
				payment.setTransactionTime(LocalDateTime.now());
				reserve.setPayment(payment);
				reserve.setStatus(ReserveStatus.Confirmed_Seat);
				payment.setReserve(reserve);
				Reservation updateReserve = reservationRepository.save(reserve);
				updateReserve.getUser().setReserve(null);
				updateReserve.setPayment(null);
				updateReserve.getSubevent().setReserve(null);
				updateReserve.getSubevent().getEvent().setSubevent(null);
				updateReserve.getSubevent().getEvent().getChef().getUser().setChef(null);
				updateReserve.getSubevent().getEvent().getChef().getUser().setReserve(null);
				return new OmiseResponse(true, updateReserve,null, null);
			}
		}
		return new OmiseResponse(false, null, charge.getFailureCode() ,charge.getFailureMessage());
	}
	
	private Client client() throws ClientException {
		return new Client.Builder().secretKey(OMISE_SKEY).build();
	}
}
