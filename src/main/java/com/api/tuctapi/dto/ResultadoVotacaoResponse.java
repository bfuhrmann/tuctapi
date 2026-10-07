package com.api.tuctapi.dto;

import java.util.List;

public class ResultadoVotacaoResponse {

    private Integer perguntaId;
    private String pergunta;
    private long totalVotos;
    private List<ResultadoOpcaoResponse> opcoes;

    public ResultadoVotacaoResponse(
            Integer perguntaId,
            String pergunta,
            long totalVotos,
            List<ResultadoOpcaoResponse> opcoes
    ) {
        this.perguntaId = perguntaId;
        this.pergunta = pergunta;
        this.totalVotos = totalVotos;
        this.opcoes = opcoes;
    }

    public Integer getPerguntaId() {
        return perguntaId;
    }

    public String getPergunta() {
        return pergunta;
    }

    public long getTotalVotos() {
        return totalVotos;
    }

    public List<ResultadoOpcaoResponse> getOpcoes() {
        return opcoes;
    }
}