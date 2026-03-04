package com.root.services;

import com.root.beans.Course;
import com.root.beans.User;
import com.root.beans.UserRegistration;
import com.root.repositories.UserRepository;
import com.root.repositories.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRegistrationService {

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Course> findCoursesByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // This will now work correctly because UserRegistrationRepository has the method
        List<UserRegistration> registrations = userRegistrationRepository.findByUserId(user.getId());

        return registrations.stream()
                            .map(UserRegistration::getCourse)
                            .collect(Collectors.toList());
    }

    public List<UserRegistration> findRegistrationsByUsername(String username) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return userRegistrationRepository.findByUser_Username(username);
    }

    public UserRegistration updateProgress(String username, Long courseId, Integer completionPercentage, String lastAccessedLesson) {
        UserRegistration registration = userRegistrationRepository
                .findByUser_UsernameAndCourse_Id(username, courseId)
                .orElseThrow(() -> new IllegalArgumentException("You are not registered for this course."));

        int safePercentage = completionPercentage == null ? 0 : Math.max(0, Math.min(100, completionPercentage));
        registration.setCompletionPercentage(safePercentage);
        registration.setLastAccessedLesson(lastAccessedLesson);
        registration.setCompleted(safePercentage >= 100);

        return userRegistrationRepository.save(registration);
    }
}