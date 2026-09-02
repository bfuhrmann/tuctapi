package com.api.tuctapi.controller;

import com.api.tuctapi.dto.GiraRequest;
import com.api.tuctapi.dto.GiraResponse;
import com.api.tuctapi.response.ApiResponse;
import com.api.tuctapi.service.GiraService;
import com.api.tuctapi.response.PaginacaoResponse;
import com.api.tuctapi.validation.NotEmptyList;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;


@Validated
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


    @PostMapping("/lote")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<List<GiraResponse>>> criarEmLote(
            @Valid
            @NotEmptyList
            @RequestBody List<@Valid GiraRequest> requests) {

        List<GiraResponse> response =
                giraService.criarEmLote(requests);

        ApiResponse<List<GiraResponse>> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.CREATED.value(),
                        "Giras criadas com sucesso",
                        response
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<PaginacaoResponse<GiraResponse>>> listar(
            @ParameterObject
            @PageableDefault(size = 10)
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

    @GetMapping("/publicas")
    public ResponseEntity<ApiResponse<List<GiraResponse>>> listarGirasPublicas() {

        List<GiraResponse> response =
                giraService.listarGirasPublicas();

        ApiResponse<List<GiraResponse>> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.OK.value(),
                        "Giras públicas encontradas",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/publicas/mes-atual")
    public ResponseEntity<ApiResponse<List<GiraResponse>>> listarGirasPublicasMesAtual() {

        List<GiraResponse> response =
                giraService.listarGirasPublicasMesAtual();

        ApiResponse<List<GiraResponse>> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.OK.value(),
                        "Giras públicas do mês atual encontradas com sucesso",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/publicas/mes/{mes}")
    public ResponseEntity<ApiResponse<List<GiraResponse>>> listarGirasPublicasPorMes(
            @PathVariable Integer mes) {

        List<GiraResponse> response =
                giraService.listarGirasPublicasPorMes(mes);

        ApiResponse<List<GiraResponse>> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.OK.value(),
                        "Giras públicas do mês encontradas com sucesso",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
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
    @SecurityRequirement(name = "bearerAuth")
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

    @GetMapping("/mes/{mes}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<List<GiraResponse>>> listarPorMes(
            @PathVariable Integer mes) {

        List<GiraResponse> response =
                giraService.listarPorMes(mes);

        ApiResponse<List<GiraResponse>> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.OK.value(),
                        "Giras do mês encontradas com sucesso",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
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
    @SecurityRequirement(name = "bearerAuth")
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