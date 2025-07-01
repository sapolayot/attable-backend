package com.project.attable.dao;

import java.util.List;

import com.project.attable.entity.Payment;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.response.PaymentsResponse;
import com.project.attable.entity.response.ReservationResponse;
import com.project.attable.entity.response.ReservationsResponse;

public interface ReservationDao {
	// #Mutation

	// Chef,Diner
	ReservationResponse bookingEvent(Reservation reserve);

	ReservationResponse paymentBooking(Payment payment);

	// Admin
	ReservationResponse editReservationStatus(Reservation reserve);
	ReservationResponse updateCancelReservationStatus(Reservation reserve);

	// Chef,Diner
	ReservationResponse cancelReservation(Long id);

	/** #Queries */
	// Admin
	ReservationsResponse listReservationPagination(int page, int pagesize);

	ReservationsResponse listRequestingReservationPagination(int page, int pagesize);

	ReservationsResponse listRejectReservationPagination(int page, int pagesize);

	ReservationsResponse listConfirmSeatReservationPagination(int page, int pagesize);

	ReservationsResponse listCancelReservationPagination(int page, int pagesize);

	Reservation getReservationById(Long id);

	Payment getPaymentById(Long id);

	PaymentsResponse listPaymentPagination(int page, int pagesize);

	List<Reservation> listAllDinerInSubEventBySubEventId(Long Id);
	// byte[] getQrCodeFromPhoneNumber(String paymentAmount) throws Exception;

}
