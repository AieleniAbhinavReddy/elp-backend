package com.root.dto;

public class DsaSheetSummaryDTO {
    private Long id;
    private String title;
    private String description;
    private String youtubePlaylistUrl;
    private int totalProblems;
    private int solvedCount;
    private int progressPercentage;

    public DsaSheetSummaryDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getYoutubePlaylistUrl() { return youtubePlaylistUrl; }
    public void setYoutubePlaylistUrl(String youtubePlaylistUrl) { this.youtubePlaylistUrl = youtubePlaylistUrl; }

    public int getTotalProblems() { return totalProblems; }
    public void setTotalProblems(int totalProblems) { this.totalProblems = totalProblems; }

    public int getSolvedCount() { return solvedCount; }
    public void setSolvedCount(int solvedCount) { this.solvedCount = solvedCount; }

    public int getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(int progressPercentage) { this.progressPercentage = progressPercentage; }
}
