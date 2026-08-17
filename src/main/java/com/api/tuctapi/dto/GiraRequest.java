package com.api.tuctapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;



public class GiraRequest {

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
    private String title;

    private String description;

    @Size(max = 500, message = "A imagem deve ter no máximo 500 caracteres")
    private String imageGira;

    @NotNull(message = "A data da gira é obrigatória")
    private LocalDateTime dateGira;

    @NotNull(message = "O campo isPublic é obrigatório")
    private Boolean isPublic;

    @NotNull(message = "O campo confirmGira é obrigatório")
    private Boolean confirmGira;

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
}

