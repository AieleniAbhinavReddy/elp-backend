package com.root.repositories;

import com.root.beans.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {

    // This method fixes your error in UserRegistrationService
    List<UserRegistration> findByUserId(Long userId);

    // These methods are used by your existing RegistrationController
    Optional<UserRegistration> findByUser_UsernameAndCourse_Id(String username, Long courseId);
    void deleteByUser_UsernameAndCourse_Id(String username, Long courseId);
}