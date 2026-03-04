package com.root.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dsa_problem_progress", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "dsa_problem_id"})
})
public class DsaProblemProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "dsa_problem_id")
    private DsaProblem dsaProblem;

    private boolean solved;

    private LocalDateTime solvedAt;

    @PrePersist
    protected void onCreate() {
        solvedAt = LocalDateTime.now();
        solved = true;
    }

    public DsaProblemProgress() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DsaProblem getDsaProblem() {
        return dsaProblem;
    }

    public void setDsaProblem(DsaProblem dsaProblem) {
        this.dsaProblem = dsaProblem;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public LocalDateTime getSolvedAt() {
        return solvedAt;
    }

    public void setSolvedAt(LocalDateTime solvedAt) {
        this.solvedAt = solvedAt;
    }
}
