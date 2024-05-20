package com.smylyn.TaskSubmissionApp.config;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smylyn.TaskSubmissionApp.repository.UserRepository;
import com.smylyn.TaskSubmissionApp.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
	
	private Logger log = LoggerFactory.getLogger(JWTFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// get authorization header and validate
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		log.info(request.getHeader(HttpHeaders.AUTHORIZATION));
		if (header == null || !header.startsWith("Bearer ")) {
			log.info("header is null / No Bearer");
			filterChain.doFilter(request, response);
			return;
		}
		
		// get jwt-token and validate
		final String token = header.split(" ")[1].trim();
		
		//get user identity and set it on the spring security context
		UserDetails userDetails = userRepository.findByUsername(jwtService.extractUsername(token)).orElse(null);
		
		if (!jwtService.validateToken(token, userDetails)) {
			log.info("invalid token");
			filterChain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails,
				null,
				userDetails == null ? List.of() : userDetails.getAuthorities()
				);
		
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		filterChain.doFilter(request, response);
	}

}
