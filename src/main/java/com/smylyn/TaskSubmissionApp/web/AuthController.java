package com.smylyn.TaskSubmissionApp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smylyn.TaskSubmissionApp.domain.dto.AuthCredentialsRequest;
import com.smylyn.TaskSubmissionApp.domain.dto.AuthCredentialsResponse;
import com.smylyn.TaskSubmissionApp.domain.dto.AuthSetupRequest;
import com.smylyn.TaskSubmissionApp.domain.model.User;
import com.smylyn.TaskSubmissionApp.repository.UserRepository;
import com.smylyn.TaskSubmissionApp.service.JwtService;
import com.smylyn.TaskSubmissionApp.service.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	private Logger log = LoggerFactory.getLogger(AuthController.class);
	

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthCredentialsRequest request) {
		System.out.println("in login controller");
//		try {
//			Authentication authentication = authManager.authenticate(
//					new UsernamePasswordAuthenticationToken(
//							request.username(), request.password()
//							)
//					);
//			log.info(authentication.getCredentials().toString());
//			User user = (User) authentication.getPrincipal();
////			UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
//			String token = jwtService.GenerateToken(user.getUsername());
//			
//			AuthCredentialsResponse response = new AuthCredentialsResponse("success", user.getUsername());
//			
//			return ResponseEntity.ok()
//					.header(HttpHeaders.AUTHORIZATION, token)
//					.body(response);
//			
//		} catch(BadCredentialsException ex) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
		
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
	    log.info("authentication value is " + authentication.isAuthenticated());
		if(authentication.isAuthenticated()){
			User user = (User) authentication.getPrincipal();
			String token = jwtService.GenerateToken(user.getUsername());
			
			AuthCredentialsResponse response = new AuthCredentialsResponse("success", user.getUsername());
			
			return ResponseEntity.ok()
					.header(HttpHeaders.AUTHORIZATION, token)
					.body(response);
	    } else {
	    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	}
	
	@GetMapping("/validate")
	public ResponseEntity<?> isTokenValid(@RequestParam String token, @AuthenticationPrincipal User user) {
		try {
			return ResponseEntity.ok(jwtService.validateToken(token, user));
			
		}catch(ExpiredJwtException e) {
				return ResponseEntity.ok(false);
				}
		}
}
