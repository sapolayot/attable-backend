package com.project.attable.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.attable.entity.Chef;
import com.project.attable.entity.ChefStatus;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.Role;
import com.project.attable.entity.RoleName;
import com.project.attable.entity.User;
import com.project.attable.entity.UserStatus;
import com.project.attable.entity.response.ChefsResponse;
import com.project.attable.entity.response.DinersResponse;
import com.project.attable.entity.response.ReservationsResponse;
import com.project.attable.entity.response.UserResponse;
import com.project.attable.repository.ChefRepository;
import com.project.attable.repository.ExperienceRepository;
import com.project.attable.repository.LanguageRepository;
import com.project.attable.repository.ReservationRepository;
import com.project.attable.repository.UserRepository;
import com.project.attable.security.JwtTokenProvider;
import com.project.attable.security.repository.RoleRepository;
import com.project.attable.security.responsemodel.ApiResponse;
import com.project.attable.security.responsemodel.ForgotPasswordResponse;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ChefRepository chefRepository;

	@Autowired
	private LanguageRepository languageRepository;
	
	@Autowired
	private ReservationRepository reserveRepository;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserResponse updateProfileDiner(User user) {
		User queryUser = userRepository.findByEmailIn(user.getEmail());
		if (queryUser != null) {
			queryUser.setProfileImage(user.getProfileImage());
			queryUser.setBirthday(user.getBirthday());
			queryUser.setGender(user.getGender());
			queryUser.setAllergic(new HashSet<>(user.getAllergic()));
			queryUser.setStatus(UserStatus.Approved);

			User result = userRepository.save(queryUser);
			return new UserResponse(true, result);
		}
		return new UserResponse(false, null);
	}

	@Override
	public UserResponse editProfileDiner(User user) {
		User queryUser = userRepository.findByEmailIn(user.getEmail());
		if (queryUser != null) {
			queryUser.setProfileImage(user.getProfileImage());
			queryUser.setFirstName(user.getFirstName());
			queryUser.setLastName(user.getLastName());
			queryUser.setBirthday(user.getBirthday());
			queryUser.setGender(user.getGender());
			queryUser.setCity(user.getCity());
			queryUser.setCountry(user.getCountry());
			queryUser.setPhoneNumber(user.getPhoneNumber());
			queryUser.setNationality(user.getNationality());
			languageRepository.deleteInBatch(queryUser.getLanguage());
			queryUser.setLanguage(new HashSet<>(user.getLanguage()));
			queryUser.setAllergic(new HashSet<>(user.getAllergic()));
			queryUser.setAboutMe(user.getAboutMe());

			User result = userRepository.save(queryUser);
			return new UserResponse(true, result);
		}
		return new UserResponse(false, null);
	}

	@Override
	public ForgotPasswordResponse forgetPassword(String email) {
		User queryUser = userRepository.findByEmailIn(email);
		if (queryUser != null) {
			return new ForgotPasswordResponse(true, queryUser.getEmail(),queryUser.getFirstName(), queryUser.getPassword());
		}
		return new ForgotPasswordResponse(false, null,null, "Unregistered email. Please recheck.");
	}

	@Override
	public ApiResponse changePassword(String token, String email, String password) {
		User queryuser = userRepository.findByEmailAndPasswordIn(email, token);
		if (queryuser == null) {
			return new ApiResponse(false, "No have User");
		}
		queryuser.setPassword(passwordEncoder.encode(password));
		userRepository.save(queryuser);
		return new ApiResponse(true, "Password Changed success");
	}

	@Override
	public UserResponse updateProfileChef(Chef chef) {
		User queryUser = userRepository.findByEmailIn(chef.getUser().getEmail());
		if (queryUser != null) {
			queryUser.setFirstName(chef.getUser().getFirstName());
			queryUser.setLastName(chef.getUser().getLastName());
			queryUser.setBirthday(chef.getUser().getBirthday());
			queryUser.setProfileImage(chef.getUser().getProfileImage());
			queryUser.setGender(chef.getUser().getGender());
			queryUser.setNationality(chef.getUser().getNationality());
			queryUser.setCity(chef.getUser().getCity());
			queryUser.setCountry(chef.getUser().getCountry());
			queryUser.setAboutMe(chef.getUser().getAboutMe());
			languageRepository.deleteInBatch(queryUser.getLanguage());
			queryUser.setLanguage(new HashSet<>(chef.getUser().getLanguage()));
			queryUser.setPhoneNumber(chef.getUser().getPhoneNumber());
			queryUser.setAllergic(new HashSet<>(chef.getUser().getAllergic()));
			queryUser.setStatus(UserStatus.Approved);
			Role role = roleRepository.findByRoleNameIn(RoleName.ROLE_CHEF);
			queryUser.setRoles(Collections.singleton(role));
			chef.setStatus(ChefStatus.Pending_for_approval);
			chef.setUser(queryUser);
			queryUser.setChef(chef);

			User result = userRepository.save(queryUser);
			return new UserResponse(true, result);
		}
		return new UserResponse(false, null);
	}

	@Override
	public UserResponse editProfileChef(Chef chef) {
		User queryUser = userRepository.findByEmailIn(chef.getUser().getEmail());
		if (queryUser != null) {
			if(queryUser.getChef().getStatus().equals(ChefStatus.Reject)) {
				queryUser.getChef().setStatus(ChefStatus.Clarify_for_request);
			}
			queryUser.setFirstName(chef.getUser().getFirstName());
			queryUser.setLastName(chef.getUser().getLastName());
			queryUser.setBirthday(chef.getUser().getBirthday());
			queryUser.setProfileImage(chef.getUser().getProfileImage());
			queryUser.setGender(chef.getUser().getGender());
			queryUser.setNationality(chef.getUser().getNationality());
			queryUser.setCity(chef.getUser().getCity());
			queryUser.setCountry(chef.getUser().getCountry());
			queryUser.setAboutMe(chef.getUser().getAboutMe());
			languageRepository.deleteInBatch(queryUser.getLanguage());
			queryUser.setLanguage(new HashSet<>(chef.getUser().getLanguage()));
			queryUser.setPhoneNumber(chef.getUser().getPhoneNumber());
			queryUser.setAllergic(new HashSet<>(chef.getUser().getAllergic()));
			queryUser.getChef().setHomeNo(chef.getHomeNo());
			queryUser.getChef().setStreet(chef.getStreet());
			queryUser.getChef().setSubDistrict(chef.getSubDistrict());
			queryUser.getChef().setDistrict(chef.getDistrict());
			queryUser.getChef().setPostalCode(chef.getPostalCode());
			queryUser.getChef().setOccupation(chef.getOccupation());
			queryUser.getChef().setFavoriteCuisine(chef.getFavoriteCuisine());
			queryUser.getChef().setSignatureDish(chef.getSignatureDish());
			queryUser.getChef().setPassportId(chef.getPassportId());
			queryUser.getChef().setPassportUrl(chef.getPassportUrl());
			queryUser.getChef().setAcc(chef.getAcc());
			experienceRepository.deleteInBatch(queryUser.getChef().getExp());
			queryUser.getChef().setExp(new ArrayList<>(chef.getExp()));
			queryUser.getChef().setUser(queryUser);
			queryUser.setChef(queryUser.getChef());

			User result = userRepository.save(queryUser);
			return new UserResponse(true, result);
		}
		return new UserResponse(false, null);
	}

	@Override
	public ApiResponse checkDupplicateEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			return new ApiResponse(false, "The email address is already registered");
		}
		return new ApiResponse(true, "No Registered");
	}
	@Override
	public ChefsResponse listChefByPagination(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page-1,pagesize, Sort.by(Sort.Direction.ASC,"id"));
	    Page<Chef> chef = chefRepository.findAll(pageRequest);
	    ChefsResponse response = new ChefsResponse();
	    response.setTotalElement(chef.getTotalElements());
	    response.setTotalPage(chef.getTotalPages());
	    response.setChef(chef.getContent());
		return response;
	}

	@Override
	public DinersResponse listDinerByPagination(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page-1,pagesize, Sort.by(Sort.Direction.ASC,"id"));
		Page<User> diner = userRepository.findByRolesIdIn(pageRequest, Long.parseLong("2"));
		DinersResponse response = new DinersResponse();
	    response.setTotalElement(diner.getTotalElements());
	    response.setTotalPage(diner.getTotalPages());
	    response.setDiner(diner.getContent());
		return response;
	}

	@Override
	public User findUserById(Long id) {
		return userRepository.findByIdIn(id);
	}

	@Override
	public ReservationsResponse getListReservationUserById(int page, int pagesize,Long id) {
		PageRequest pageRequest = PageRequest.of(page-1,pagesize, Sort.by(Sort.Direction.ASC,"id"));
	    Page<Reservation> reserve = reserveRepository.findByUserIdIn(pageRequest,id);
	    ReservationsResponse response = new ReservationsResponse();
	    response.setTotalElement(reserve.getTotalElements());
	    response.setTotalPage(reserve.getTotalPages());
	    response.setReserve(reserve.getContent());
		return response;
	}
}
