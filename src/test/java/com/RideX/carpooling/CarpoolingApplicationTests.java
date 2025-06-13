package com.RideX.carpooling;

import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.EmailServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CarpoolingApplicationTests {
	@Autowired
	private EmailServices emailService;

	private UserRepository userRepository;

//	@Test
//	void sendEmailTest() {
//		System.out.println("Email sending");
//		emailService.sendEmail("sainipra16@gmail.com", "Email from ConnectMate Application", "Test");
//	}

}
