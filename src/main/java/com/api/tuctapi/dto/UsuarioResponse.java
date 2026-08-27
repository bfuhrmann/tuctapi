package com.api.tuctapi.dto;

import com.api.tuctapi.model.NivelUsuario;
import com.api.tuctapi.model.Usuario;

public class UsuarioResponse {
    private Integer id;
    private String nome;
    private String email;
    private NivelUsuario nivel;
    private Boolean ativo;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.nivel = usuario.getNivel();
        this.ativo = usuario.getAtivo();
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public NivelUsuario getNivel() {
        return nivel;
    }

    public Boolean getAtivo() {
        return ativo;
    }
}
