package com.root.dto;

import java.util.List;

public class CourseDetailResponse {
    private Long courseId;
    private String title;
    private String description;
    private String category;
    private String difficultyLevel;
    private Integer estimatedHours;
    private int totalLessons;
    private List<LessonSummaryDTO> lessons;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public int getTotalLessons() {
        return totalLessons;
    }

    public void setTotalLessons(int totalLessons) {
        this.totalLessons = totalLessons;
    }

    public List<LessonSummaryDTO> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonSummaryDTO> lessons) {
        this.lessons = lessons;
    }
}
