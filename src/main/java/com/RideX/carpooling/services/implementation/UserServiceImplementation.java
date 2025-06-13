package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.helpers.AppConstants;
import com.RideX.carpooling.helpers.Helper;
import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.CustomUUIDService;
import com.RideX.carpooling.services.EmailServices;
import com.RideX.carpooling.services.UserServices;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImplementation implements UserServices {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailServices emailService;

    @Autowired
    private Helper helper;

    @Autowired
    private CustomUUIDService customUUID;

    @Override
    public User saveUser(User user) {
        String userId = "USR"+customUUID.getUUID();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        logger.info("Saving user: {}",user);
        logger.info("User created using: {}",user.getUserId());

        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        logger.info("email token created using: {} for user: {}",emailToken,user.getUserId());

        User savedUser = userRepository.save(user);

        String emailLink = helper.getLinkForEmailVerification(emailToken);

        emailService.sendEmail(savedUser.getEmail(), "Verify Your Account : RideX", emailLink);
        return savedUser;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findByUserIdNative(id);
    }

    @Override
    public List<Rides> getRidesJoinedByUser(String userId) {
        return userRepository.findByUserId(userId)
                .map(User::getRidesJoined)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> verifiedEmail(boolean isEmailVerified) {
        return userRepository.findAllByEmailVerified(isEmailVerified);
    }

    @Override
    public List<User> enabledUser(boolean isEnabled) {
        return userRepository.findAllByEnabled(isEnabled);
    }

    @Override
    public Page<User> getAllUsers(int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equals("desc") ? Sort.by(sortField).descending() : Sort.by(sortDirection).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }

    public static Pageable safePageRequest(int page, int size, Sort sort) {
        int validSize = size < 1 ? AppConstants.PAGE_SIZE : size;
        return PageRequest.of(Math.max(page, 0), validSize, sort);
    }


    @Override
    public Page<User> searchByUserId(String userId, int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        var pageable = safePageRequest(page, size, sort);
        Page<User> userPage = userRepository.findAllByUserIdIgnoreCase(userId,pageable);
        System.out.println("Service page :"+userPage.getTotalElements());
        return userPage;
    }

    @Override
    public Page<User> searchByEmail(String email, int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Pageable pageable = safePageRequest(page, size, sort);
        Page<User> userPage = userRepository.findAllByEmailIgnoreCase(email, pageable);
        System.out.println("Service page: " + userPage.getTotalElements());
        return userPage;
    }

    @Override
    public Page<User> searchByPhone(String phone, int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        var pageable = safePageRequest(page, size, sort);
        Page<User> userPage = userRepository.findAllByPhoneIgnoreCase(phone,pageable);
        System.out.println("Service page :"+userPage.getTotalElements());
        return userPage;
    }
}
