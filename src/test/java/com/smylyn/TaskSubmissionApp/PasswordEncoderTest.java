package com.smylyn.TaskSubmissionApp;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {
	

	@Test
	void encode_password() {
		PasswordEncoder pe = new BCryptPasswordEncoder();
		System.out.println(pe.encode("hello"));
		System.out.println(pe.encode("cheta"));
		
	}


}
