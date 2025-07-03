package com.project.attable.security.controller;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// import com.google.firebase.auth.FirebaseAuth;
// import com.google.firebase.auth.FirebaseAuthException;
// import com.google.firebase.auth.FirebaseToken;
import com.project.attable.entity.Role;
import com.project.attable.entity.User;
import com.project.attable.entity.UserStatus;
import com.project.attable.entity.response.UserResponse;
import com.project.attable.repository.UserRepository;
import com.project.attable.security.GenerateToken;
import com.project.attable.security.JwtTokenProvider;
import com.project.attable.security.repository.RoleRepository;
import com.project.attable.security.requestmodel.LoginRequest;
import com.project.attable.security.requestmodel.SignUpRequest;
import com.project.attable.security.responsemodel.JwtAuthenticationResponse;
import com.project.attable.security.responsemodel.SignInResponse;
import com.project.attable.security.responsemodel.SignUpResponse;

@RestController
@RequestMapping("/auth")
public class AuthenController {
	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	RoleRepository roleRepository;

	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest loginRequest) {
		User queryuser = userRepository.findByEmailIn(loginRequest.getEmail());

		if (!userRepository.existsByEmail(loginRequest.getEmail())) {
			return ResponseEntity.ok()
					.body(new SignInResponse(false, "Invalid User, Please recheck your email and password"));
		} else if (!passwordEncoder.matches(loginRequest.getPassword(), queryuser.getPassword())) {
			return ResponseEntity.ok()
					.body(new SignInResponse(false, "Invalid User, Please recheck your email and password"));
		}

		String jwt = GenerateToken.getTokenNotExpired(loginRequest.getEmail());

		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, queryuser));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.ok().body(new SignUpResponse(false, "This email already used"));
		}

		User user = new User();
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setPhoneNumber(signUpRequest.getPhoneNumber());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setStatus(UserStatus.Pending_for_approval);
		user.setFacebookUser(false);

		Role userRole = roleRepository.findByRoleNameIn(signUpRequest.getRoleName());

		user.setRoles(Collections.singleton(userRole));

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{userId}")
				.buildAndExpand(result.getId()).toUri();
		
		String jwt = GenerateToken.getTokenNotExpired(signUpRequest.getEmail());

		return ResponseEntity.created(location).body(new SignUpResponse(true, "Successfully signup", jwt,
				user.getEmail(), user.getFirstName(), user.getLastName(), user.getRoles(), user.isFacebookUser()));
	}

	@PostMapping("/validateToken")
	public ResponseEntity<?> validateToken(@Valid @RequestBody Map<String, String> request) {
		String authToken = request.get("token");
		String email = request.get("email");
		
		Long id = tokenProvider.getUserIdFromJWT(authToken);
		User queryuser = userRepository.findByIdIn(id);
		if (queryuser != null) {
			if (!email.equals(queryuser.getEmail())) {
				return ResponseEntity.badRequest().build();
			}
			if(tokenProvider.validateToken(authToken)) {
				return ResponseEntity.ok(new UserResponse(true, queryuser));
			}
		}

		return ResponseEntity.badRequest().build();
	}
	
	//Add resend token
	

	// @PostMapping("/fbauth")
	// public ResponseEntity<?> authenticationByFacebook(@Valid @RequestBody FacebookAuthData facebookAuthData) {
	// 	try {
	// 		FirebaseToken data = FirebaseAuth.getInstance().verifyIdToken(facebookAuthData.getToken());

	// 		if (data.isEmailVerified()) {
	// 			return ResponseEntity.ok().body("Email not verified");
	// 		}
	// 		if (userRepository.existsByEmail(facebookAuthData.getEmail())) {
	// 			User queryuser = userRepository.findByEmailIn(facebookAuthData.getEmail());
	// 			try {
	// 				String jwt = GenerateToken.getTokenNotExpired(facebookAuthData.getEmail());

	// 				return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, queryuser));
	// 			} catch (Exception e) {
	// 				e.printStackTrace();
	// 				return ResponseEntity.badRequest().body("Please check email or password again");
	// 			}
	// 		}

	// 		User user = new User();
	// 		user.setFirstName(facebookAuthData.getFirstName());
	// 		user.setLastName(facebookAuthData.getLastName());
	// 		user.setEmail(facebookAuthData.getEmail());
	// 		user.setPassword(passwordEncoder.encode(facebookAuthData.getUid()));
	// 		user.setProfileImage(facebookAuthData.getProfileImage());
	// 		user.setBirthday(facebookAuthData.getBirthday());
	// 		user.setStatus(UserStatus.Pending_for_approval);
	// 		user.setFacebookUser(true);
	// 		Role userRole = roleRepository.findByRoleNameIn(facebookAuthData.getRoleName());

	// 		user.setRoles(Collections.singleton(userRole));

	// 		User result = userRepository.save(user);

	// 		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{userId}")
	// 				.buildAndExpand(result.getId()).toUri();

	// 		String jwt = GenerateToken.getTokenNotExpired(facebookAuthData.getEmail());

	// 		return ResponseEntity.created(location)
	// 				.body(new SignUpResponse(true, "You have successfully signed up.", jwt, user.getEmail(),
	// 						user.getFirstName(), user.getLastName(), user.getRoles(), user.isFacebookUser()));
	// 	} catch (FirebaseAuthException e) {
	// 		e.printStackTrace();
	// 		return ResponseEntity.badRequest().body(e.getMessage());
	// 	}

	// }

}
