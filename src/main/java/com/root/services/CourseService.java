package com.root.services;

import com.root.beans.*;
import com.root.dto.*;
import com.root.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LessonProgressRepository lessonProgressRepository;

    @Autowired
    private UserRegistrationRepository registrationRepository;

    // ── Course listing ──────────────────────────────────────────

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }

    // ── Course detail with lessons ──────────────────────────────

    public CourseDetailResponse getCourseDetail(Long courseId, String username) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<Lesson> lessons = lessonRepository.findByCourseIdOrderByOrderIndexAsc(courseId);

        // Check which lessons are completed (if user is registered)
        Set<Long> completedLessonIds = new HashSet<>();
        if (username != null) {
            Optional<UserRegistration> regOpt =
                    registrationRepository.findByUser_UsernameAndCourse_Id(username, courseId);
            if (regOpt.isPresent()) {
                List<LessonProgress> progressList =
                        lessonProgressRepository.findByRegistrationId(regOpt.get().getId());
                completedLessonIds = progressList.stream()
                        .map(lp -> lp.getLesson().getId())
                        .collect(Collectors.toSet());
            }
        }

        Set<Long> finalCompletedIds = completedLessonIds;
        List<LessonSummaryDTO> lessonSummaries = lessons.stream()
                .map(l -> new LessonSummaryDTO(
                        l.getId(), l.getTitle(), l.getOrderIndex(),
                        finalCompletedIds.contains(l.getId())))
                .collect(Collectors.toList());

        CourseDetailResponse response = new CourseDetailResponse();
        response.setCourseId(course.getId());
        response.setTitle(course.getTitle());
        response.setDescription(course.getDescription());
        response.setCategory(course.getCategory());
        response.setDifficultyLevel(course.getDifficultyLevel());
        response.setEstimatedHours(course.getEstimatedHours());
        response.setTotalLessons(lessons.size());
        response.setLessons(lessonSummaries);
        return response;
    }

    // ── Single lesson content ───────────────────────────────────

    public LessonDetailResponse getLessonDetail(Long courseId, Long lessonId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Lesson lesson = lessonRepository.findByIdAndCourseId(lessonId, courseId)
                .orElseThrow(() -> new RuntimeException("Lesson not found in this course"));

        List<Lesson> allLessons = lessonRepository.findByCourseIdOrderByOrderIndexAsc(courseId);

        // Find previous and next lessons for navigation
        Long previousId = null;
        Long nextId = null;
        for (int i = 0; i < allLessons.size(); i++) {
            if (allLessons.get(i).getId().equals(lessonId)) {
                if (i > 0) previousId = allLessons.get(i - 1).getId();
                if (i < allLessons.size() - 1) nextId = allLessons.get(i + 1).getId();
                break;
            }
        }

        LessonDetailResponse response = new LessonDetailResponse();
        response.setLessonId(lesson.getId());
        response.setCourseId(courseId);
        response.setCourseTitle(course.getTitle());
        response.setTitle(lesson.getTitle());
        response.setContent(lesson.getContent());
        response.setOrderIndex(lesson.getOrderIndex());
        response.setTotalLessons(allLessons.size());
        response.setPreviousLessonId(previousId);
        response.setNextLessonId(nextId);
        return response;
    }

    // ── Lesson completion ───────────────────────────────────────

    @Transactional
    public Map<String, Object> markLessonComplete(String username, Long courseId, Long lessonId) {
        UserRegistration registration = registrationRepository
                .findByUser_UsernameAndCourse_Id(username, courseId)
                .orElseThrow(() -> new RuntimeException("You are not registered for this course"));

        Lesson lesson = lessonRepository.findByIdAndCourseId(lessonId, courseId)
                .orElseThrow(() -> new RuntimeException("Lesson not found in this course"));

        // Check if already completed
        Optional<LessonProgress> existing =
                lessonProgressRepository.findByRegistrationIdAndLessonId(registration.getId(), lessonId);
        if (existing.isPresent()) {
            return buildProgressResponse(registration, courseId, "Lesson already completed");
        }

        // Mark complete
        LessonProgress progress = new LessonProgress();
        progress.setRegistration(registration);
        progress.setLesson(lesson);
        lessonProgressRepository.save(progress);

        // Recalculate completion percentage
        updateCompletionPercentage(registration, courseId);

        return buildProgressResponse(registration, courseId, "Lesson marked as complete");
    }

    @Transactional
    public Map<String, Object> unmarkLessonComplete(String username, Long courseId, Long lessonId) {
        UserRegistration registration = registrationRepository
                .findByUser_UsernameAndCourse_Id(username, courseId)
                .orElseThrow(() -> new RuntimeException("You are not registered for this course"));

        Optional<LessonProgress> existing =
                lessonProgressRepository.findByRegistrationIdAndLessonId(registration.getId(), lessonId);
        if (existing.isEmpty()) {
            return buildProgressResponse(registration, courseId, "Lesson was not marked as complete");
        }

        lessonProgressRepository.delete(existing.get());

        // Recalculate completion percentage
        updateCompletionPercentage(registration, courseId);

        return buildProgressResponse(registration, courseId, "Lesson unmarked as complete");
    }

    // ── Progress details ────────────────────────────────────────

    public Map<String, Object> getLessonProgress(String username, Long courseId) {
        UserRegistration registration = registrationRepository
                .findByUser_UsernameAndCourse_Id(username, courseId)
                .orElseThrow(() -> new RuntimeException("You are not registered for this course"));

        return buildProgressResponse(registration, courseId, null);
    }

    // ── My courses with progress ────────────────────────────────

    public List<MyCourseResponse> getMyCourses(String username) {
        List<UserRegistration> registrations =
                registrationRepository.findByUser_Username(username);

        return registrations.stream().map(reg -> {
            Course course = reg.getCourse();
            int totalLessons = lessonRepository.countByCourseId(course.getId());
            int completedLessons = lessonProgressRepository.countByRegistrationId(reg.getId());

            MyCourseResponse resp = new MyCourseResponse();
            resp.setCourseId(course.getId());
            resp.setTitle(course.getTitle());
            resp.setDescription(course.getDescription());
            resp.setCategory(course.getCategory());
            resp.setDifficultyLevel(course.getDifficultyLevel());
            resp.setEstimatedHours(course.getEstimatedHours());
            resp.setTotalLessons(totalLessons);
            resp.setCompletedLessons(completedLessons);
            resp.setCompletionPercentage(reg.getCompletionPercentage());
            resp.setLastAccessedLesson(reg.getLastAccessedLesson());
            resp.setCompleted(reg.isCompleted());
            return resp;
        }).collect(Collectors.toList());
    }

    // ── Helpers ─────────────────────────────────────────────────

    private void updateCompletionPercentage(UserRegistration registration, Long courseId) {
        int totalLessons = lessonRepository.countByCourseId(courseId);
        int completedLessons = lessonProgressRepository.countByRegistrationId(registration.getId());

        int percentage = totalLessons > 0
                ? (int) Math.round((completedLessons * 100.0) / totalLessons)
                : 0;

        registration.setCompletionPercentage(percentage);
        registration.setCompleted(percentage >= 100);
        registrationRepository.save(registration);
    }

    private Map<String, Object> buildProgressResponse(
            UserRegistration registration, Long courseId, String message) {
        int totalLessons = lessonRepository.countByCourseId(courseId);
        int completedLessons = lessonProgressRepository.countByRegistrationId(registration.getId());

        List<LessonProgress> progressList =
                lessonProgressRepository.findByRegistrationId(registration.getId());
        List<Long> completedLessonIds = progressList.stream()
                .map(lp -> lp.getLesson().getId())
                .collect(Collectors.toList());

        Map<String, Object> response = new LinkedHashMap<>();
        if (message != null) response.put("message", message);
        response.put("courseId", courseId);
        response.put("totalLessons", totalLessons);
        response.put("completedLessons", completedLessons);
        response.put("completionPercentage", registration.getCompletionPercentage());
        response.put("completed", registration.isCompleted());
        response.put("completedLessonIds", completedLessonIds);
        return response;
    }
}
