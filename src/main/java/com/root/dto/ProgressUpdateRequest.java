package com.root.dto;

public class ProgressUpdateRequest {
    private Integer completionPercentage;
    private String lastAccessedLesson;

    public Integer getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(Integer completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public String getLastAccessedLesson() {
        return lastAccessedLesson;
    }

    public void setLastAccessedLesson(String lastAccessedLesson) {
        this.lastAccessedLesson = lastAccessedLesson;
    }
}
