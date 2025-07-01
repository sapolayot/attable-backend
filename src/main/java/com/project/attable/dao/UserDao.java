package com.project.attable.dao;

import java.util.List;

import com.project.attable.entity.Chef;
import com.project.attable.entity.User;
import com.project.attable.entity.response.ChefsResponse;
import com.project.attable.entity.response.DinersResponse;
import com.project.attable.entity.response.ReservationsResponse;
import com.project.attable.entity.response.UserResponse;
import com.project.attable.security.responsemodel.ApiResponse;
import com.project.attable.security.responsemodel.ForgotPasswordResponse;

public interface UserDao {
	
	// Diner
	UserResponse updateProfileDiner(User user);

	UserResponse editProfileDiner(User user);

	// Chef

	UserResponse updateProfileChef(Chef chef);

	UserResponse editProfileChef(Chef chef);

	// Forgot Password

	ForgotPasswordResponse forgetPassword(String email);

	ApiResponse changePassword(String token, String email, String password);

	// Check exist email
	ApiResponse checkDupplicateEmail(String email);

	
	//Admin
	ReservationsResponse getListReservationUserById(int page, int pagesize,Long id);
	
	ChefsResponse listChefByPagination(int page, int pagesize);

	DinersResponse listDinerByPagination(int page, int pagesize);

	User findUserById(Long id);

}
