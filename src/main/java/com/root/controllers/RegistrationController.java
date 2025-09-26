package com.root.controllers;

import com.root.beans.Course;
import com.root.beans.User;
import com.root.beans.UserRegistration;
import com.root.repositories.CourseRepository;
import com.root.repositories.UserRepository;
import com.root.repositories.UserRegistrationRepository;
import com.root.services.UserRegistrationService; // <-- ADD IMPORT
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails; // <-- ADD IMPORT
import org.springframework.web.bind.annotation.*;

import java.util.List; // <-- ADD IMPORT

@RestController
@RequestMapping("/api/registrations")
//@CrossOrigin(origins = "http://localhost:3000")
public class RegistrationController {

    @Autowired private UserRegistrationRepository registrationRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private UserRepository userRepository;

    @Autowired // <-- INJECT THE NEW SERVICE
    private UserRegistrationService userRegistrationService;

    // No changes to your existing register/unregister methods needed. They remain as they are.
    @PostMapping("/register/{courseId}")
    public ResponseEntity<?> registerForCourse(@PathVariable Long courseId, Authentication authentication) {
        String username = authentication.getName();

        if (registrationRepository.findByUser_UsernameAndCourse_Id(username, courseId).isPresent()) {
            return ResponseEntity.badRequest().body("Already registered for this course.");
        }

        User user = userRepository.findByUsername(username).orElse(null); // Updated to use findByUsername
        Course course = courseRepository.findById(courseId).orElse(null);

        if (user == null || course == null) {
            return ResponseEntity.notFound().build();
        }

        UserRegistration registration = new UserRegistration();
        registration.setUser(user);
        registration.setCourse(course);
        registrationRepository.save(registration);

        return ResponseEntity.ok("Successfully registered for the course.");
    }

    @Transactional
    @DeleteMapping("/unregister/{courseId}")
    public ResponseEntity<?> unregisterFromCourse(@PathVariable Long courseId, Authentication authentication) {
        String username = authentication.getName();

        if (registrationRepository.findByUser_UsernameAndCourse_Id(username, courseId).isEmpty()) {
            return ResponseEntity.badRequest().body("Not registered for this course.");
        }

        registrationRepository.deleteByUser_UsernameAndCourse_Id(username, courseId);
        return ResponseEntity.ok("Successfully unregistered from the course.");
    }

    // --- ADD THIS ENTIRE NEW ENDPOINT ---
    @GetMapping("/my-courses")
    public ResponseEntity<List<Course>> getMyRegisteredCourses(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        List<Course> courses = userRegistrationService.findCoursesByUsername(username);

        return ResponseEntity.ok(courses);
    }
}