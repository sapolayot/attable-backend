package com.project.attable.graphql.Query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.project.attable.dao.ReservationDao;
import com.project.attable.entity.Payment;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.User;
import com.project.attable.entity.response.PaymentsResponse;
import com.project.attable.entity.response.ReservationResponse;
import com.project.attable.entity.response.ReservationsResponse;
import com.project.attable.repository.ReservationRepository;

@Component
public class ReservationQuery implements GraphQLQueryResolver {

	@Autowired
	private ReservationDao reservationDao;
	
	public ReservationsResponse listReservationPagination(int page, int pagesize) {
		return reservationDao.listReservationPagination(page, pagesize);
	}
	
	public ReservationsResponse listRequestingReservationPagination(int page, int pagesize) {
		return reservationDao.listRequestingReservationPagination(page, pagesize);
	}
	
	public ReservationsResponse listRejectReservationPagination(int page, int pagesize) {
		return reservationDao.listRejectReservationPagination(page, pagesize);
	}
	
	public ReservationsResponse listConfirmSeatReservationPagination(int page, int pagesize) {
		return reservationDao.listConfirmSeatReservationPagination(page, pagesize);
	}
	
	public ReservationsResponse listCancelReservationPagination(int page, int pagesize) {
		return reservationDao.listCancelReservationPagination(page, pagesize);
	}
	
	public Reservation getReservationById(Long id) {
		return reservationDao.getReservationById(id);
	}
	
	public PaymentsResponse listPaymentPagination(int page, int pagesize) {
		return reservationDao.listPaymentPagination(page, pagesize);
	}
	
	public Payment getPaymentById(Long id) {
		return reservationDao.getPaymentById(id);
	}

	public List<Reservation> listAllDinerInSubEventBySubEventId(Long id) {
		return reservationDao.listAllDinerInSubEventBySubEventId(id);
	}
}