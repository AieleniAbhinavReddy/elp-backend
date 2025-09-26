package com.root.repositories;

import com.root.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// The ID of User is now Long
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // Method to find user by the reset token
    Optional<User> findByResetPasswordToken(String token);
}