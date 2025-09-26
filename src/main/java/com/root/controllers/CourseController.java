package com.root.controllers;

import com.root.beans.Course;
import com.root.repositories.CourseRepository;
import com.root.repositories.UserRegistrationRepository;
import com.root.services.YouTubeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
//@CrossOrigin(origins = "http://localhost:3000") // Allow React client
public class CourseController {

    // A logger for printing detailed errors to the console
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired private CourseRepository courseRepository;
    @Autowired private UserRegistrationRepository registrationRepository;
    @Autowired private YouTubeService youTubeService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseWithVideos(@PathVariable Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) return ResponseEntity.notFound().build();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isRegistered = false;

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            isRegistered = registrationRepository.findByUser_UsernameAndCourse_Id(username, courseId).isPresent();
        }

        int videosToFetch = isRegistered ? 50 : 3;
        String videosJson;

        // --- START OF THE NEW ERROR HANDLING BLOCK ---
        try {
            // First, check if the playlist ID is valid before making the API call
            if (course.getYoutubePlaylistId() == null || course.getYoutubePlaylistId().isBlank()) {
                throw new Exception("YouTube Playlist ID is missing for course ID: " + courseId);
            }
            // This is the line that might crash, so it's inside the 'try' block
            videosJson = youTubeService.getPlaylistVideos(course.getYoutubePlaylistId(), videosToFetch);

        } catch (Exception e) {
            // If anything goes wrong in the 'try' block, this 'catch' block will run
            // 1. Log the detailed error to your Spring Boot console for debugging
            logger.error("Could not fetch YouTube videos for playlist ID: {}", course.getYoutubePlaylistId(), e);

            // 2. Set a safe, empty default value so the frontend doesn't break
            videosJson = "{\"items\":[]}";
        }
        // --- END OF THE NEW ERROR HANDLING BLOCK ---

        Map<String, Object> response = new HashMap<>();
        response.put("courseDetails", course);
        response.put("videos", videosJson);
        response.put("isRegistered", isRegistered);

        return ResponseEntity.ok(response);
    }
}