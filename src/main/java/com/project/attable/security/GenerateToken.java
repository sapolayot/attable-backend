package com.project.attable.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.project.attable.entity.Reservation;
import com.project.attable.repository.ReservationRepository;

@Component
public class GenerateToken {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserDetailsService userDetailsService;

	private static ReservationRepository reservationRepository0;
	
	private static JwtTokenProvider tokenProvider0;
	
	private static UserDetailsService userDetailsService0;
	
	@PostConstruct
	private void initStaticDao() {
		reservationRepository0 = this.reservationRepository;
		tokenProvider0 = this.tokenProvider;
		userDetailsService0 = this.userDetailsService;
	}

	public static String getTokenForEmailByReserveId(Long reserveId, String email) {

		final UserDetails userDetails = userDetailsService0.loadUserByUsername(email);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

		if (!user.getEmail().equals(email)) {
			return null;
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider0.generateTokenNotExpired(authentication);//Generate

		Reservation oldReservation = reservationRepository0.findByIdIn(reserveId);

		oldReservation.setTokenEmail(jwt);

		reservationRepository0.save(oldReservation);

		return jwt;
	}

	public static String getToken(String email) {

		final UserDetails userDetails = userDetailsService0.loadUserByUsername(email);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

		if (!user.getEmail().equals(email)) {
			return null;
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider0.generateToken(authentication);

		return jwt;
	}
	
	public static String getTokenNotExpired(String email) {

		final UserDetails userDetails = userDetailsService0.loadUserByUsername(email);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

		if (!user.getEmail().equals(email)) {
			return null;
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider0.generateTokenNotExpired(authentication);

		return jwt;
	}

}
