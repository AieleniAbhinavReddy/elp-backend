package com.root.dto;

public class DsaProblemDTO {
    private Long id;
    private String title;
    private String youtubeVideoId;
    private String youtubeVideoUrl;
    private String thumbnailUrl;
    private String practiceUrl;
    private String platform;
    private int orderIndex;
    private boolean solved;

    public DsaProblemDTO() {}

    public DsaProblemDTO(Long id, String title, String youtubeVideoId, String youtubeVideoUrl,
                         String thumbnailUrl, String practiceUrl, String platform,
                         int orderIndex, boolean solved) {
        this.id = id;
        this.title = title;
        this.youtubeVideoId = youtubeVideoId;
        this.youtubeVideoUrl = youtubeVideoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.practiceUrl = practiceUrl;
        this.platform = platform;
        this.orderIndex = orderIndex;
        this.solved = solved;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getYoutubeVideoId() { return youtubeVideoId; }
    public void setYoutubeVideoId(String youtubeVideoId) { this.youtubeVideoId = youtubeVideoId; }

    public String getYoutubeVideoUrl() { return youtubeVideoUrl; }
    public void setYoutubeVideoUrl(String youtubeVideoUrl) { this.youtubeVideoUrl = youtubeVideoUrl; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public String getPracticeUrl() { return practiceUrl; }
    public void setPracticeUrl(String practiceUrl) { this.practiceUrl = practiceUrl; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public int getOrderIndex() { return orderIndex; }
    public void setOrderIndex(int orderIndex) { this.orderIndex = orderIndex; }

    public boolean isSolved() { return solved; }
    public void setSolved(boolean solved) { this.solved = solved; }
}
