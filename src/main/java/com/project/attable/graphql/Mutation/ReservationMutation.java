package com.project.attable.graphql.Mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.project.attable.dao.ReservationDao;
import com.project.attable.entity.EventStatus;
import com.project.attable.entity.Payment;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.ReserveStatus;
import com.project.attable.entity.SubEvent;
import com.project.attable.entity.User;
import com.project.attable.entity.response.EventResponse;
import com.project.attable.entity.response.PaymentResponse;
import com.project.attable.entity.response.ReservationResponse;
import com.project.attable.entity.response.SubEventResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ReservationMutation implements GraphQLMutationResolver{

    @Autowired
    ReservationDao reservationDao;

    @PreAuthorize("hasAnyRole('ROLE_DINER','ROLE_CHEF')")
	public ReservationResponse bookingEvent(Reservation reserve) {
		return reservationDao.bookingEvent(reserve);
	}
    
    @PreAuthorize("hasAnyRole('ROLE_DINER','ROLE_CHEF')")
	public ReservationResponse paymentBooking(Payment payment) {
		return reservationDao.paymentBooking(payment);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ReservationResponse editReservationStatus(Reservation reserve) {
		return reservationDao.editReservationStatus(reserve);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ReservationResponse updateCancelReservationStatus(Reservation reserve) {
		return reservationDao.updateCancelReservationStatus(reserve);
	}
    
	@PreAuthorize("hasAnyRole('ROLE_DINER','ROLE_CHEF')")
	public ReservationResponse cancelReservation(Long id) {
		return reservationDao.cancelReservation(id);
	}
}
