package com.RideX.carpooling.repositories;

import com.RideX.carpooling.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT u.user_id, u.email, u.first_name, u.last_name, u.phone, u.password, u.enabled, " +
            "u.email_token, u.email_verified, u.phone_verified, u.profile_pic, u.provider, u.provider_user_id, " +
            "u.date_create, u.date_update, u.cloudinary_image_public_id, u.govt_id_verified, u.govt_id_upload, " +
            "u.govt_id_type, u.govt_id_number, u.verify_request_id, u.coins " +
            "FROM users u WHERE u.email = :email", nativeQuery = true)
    User findByEmailNative(@Param("email") String email);

    @Query(value = "SELECT u.user_id, u.email, u.first_name, u.last_name, u.phone, u.password, u.enabled, " +
            "u.email_token, u.email_verified, u.phone_verified, u.profile_pic, u.provider, u.provider_user_id, " +
            "u.date_create, u.date_update, u.cloudinary_image_public_id, u.govt_id_verified, u.govt_id_upload, " +
            "u.govt_id_type, u.govt_id_number, u.verify_request_id, u.coins " +
            "FROM users u WHERE u.user_id = :userId", nativeQuery = true)
    User findByUserIdNative(@Param("userId") String userId);

    @EntityGraph(value = "User.carDetailsAndUserDetails")
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailToken(String id);
    Optional<User> findByUserId(String userId);

    List<User> findAll();
    List<User> findAllByEmailVerified(boolean emailVerified);
    List<User> findAllByEnabled(boolean isEnabled);

    @Query("SELECT u FROM com.RideX.carpooling.model.User u WHERE LOWER(u.userId) LIKE LOWER(CONCAT('%', :userId, '%'))")
    Page<User> findAllByUserIdIgnoreCase(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT u FROM com.RideX.carpooling.model.User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    Page<User> findAllByEmailIgnoreCase(@Param("email") String email, Pageable pageable);

    @Query("SELECT u FROM com.RideX.carpooling.model.User u WHERE LOWER(u.phone) LIKE LOWER(CONCAT('%', :phone, '%'))")
    Page<User> findAllByPhoneIgnoreCase(@Param("phone") String phone, Pageable pageable);
}
