package com.project.attable.dao;

import com.project.attable.entity.Payment;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.ReserveStatus;
import com.project.attable.entity.SubEvent;
import com.project.attable.entity.User;
import com.project.attable.entity.response.PaymentsResponse;
import com.project.attable.entity.response.ReservationResponse;
import com.project.attable.entity.response.ReservationsResponse;
import com.project.attable.repository.PaymentRepository;
import com.project.attable.repository.ReservationRepository;
import com.project.attable.repository.SubEventRepository;
import com.project.attable.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDaoImpl implements ReservationDao {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	SubEventRepository subEventRepository;

	@Value("${gapDay.eventReview}")
	private int eventReview;

	@Value("${gapDay.eventRelease}")
	private int eventRelease;

	@Value("${gapDay.eventPrepare}")
	private int eventPrepare;

	@Value("${gapDay.emailMinimumSeat}")
	private int emailMinimumSeat;

	@Value("${gapDay.emailConfirmSeat}")
	private int emailConfirmSeat;

	@Override
	public ReservationsResponse listReservationPagination(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<Reservation> reservations = reservationRepository.findAll(pageRequest);
		ReservationsResponse response = new ReservationsResponse();
		response.setTotalElement(reservations.getTotalElements());
		response.setTotalPage(reservations.getTotalPages());
		response.setReserve(reservations.getContent());
		return response;
	}

	@Override
	public ReservationsResponse listRequestingReservationPagination(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<Reservation> reservations = reservationRepository.findByStatusIn(pageRequest, ReserveStatus.Requesting);
		ReservationsResponse response = new ReservationsResponse();
		response.setTotalElement(reservations.getTotalElements());
		response.setTotalPage(reservations.getTotalPages());
		response.setReserve(reservations.getContent());
		return response;
	}

	@Override
	public ReservationsResponse listRejectReservationPagination(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<Reservation> reservations = reservationRepository.findByStatusIn(pageRequest, ReserveStatus.Reject);
		ReservationsResponse response = new ReservationsResponse();
		response.setTotalElement(reservations.getTotalElements());
		response.setTotalPage(reservations.getTotalPages());
		response.setReserve(reservations.getContent());
		return response;
	}

	@Override
	public ReservationsResponse listConfirmSeatReservationPagination(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<Reservation> reservations = reservationRepository.findByStatusIn(pageRequest,
				ReserveStatus.Confirmed_Seat);
		ReservationsResponse response = new ReservationsResponse();
		response.setTotalElement(reservations.getTotalElements());
		response.setTotalPage(reservations.getTotalPages());
		response.setReserve(reservations.getContent());
		return response;
	}

	@Override
	public ReservationsResponse listCancelReservationPagination(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<Reservation> reservations = reservationRepository.findByStatusIn(pageRequest, ReserveStatus.Cancelled);
		ReservationsResponse response = new ReservationsResponse();
		response.setTotalElement(reservations.getTotalElements());
		response.setTotalPage(reservations.getTotalPages());
		response.setReserve(reservations.getContent());
		return response;
	}

	@Override
	public Reservation getReservationById(Long id) {
		Reservation reserve = reservationRepository.findByIdIn(id);
		return reserve;
	}

	@Override
	public Payment getPaymentById(Long id) {
		Payment payment = paymentRepository.findByIdIn(id);
		return payment;
	}

	@Override
	public PaymentsResponse listPaymentPagination(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<Payment> payment = paymentRepository.findAll(pageRequest);
		PaymentsResponse response = new PaymentsResponse();
		response.setTotalElement(payment.getTotalElements());
		response.setTotalPage(payment.getTotalPages());
		response.setPayment(payment.getContent());
		return response;
	}

	@Override
	public List<Reservation> listAllDinerInSubEventBySubEventId(Long id) {
		List<Reservation> listAllDiner = new ArrayList<>();
		SubEvent subEvent = subEventRepository.findByIdIn(id);
		if (subEvent != null && !subEvent.getReserve().isEmpty()) {
			for (Reservation reserve : subEvent.getReserve()) {
				listAllDiner.add(reserve);
			}
		}
		return listAllDiner;
	}

	@Override
	public ReservationResponse bookingEvent(Reservation reserve) {
		User user = userRepository.findByIdIn(reserve.getUser().getId());
		SubEvent subEvent = subEventRepository.findByIdIn(reserve.getSubevent().getId());
		if (user == null) {
			return new ReservationResponse(false, null, "User not found");
		} else if (subEvent == null) {
			return new ReservationResponse(false, null, "Subevent not found");
		} else {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime eventDate = subEvent.getEventDate();
			long dayEvent = now.until(eventDate, ChronoUnit.DAYS) + 1;
			long bookingDay = eventReview + eventRelease;
			if (dayEvent < bookingDay) {
				return new ReservationResponse(false, null, "Can not booking at gap day");
			}
			if (subEvent.getCurrentSeat() < reserve.getJoin().size()) {
				return new ReservationResponse(false, null, "Seat not enough");
			}
			reserve.setStatus(ReserveStatus.Requesting);
			reserve.setRequestingDate(LocalDateTime.now());
			for (int i = 0; i < reserve.getJoin().size(); i++) {
				reserve.setSeatNumber(String.format("%05d", i + 1));
			}
			reserve.setUser(user);
			reserve.setRefundable(false);
			reserve.setSubevent(subEvent);
			int currentSeat = subEvent.getCurrentSeat() - reserve.getJoin().size();
			subEvent.setCurrentSeat(currentSeat);
			subEventRepository.save(subEvent);
			return new ReservationResponse(true, reservationRepository.save(reserve), null);
		}
	}

	@Override
	public ReservationResponse paymentBooking(Payment payment) {
		Reservation oldReserve = reservationRepository.findByIdIn(payment.getReserve().getId());
		if (oldReserve != null) {
			if (oldReserve.getStatus().equals(ReserveStatus.Pending_Payment_Review)) {
				oldReserve.setStatus(ReserveStatus.Clarify_for_review_booking);
			}
			if (oldReserve.getPayment() != null) {
				Payment oldPayment = oldReserve.getPayment();
				oldPayment.setPaymentDate(payment.getPaymentDate());
				oldPayment.setSlipUrl(payment.getSlipUrl());
				oldReserve.setReserveDate(LocalDateTime.now());
				oldPayment.setTransactionTime(LocalDateTime.now());
				oldPayment.setPrice(payment.getPrice());
				oldPayment.setReserve(oldReserve);
				oldReserve.setPayment(oldPayment);
			} else {
				oldReserve.setReserveDate(LocalDateTime.now());
				payment.setTransactionTime(LocalDateTime.now());
				payment.setReserve(oldReserve);
				oldReserve.setPayment(payment);
			}
			return new ReservationResponse(true, reservationRepository.save(oldReserve), null);
		}
		return new ReservationResponse(false, null, "Reservation Not found");
	}

	@Override
	public ReservationResponse editReservationStatus(Reservation reserve) {
		Reservation oldReservation = reservationRepository.findByIdIn(reserve.getId());
		if (oldReservation != null) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime eventDate = oldReservation.getSubevent().getEventDate();
			long dayEventDate = now.until(eventDate, ChronoUnit.DAYS) + 1;
			if (dayEventDate < emailConfirmSeat) {
				return new ReservationResponse(false, null, "Can not approved/reject booking at gap day");
			}
			oldReservation.setStatus(reserve.getStatus());
			if (reserve.getStatus().equals(ReserveStatus.Confirmed_Seat)) {
				oldReservation.setApproveDate(LocalDateTime.now());
				reserve.setRefundable(true);
			} else if (reserve.getStatus().equals(ReserveStatus.Pending_Payment_Review)) {
				if (oldReservation.getDetailReject() == null) {
					oldReservation.setDetailReject(new ArrayList<>());
				}
				reserve.getDetailReject().get(0).setRejectDate(LocalDateTime.now());
				oldReservation.getDetailReject().add(reserve.getDetailReject().get(0));
			} else if (reserve.getStatus().equals(ReserveStatus.Reject)) {
				int currentSeat = oldReservation.getSubevent().getCurrentSeat() + oldReservation.getJoin().size();
				oldReservation.getSubevent().setCurrentSeat(currentSeat);
			}
			return new ReservationResponse(true, reservationRepository.save(oldReservation), null);
		}
		return new ReservationResponse(false, null, "Reservation not found");
	}

	@Override
	public ReservationResponse cancelReservation(Long id) {
		Reservation oldReservation = reservationRepository.findByIdIn(id);
		if (oldReservation != null) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime eventDate = oldReservation.getSubevent().getEventDate();
			long dayEventDate = now.until(eventDate, ChronoUnit.DAYS) + 1;
			if (dayEventDate <= 0) {
				return new ReservationResponse(false, null, "Can not cancel at gap day");
			}
			oldReservation.setStatus(ReserveStatus.Cancel_Requesting);
			return new ReservationResponse(true, reservationRepository.save(oldReservation), null);
		}
		return new ReservationResponse(false, null, "Reservation not found");
	}

	@Override
	public ReservationResponse updateCancelReservationStatus(Reservation reserve) {
		Reservation oldReservation = reservationRepository.findByIdIn(reserve.getId());
		if (oldReservation != null) {
			ReserveStatus status = reserve.getStatus();
			if (status.equals(ReserveStatus.Pending) || status.equals(ReserveStatus.Confirmed_Seat)) {
				oldReservation.setStatus(status);
				if (status.equals(ReserveStatus.Pending)) {
					if (reserve.getDetailCancelReject() != null) {
						return new ReservationResponse(false, null, "Can't add reject cancel in Pending");
					}
					int currentSeat = oldReservation.getSubevent().getCurrentSeat() + oldReservation.getJoin().size();
					oldReservation.getSubevent().setCurrentSeat(currentSeat);
					return new ReservationResponse(true, reservationRepository.save(oldReservation), null);
				} else if (status.equals(ReserveStatus.Confirmed_Seat)) {
					if (reserve.getDetailCancelReject() == null) {
						return new ReservationResponse(false, null, "Please input reject cancel");
					}
					if (oldReservation.getDetailCancelReject() == null) {
						oldReservation.setDetailCancelReject(new ArrayList<>());
					}
					reserve.getDetailCancelReject().get(0).setRejectDate(LocalDateTime.now());
					oldReservation.getDetailCancelReject().add(reserve.getDetailCancelReject().get(0));
					return new ReservationResponse(true, reservationRepository.save(oldReservation), null);
				}
			}
			return new ReservationResponse(false, null, "Can not update status");
		}
		return new ReservationResponse(false, null, "Reservation not found");
	}
}
