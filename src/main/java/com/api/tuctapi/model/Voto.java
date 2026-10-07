package com.api.tuctapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "votos",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_voto_usuario",
                        columnNames = {"pergunta_id", "usuario_id"}
                ),
                @UniqueConstraint(
                        name = "uk_voto_anonimo",
                        columnNames = {"pergunta_id", "token_anonimo"}
                )
        }
)
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pergunta_id", nullable = false)
    private Pergunta pergunta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "opcao_id", nullable = false)
    private Opcao opcao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "token_anonimo", length = 100)
    private String tokenAnonimo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public Pergunta getPergunta() {
        return pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }

    public Opcao getOpcao() {
        return opcao;
    }

    public void setOpcao(Opcao opcao) {
        this.opcao = opcao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTokenAnonimo() {
        return tokenAnonimo;
    }

    public void setTokenAnonimo(String tokenAnonimo) {
        this.tokenAnonimo = tokenAnonimo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}