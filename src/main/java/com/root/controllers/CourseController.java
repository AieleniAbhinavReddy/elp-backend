package com.root.controllers;

import com.root.beans.Course;
import com.root.dto.CourseDetailResponse;
import com.root.dto.LessonDetailResponse;
import com.root.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * GET /api/courses — List all available courses
     */
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    /**
     * GET /api/courses/{courseId} — Course detail with lesson list (titles only).
     * If user is authenticated, lesson completion status is included.
     */
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDetailResponse> getCourseDetail(@PathVariable Long courseId) {
        String username = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            username = auth.getName();
        }

        try {
            CourseDetailResponse response = courseService.getCourseDetail(courseId, username);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/courses/{courseId}/lessons/{lessonId} — Full lesson content
     */
    @GetMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<LessonDetailResponse> getLessonContent(
            @PathVariable Long courseId,
            @PathVariable Long lessonId) {
        try {
            LessonDetailResponse response = courseService.getLessonDetail(courseId, lessonId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}