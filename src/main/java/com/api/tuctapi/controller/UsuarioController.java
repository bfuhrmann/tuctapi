package com.api.tuctapi.controller;

import com.api.tuctapi.dto.UsuarioRequest;
import com.api.tuctapi.dto.UsuarioResponse;
import com.api.tuctapi.response.ApiResponse;
import com.api.tuctapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<UsuarioResponse>> criar(
            @Valid @RequestBody UsuarioRequest request) {

        UsuarioResponse response =
                usuarioService.criar(request);

        ApiResponse<UsuarioResponse> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.CREATED.value(),
                        "Usuário criado com sucesso",
                        response
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);
    }
}
