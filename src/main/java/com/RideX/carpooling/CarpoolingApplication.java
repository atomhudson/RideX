package com.RideX.carpooling;

import com.RideX.carpooling.helpers.AppConstants;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.CustomUUIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableScheduling
//public class CarpoolingApplication implements CommandLineRunner {
public class CarpoolingApplication{

	public static void main(String[] args) {
		SpringApplication.run(CarpoolingApplication.class, args);
	}

//	@Autowired
//	private UserRepository userRepo;
// 	@Autowired
//	private CustomUUIDService customUUIDService;
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	@Override
//	public void run(String... args) throws Exception {
//		User user = new User();
//		String userId = "ADM"+customUUIDService.getUUID();
//		user.setUserId(userId);
//		user.setfName("admin");
//		user.setlName("admin");
//		user.setEmail("admin@admin.com");
//		user.setPhone("123456789");
//		user.setPassword(passwordEncoder.encode("admin1234"));
//		user.setEnabled(true);
//		user.setEmailVerified(true);
//		user.setPhoneVerified(true);
//		user.setGovtIdVerified(true);
//		user.setProfilePic("https://res.cloudinary.com/dgv8awzpn/image/upload/v1732823751/samples/man-portrait.jpg");
//		user.setDateCreate(new Date());
//		user.setDateUpdate(new Date());
//		user.setRoleList(List.of(AppConstants.ROLE_ADMIN));
//		userRepo.save(user);
//		System.out.println("User created with ID: "+user.getUserId());
//	}
}

