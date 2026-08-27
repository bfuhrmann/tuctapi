package com.api.tuctapi.service;

import com.api.tuctapi.dto.LoginRequest;
import com.api.tuctapi.dto.LoginResponse;
import com.api.tuctapi.model.Usuario;
import com.api.tuctapi.repository.UsuarioRepository;
import com.api.tuctapi.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Email ou senha inválidos"
                        )
                );

        if (!usuario.getAtivo()) {
            throw new IllegalArgumentException(
                    "Usuário está inativo"
            );
        }

        boolean senhaValida = passwordEncoder.matches(
                request.getSenha(),
                usuario.getSenha()
        );

        if (!senhaValida) {
            throw new IllegalArgumentException(
                    "Email ou senha inválidos"
            );
        }

        String token = jwtService.gerarToken(usuario);

        return new LoginResponse(
                token,
                "Bearer",
                jwtService.getExpirationTime()
        );
    }
}
