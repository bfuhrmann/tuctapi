package com.api.tuctapi.dto;

import com.api.tuctapi.model.TipoAcessoVotacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class PerguntaRequest {

    @NotBlank(message = "A pergunta é obrigatória")
    @Size(max = 500, message = "A pergunta deve ter no máximo 500 caracteres")
    private String pergunta;

    @NotNull(message = "O tipo de acesso é obrigatório")
    private TipoAcessoVotacao tipoAcesso;

    private Boolean ativo = true;

    @JsonProperty("data_inicio")
    private LocalDateTime dataInicio;

    @JsonProperty("data_fim")
    private LocalDateTime dataFim;

    public PerguntaRequest() {
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public TipoAcessoVotacao getTipoAcesso() {
        return tipoAcesso;
    }

    public void setTipoAcesso(TipoAcessoVotacao tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }
}