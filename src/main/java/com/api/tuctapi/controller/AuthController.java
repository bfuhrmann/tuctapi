package com.api.tuctapi.controller;

import com.api.tuctapi.dto.LoginRequest;
import com.api.tuctapi.dto.LoginResponse;
import com.api.tuctapi.response.ApiResponse;
import com.api.tuctapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response =
                authService.login(request);

        ApiResponse<LoginResponse> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.OK.value(),
                        "Login realizado com sucesso",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }
}
