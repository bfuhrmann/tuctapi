package com.api.tuctapi.dto;

public class ResultadoOpcaoResponse {

    private Integer id;
    private String descricao;
    private long votos;
    private double percentual;

    public ResultadoOpcaoResponse(
            Integer id,
            String descricao,
            long votos,
            double percentual
    ) {
        this.id = id;
        this.descricao = descricao;
        this.votos = votos;
        this.percentual = percentual;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public long getVotos() {
        return votos;
    }

    public double getPercentual() {
        return percentual;
    }
}