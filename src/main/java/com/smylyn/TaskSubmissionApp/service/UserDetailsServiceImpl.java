package com.smylyn.TaskSubmissionApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smylyn.TaskSubmissionApp.domain.model.User;
import com.smylyn.TaskSubmissionApp.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	
	@Autowired UserRepository userRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);

		return user.orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
	}

}
