package com.smylyn.TaskSubmissionApp.web;

import java.net.http.HttpResponse;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smylyn.TaskSubmissionApp.domain.dto.AuthCredentialsRequest;
import com.smylyn.TaskSubmissionApp.domain.dto.AuthCredentialsResponse;
import com.smylyn.TaskSubmissionApp.domain.dto.AuthSetupRequest;
import com.smylyn.TaskSubmissionApp.domain.model.User;
import com.smylyn.TaskSubmissionApp.repository.UserRepository;
import com.smylyn.TaskSubmissionApp.service.JwtService;
import com.smylyn.TaskSubmissionApp.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AuthenticationManager authManager;
	
//	@PostMapping("/signup")
//	public ResponseEntity<?> signup(@RequestBody AuthSetupRequest request) {
//		System.out.println("in signup controller");
//		User user = new User();
//		user.setCohortStartDate(LocalDate.of(2024, 5, 1));
//		user.setUsername(request.username());
//		user.setPassword(request.password());
//		user.setIsEnabled(true);
//		userRepo.save(user);
//		
//		return ResponseEntity.ok("sign up successfull");
//		
//		
//	}
//	
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody AuthCredentialsRequest request) {
//		
//		return ResponseEntity.ok("Hello World");
//	}

	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthCredentialsRequest request) {
		System.out.println("in login controller");
		try {
//			Authentication authenticate = authManager.authenticate(
//					new UsernamePasswordAuthenticationToken(
//							request.username(), request.password()
//							)
//					);

			UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
			String token = jwtService.GenerateToken(userDetails.getUsername());
			
			AuthCredentialsResponse response = new AuthCredentialsResponse("success", userDetails.getUsername());
			
			return ResponseEntity.ok()
					.header(HttpHeaders.AUTHORIZATION, token)
					.body(response);
			
		} catch(BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

}
