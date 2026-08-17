package com.api.tuctapi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "giras")
public class Gira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 500)
    private String imageGira;

    @Column(columnDefinition = "DATETIME(3)")
    private LocalDateTime dateGira;

    @Column(nullable = false)
    private Boolean isPublic;

    @Column(nullable = false)
    private Boolean confirmGira;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;

        if (this.isPublic == null) {
            this.isPublic = false;
        }

        if (this.confirmGira == null) {
            this.confirmGira = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getImageGira() {
        return imageGira;
    }

    public void setImageGira(String imageGira) {
        this.imageGira = imageGira;
    }

    public LocalDateTime getDateGira() {
        return dateGira;
    }

    public void setDateGira(LocalDateTime dateGira) {
        this.dateGira = dateGira;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getConfirmGira() {
        return confirmGira;
    }

    public void setConfirmGira(Boolean confirmGira) {
        this.confirmGira = confirmGira;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}

