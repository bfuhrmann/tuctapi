package com.api.tuctapi.dto;

public class OpcaoResponse {

    private Integer id;
    private String descricao;

    public OpcaoResponse() {
    }

    public OpcaoResponse(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}