package com.api.tuctapi.controller;

import com.api.tuctapi.dto.GiraRequest;
import com.api.tuctapi.dto.GiraResponse;
import com.api.tuctapi.response.ApiResponse;
import com.api.tuctapi.service.GiraService;
import com.api.tuctapi.response.PaginacaoResponse;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/v1/giras")
public class GiraController {

    private final GiraService giraService;

    public GiraController(GiraService giraService) {
        this.giraService = giraService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GiraResponse>> criar(
            @Valid @RequestBody GiraRequest request) {

        GiraResponse response = giraService.criar(request);

        ApiResponse<GiraResponse> apiResponse = new ApiResponse<>(
                true,
                HttpStatus.CREATED.value(),
                "Gira criada com sucesso",
                response
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginacaoResponse<GiraResponse>>> listar(
            Pageable pageable) {

        PaginacaoResponse<GiraResponse> response =
                giraService.listar(pageable);

        ApiResponse<PaginacaoResponse<GiraResponse>> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.OK.value(),
                        "Giras encontradas com sucesso",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GiraResponse>> buscarPorId(
            @PathVariable Integer id) {

        GiraResponse response = giraService.buscarPorId(id);

        ApiResponse<GiraResponse> apiResponse = new ApiResponse<>(
                true,
                HttpStatus.OK.value(),
                "Gira encontrada com sucesso",
                response
        );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/mes-atual")
    public ResponseEntity<ApiResponse<List<GiraResponse>>> listarMesAtual() {

        List<GiraResponse> response = giraService.listarMesAtual();

        ApiResponse<List<GiraResponse>> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.OK.value(),
                        "Giras do mês atual encontradas com sucesso",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GiraResponse>> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody GiraRequest request) {

        GiraResponse response = giraService.atualizar(id, request);

        ApiResponse<GiraResponse> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.OK.value(),
                        "Gira atualizada com sucesso",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(
            @PathVariable Integer id) {

        giraService.deletar(id);

        ApiResponse<Void> response = new ApiResponse<>(
                true,
                HttpStatus.OK.value(),
                "Gira excluída com sucesso",
                null
        );

        return ResponseEntity.ok(response);
    }
}