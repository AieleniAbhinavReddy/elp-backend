package com.root.dto;

import java.util.List;

public class DsaSheetDetailDTO {
    private Long id;
    private String title;
    private String description;
    private String youtubePlaylistUrl;
    private int totalProblems;
    private int solvedCount;
    private int progressPercentage;
    private List<DsaProblemDTO> problems;

    public DsaSheetDetailDTO() {}

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

    public List<DsaProblemDTO> getProblems() { return problems; }
    public void setProblems(List<DsaProblemDTO> problems) { this.problems = problems; }
}
