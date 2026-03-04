package com.root.controllers;

import com.root.beans.Course;
import com.root.beans.User;
import com.root.beans.UserRegistration;
import com.root.dto.MyCourseResponse;
import com.root.repositories.CourseRepository;
import com.root.repositories.UserRepository;
import com.root.repositories.UserRegistrationRepository;
import com.root.services.CourseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    @Autowired private UserRegistrationRepository registrationRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CourseService courseService;

    /**
     * POST /api/registrations/register/{courseId} — Register for a course
     */
    @PostMapping("/register/{courseId}")
    public ResponseEntity<?> registerForCourse(@PathVariable Long courseId, Authentication authentication) {
        String username = authentication.getName();

        if (registrationRepository.findByUser_UsernameAndCourse_Id(username, courseId).isPresent()) {
            return ResponseEntity.badRequest().body("Already registered for this course.");
        }

        User user = userRepository.findByUsername(username).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);

        if (user == null || course == null) {
            return ResponseEntity.notFound().build();
        }

        UserRegistration registration = new UserRegistration();
        registration.setUser(user);
        registration.setCourse(course);
        registration.setCompletionPercentage(0);
        registration.setCompleted(false);
        registrationRepository.save(registration);

        return ResponseEntity.ok("Successfully registered for the course.");
    }

    /**
     * DELETE /api/registrations/unregister/{courseId} — Unregister + delete all progress
     * Cascade delete handles LessonProgress cleanup automatically.
     */
    @Transactional
    @DeleteMapping("/unregister/{courseId}")
    public ResponseEntity<?> unregisterFromCourse(@PathVariable Long courseId, Authentication authentication) {
        String username = authentication.getName();

        if (registrationRepository.findByUser_UsernameAndCourse_Id(username, courseId).isEmpty()) {
            return ResponseEntity.badRequest().body("Not registered for this course.");
        }

        registrationRepository.deleteByUser_UsernameAndCourse_Id(username, courseId);
        return ResponseEntity.ok("Successfully unregistered from the course. All progress has been cleared.");
    }

    /**
     * GET /api/registrations/my-courses — List enrolled courses with progress
     */
    @GetMapping("/my-courses")
    public ResponseEntity<List<MyCourseResponse>> getMyRegisteredCourses(Authentication authentication) {
        String username = authentication.getName();
        List<MyCourseResponse> response = courseService.getMyCourses(username);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/registrations/{courseId}/lessons/{lessonId}/complete — Mark lesson complete
     */
    @PostMapping("/{courseId}/lessons/{lessonId}/complete")
    public ResponseEntity<?> markLessonComplete(
            @PathVariable Long courseId,
            @PathVariable Long lessonId,
            Authentication authentication) {
        String username = authentication.getName();
        try {
            Map<String, Object> result = courseService.markLessonComplete(username, courseId, lessonId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * DELETE /api/registrations/{courseId}/lessons/{lessonId}/complete — Unmark lesson
     */
    @DeleteMapping("/{courseId}/lessons/{lessonId}/complete")
    public ResponseEntity<?> unmarkLessonComplete(
            @PathVariable Long courseId,
            @PathVariable Long lessonId,
            Authentication authentication) {
        String username = authentication.getName();
        try {
            Map<String, Object> result = courseService.unmarkLessonComplete(username, courseId, lessonId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * GET /api/registrations/{courseId}/progress — Detailed lesson progress
     */
    @GetMapping("/{courseId}/progress")
    public ResponseEntity<?> getLessonProgress(
            @PathVariable Long courseId,
            Authentication authentication) {
        String username = authentication.getName();
        try {
            Map<String, Object> result = courseService.getLessonProgress(username, courseId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}