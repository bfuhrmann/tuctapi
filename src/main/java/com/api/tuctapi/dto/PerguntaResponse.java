package com.api.tuctapi.dto;

import com.api.tuctapi.model.TipoAcessoVotacao;

import java.time.LocalDateTime;
import java.util.List;

public class PerguntaResponse {

    private Integer id;
    private String pergunta;
    private TipoAcessoVotacao tipoAcesso;
    private Boolean ativo;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private List<OpcaoResponse> opcoes;

    public PerguntaResponse() {
    }

    public PerguntaResponse(
            Integer id,
            String pergunta,
            TipoAcessoVotacao tipoAcesso,
            Boolean ativo,
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            List<OpcaoResponse> opcoes
    ) {
        this.id = id;
        this.pergunta = pergunta;
        this.tipoAcesso = tipoAcesso;
        this.ativo = ativo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.opcoes = opcoes;
    }

    public Integer getId() {
        return id;
    }

    public String getPergunta() {
        return pergunta;
    }

    public TipoAcessoVotacao getTipoAcesso() {
        return tipoAcesso;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public List<OpcaoResponse> getOpcoes() {
        return opcoes;
    }
}