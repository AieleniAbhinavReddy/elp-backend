package com.root.dto;

public class LessonSummaryDTO {
    private Long id;
    private String title;
    private int orderIndex;
    private boolean completed;

    public LessonSummaryDTO() {}

    public LessonSummaryDTO(Long id, String title, int orderIndex, boolean completed) {
        this.id = id;
        this.title = title;
        this.orderIndex = orderIndex;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
