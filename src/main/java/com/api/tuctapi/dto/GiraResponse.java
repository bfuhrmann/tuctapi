package com.api.tuctapi.dto;

import com.api.tuctapi.model.Gira;
import java.time.LocalDateTime;

public class GiraResponse {

    private Integer id;
    private String title;
    private String description;
    private String imageGira;
    private LocalDateTime dateGira;
    private Boolean isPublic;
    private Boolean confirmGira;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GiraResponse(Gira gira) {
        this.id = gira.getId();
        this.title = gira.getTitle();
        this.description = gira.getDescription();
        this.imageGira = gira.getImageGira();
        this.dateGira = gira.getDateGira();
        this.isPublic = gira.getIsPublic();
        this.confirmGira = gira.getConfirmGira();
        this.createdAt = gira.getCreatedAt();
        this.updatedAt = gira.getUpdatedAt();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageGira() {
        return imageGira;
    }

    public LocalDateTime getDateGira() {
        return dateGira;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public Boolean getConfirmGira() {
        return confirmGira;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}

