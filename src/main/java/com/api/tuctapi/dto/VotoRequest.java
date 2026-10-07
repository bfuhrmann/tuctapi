package com.api.tuctapi.dto;

import jakarta.validation.constraints.NotNull;

public class VotoRequest {

    @NotNull(message = "A opção é obrigatória")
    private Integer opcaoId;

    public Integer getOpcaoId() {
        return opcaoId;
    }

    public void setOpcaoId(Integer opcaoId) {
        this.opcaoId = opcaoId;
    }
}