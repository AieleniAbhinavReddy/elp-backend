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
}