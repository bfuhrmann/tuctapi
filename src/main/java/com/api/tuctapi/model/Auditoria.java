package com.api.tuctapi.model;

import com.api.tuctapi.enums.AcaoAuditoria;
import com.api.tuctapi.enums.EntidadeAuditoria;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditorias")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "usuario_email", nullable = false, length = 255)
    private String usuarioEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AcaoAuditoria acao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EntidadeAuditoria entidade;

    @Column(name = "entidade_id")
    private Integer entidadeId;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @PrePersist
    public void prePersist() {
        if (dataHora == null) {
            dataHora = LocalDateTime.now();
        }
    }

    public Integer getId() {
        return id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public AcaoAuditoria getAcao() {
        return acao;
    }

    public void setAcao(AcaoAuditoria acao) {
        this.acao = acao;
    }

    public EntidadeAuditoria getEntidade() {
        return entidade;
    }

    public void setEntidade(EntidadeAuditoria entidade) {
        this.entidade = entidade;
    }

    public Integer getEntidadeId() {
        return entidadeId;
    }

    public void setEntidadeId(Integer entidadeId) {
        this.entidadeId = entidadeId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}