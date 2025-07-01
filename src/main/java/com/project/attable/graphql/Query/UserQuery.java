package com.project.attable.graphql.Query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.project.attable.dao.EventDao;
import com.project.attable.dao.UserDao;
import com.project.attable.entity.Chef;
import com.project.attable.entity.Event;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.User;
import com.project.attable.entity.response.ChefsResponse;
import com.project.attable.entity.response.DinersResponse;
import com.project.attable.entity.response.EventsResponse;
import com.project.attable.entity.response.ReservationsResponse;
import com.project.attable.entity.response.UserResponse;
import com.project.attable.repository.ChefRepository;
import com.project.attable.repository.UserRepository;
import com.project.attable.security.responsemodel.ApiResponse;
import com.project.attable.security.responsemodel.ForgotPasswordResponse;

@Component
public class UserQuery implements GraphQLQueryResolver {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ChefRepository chefRepository;

	public User getUserById(Long id) {
		return userRepository.findByIdIn(id);
	}

	public Chef getChefById(Long id) {
		return chefRepository.findByIdIn(id);
	}
	
	public Chef getChefByUserId(Long id) {
		return chefRepository.findByUserIdIn(id);
	}

	public ChefsResponse listChefByPagination(int page, int pagesize) {
		return userDao.listChefByPagination(page, pagesize);
	}
	
	public DinersResponse listDinerByPagination(int page, int pagesize) {
		return userDao.listDinerByPagination(page, pagesize);
	}

	public ForgotPasswordResponse forgetPassword(String email) {
		return userDao.forgetPassword(email);
	}

	public ApiResponse changePassword(String token, String email, String password) {
		return userDao.changePassword(token, email, password);
	}

	public ApiResponse checkDupplicateEmail(String email) {
		return userDao.checkDupplicateEmail(email);
	}
	
	public ReservationsResponse getListReservationUserById(int page, int pagesize,Long id) {
		return userDao.getListReservationUserById(page,pagesize,id);
	}
}
