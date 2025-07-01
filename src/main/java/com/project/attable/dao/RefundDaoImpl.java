package com.project.attable.dao;

import com.project.attable.afteraspect.CancelEventAspect;
import com.project.attable.entity.AccountForRefund;
import com.project.attable.entity.EventStatus;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.ReserveStatus;
import com.project.attable.entity.response.RefundResponse;
import com.project.attable.repository.ReservationRepository;
import com.project.attable.security.JwtTokenProvider;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RefundDaoImpl implements RefundDao {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	JwtTokenProvider tokenProvider;

	// Diner upload bankstatement and update bankstatement
	@Override
	public RefundResponse addBankaccountForRefund(Reservation reserve, String authToken) {
		Reservation oldReserve = reservationRepository.findByIdIn(reserve.getId());
		if (oldReserve != null) {
			oldReserve.setStatus(ReserveStatus.Clarify_for_review_refund);
			if (oldReserve.getAccountForRefund() == null) {
				if (!tokenProvider.validateToken(authToken) && !oldReserve.getTokenEmail().equals(authToken)) {
					return new RefundResponse(false, null, "Expired token");
				}
				AccountForRefund newAccount = new AccountForRefund();
				newAccount.setAccountName(reserve.getAccountForRefund().getAccountName());
				newAccount.setAccountNumber(reserve.getAccountForRefund().getAccountNumber());
				newAccount.setBankName(reserve.getAccountForRefund().getBankName());
				newAccount.setBankStatementUrl(reserve.getAccountForRefund().getBankStatementUrl());
				newAccount.setBranch(reserve.getAccountForRefund().getBranch());
				oldReserve.setAccountForRefund(newAccount);
			} else {
				oldReserve.getAccountForRefund().setAccountName(reserve.getAccountForRefund().getAccountName());
				oldReserve.getAccountForRefund().setAccountNumber(reserve.getAccountForRefund().getAccountNumber());
				oldReserve.getAccountForRefund().setBankName(reserve.getAccountForRefund().getBankName());
				oldReserve.getAccountForRefund()
						.setBankStatementUrl(reserve.getAccountForRefund().getBankStatementUrl());
				oldReserve.getAccountForRefund().setBranch(reserve.getAccountForRefund().getBranch());
			}
			return new RefundResponse(true, reservationRepository.save(oldReserve).getAccountForRefund(), null);
		}
		return new RefundResponse(false, null, "Not have reservation");
	}

	// Admin update payment for cancelled
	@Override
	public RefundResponse updateBankAccountForRefund(Reservation reserve) {
		Reservation oldReserve = reservationRepository.findByIdIn(reserve.getId());
		if (oldReserve != null) {
			if (oldReserve.getAccountForRefund() == null) {
				return new RefundResponse(false, null, "Not have account for refund");
			}
			ReserveStatus status = reserve.getStatus();
			AccountForRefund acc = oldReserve.getAccountForRefund();
			oldReserve.setStatus(status);
			if (status.equals(ReserveStatus.Pending_Payment_Review)) {
				acc.setAccountName(null);
				acc.setAccountNumber(null);
				acc.setBankName(null);
				acc.setBranch(null);
				acc.setBankStatementUrl(null);
			} else if (status.equals(ReserveStatus.Cancelled)) {
				acc.setSlipUrl(reserve.getAccountForRefund().getSlipUrl());
				acc.setDateTimetransfer(reserve.getAccountForRefund().getDateTimetransfer());
				acc.setText(reserve.getAccountForRefund().getText());
				acc.setAmount(reserve.getAccountForRefund().getAmount());
				acc.setStatus(true);// add status
				oldReserve.setCancelDate(LocalDateTime.now());
			}
			oldReserve.setAccountForRefund(acc);
			boolean checkAllUploadSlip = true;
			for (Reservation r : oldReserve.getSubevent().getReserve()) {
				if (r.getAccountForRefund() == null) {
					checkAllUploadSlip = false;
					break;
				} else {
					if (r.getAccountForRefund().getBankStatementUrl() == null
							&& r.getAccountForRefund().getSlipUrl() == null) {
						checkAllUploadSlip = false;
						break;
					}
				}
			}
			if (checkAllUploadSlip) {
				oldReserve.getSubevent().setStatus(EventStatus.Cancelled);
			}
			return new RefundResponse(true, reservationRepository.save(oldReserve).getAccountForRefund(), null);
		}
		return new RefundResponse(false, null, "Not have reservation");
	}

}
