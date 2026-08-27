package com.api.tuctapi.service;

import com.api.tuctapi.dto.UsuarioRequest;
import com.api.tuctapi.dto.UsuarioResponse;
import com.api.tuctapi.model.Usuario;
import com.api.tuctapi.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponse criar(UsuarioRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Já existe um usuário com este email"
            );
        }

        Usuario usuario = new Usuario();

        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());

        // Nunca salvar a senha em texto puro
        usuario.setSenha(
                passwordEncoder.encode(request.getSenha())
        );

        usuario.setNivel(request.getNivel());
        usuario.setAtivo(true);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new UsuarioResponse(usuarioSalvo);
    }
}
