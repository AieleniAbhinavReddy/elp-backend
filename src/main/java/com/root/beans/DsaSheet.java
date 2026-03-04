package com.root.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dsa_sheets")
public class DsaSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private String youtubePlaylistId;

    private String youtubePlaylistUrl;

    private int totalProblems;

    @OneToMany(mappedBy = "dsaSheet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DsaProblem> problems = new ArrayList<>();

    public DsaSheet() {}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutubePlaylistId() {
        return youtubePlaylistId;
    }

    public void setYoutubePlaylistId(String youtubePlaylistId) {
        this.youtubePlaylistId = youtubePlaylistId;
    }

    public String getYoutubePlaylistUrl() {
        return youtubePlaylistUrl;
    }

    public void setYoutubePlaylistUrl(String youtubePlaylistUrl) {
        this.youtubePlaylistUrl = youtubePlaylistUrl;
    }

    public int getTotalProblems() {
        return totalProblems;
    }

    public void setTotalProblems(int totalProblems) {
        this.totalProblems = totalProblems;
    }

    public List<DsaProblem> getProblems() {
        return problems;
    }

    public void setProblems(List<DsaProblem> problems) {
        this.problems = problems;
    }
}
