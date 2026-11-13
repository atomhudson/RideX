package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.helpers.ResourceNotFoundException;
import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.DetailsRepository;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DetailsRepository detailsRepository;

    private Pageable safePageRequest(int page, int size, Sort sort) {
        if (size < 1) size = 5;
        if (page < 0) page = 0;
        return PageRequest.of(page, size, sort);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getNavbarUserByEmail(String email) {
        return userRepository.findNavbarByEmail(email)
                .orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<Rides> getRidesJoinedByUser(String userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            return userOptional.get().getRidesJoined();
        } else {
            return Collections.emptyList();
        }
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
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Pageable pageable = safePageRequest(page, size, sort);
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> searchByUserId(String userId, int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Pageable pageable = safePageRequest(page, size, sort);
        return userRepository.findAllByUserIdIgnoreCase(userId, pageable);
    }

    @Override
    public Page<User> searchByEmail(String email, int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Pageable pageable = safePageRequest(page, size, sort);
        return userRepository.findAllByEmailIgnoreCase(email, pageable);
    }

    @Override
    public Page<User> searchByPhone(String phone, int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Pageable pageable = safePageRequest(page, size, sort);
        return userRepository.findAllByPhoneIgnoreCase(phone, pageable);
    }

    @Override
    public Page<User> searchUsersUniversal(String keyword, int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Pageable pageable = safePageRequest(page, size, sort);
        return userRepository.searchUsersUniversal(keyword, pageable);
    }
}
