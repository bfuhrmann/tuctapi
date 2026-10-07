package com.api.tuctapi.controller;

import com.api.tuctapi.dto.OpcaoRequest;
import com.api.tuctapi.dto.OpcaoResponse;
import com.api.tuctapi.dto.PerguntaRequest;
import com.api.tuctapi.dto.PerguntaResponse;
import com.api.tuctapi.response.ApiResponse;
import com.api.tuctapi.service.VotacaoService;
import com.api.tuctapi.dto.VotoRequest;
import com.api.tuctapi.dto.ResultadoVotacaoResponse;
import jakarta.validation.Valid;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api/v1/votacoes")
public class VotacaoController {

    private final VotacaoService votacaoService;

    public VotacaoController(VotacaoService votacaoService) {
        this.votacaoService = votacaoService;
    }

    @PostMapping("/perguntas")
    public ResponseEntity<ApiResponse<PerguntaResponse>> criarPergunta(
            @Valid @RequestBody PerguntaRequest request,
            Authentication authentication
    ) {

        PerguntaResponse response =
                votacaoService.criarPergunta(
                        request,
                        authentication.getName()
                        );

        ApiResponse<PerguntaResponse> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.CREATED.value(),
                        "Pergunta criada com sucesso",
                        response
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @PostMapping("/perguntas/{perguntaId}/opcoes")
    public ResponseEntity<ApiResponse<OpcaoResponse>> adicionarOpcao(
            @PathVariable Integer perguntaId,
            @Valid @RequestBody OpcaoRequest request,
            Authentication authentication
    ) {

        OpcaoResponse response =
                votacaoService.adicionarOpcao(
                        perguntaId,
                        request,
                        authentication.getName()
                );

        ApiResponse<OpcaoResponse> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.CREATED.value(),
                        "Opção criada com sucesso",
                        response
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @GetMapping("/perguntas/{perguntaId}")
    public ResponseEntity<ApiResponse<PerguntaResponse>> buscarPergunta(
            @PathVariable Integer perguntaId
    ) {

        PerguntaResponse response =
                votacaoService.buscarPergunta(perguntaId);

        ApiResponse<PerguntaResponse> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.OK.value(),
                        "Pergunta encontrada",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/perguntas/{perguntaId}/votos")
    public ResponseEntity<ApiResponse<Void>> votar(
            @PathVariable Integer perguntaId,
            @Valid @RequestBody VotoRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) {

        if (authentication != null && authentication.isAuthenticated()) {

            votacaoService.votarComoRegistrado(
                    perguntaId,
                    request.getOpcaoId(),
                    authentication.getName()
            );

        } else {

            String tokenAnonimo = obterTokenAnonimo(httpRequest);

            String tokenGerado = votacaoService.votarComoLivre(
                    perguntaId,
                    request.getOpcaoId(),
                    tokenAnonimo
            );

            if (tokenAnonimo == null || tokenAnonimo.isBlank()) {
                adicionarCookieToken(
                        httpResponse,
                        tokenGerado
                );
            }
        }

        ApiResponse<Void> response =
                new ApiResponse<>(
                        true,
                        HttpStatus.CREATED.value(),
                        "Voto registrado com sucesso",
                        null
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    private String obterTokenAnonimo(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {

            if ("VOTACAO_TOKEN".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    private void adicionarCookieToken(
            HttpServletResponse response,
            String token
    ) {

        Cookie cookie = new Cookie(
                "VOTACAO_TOKEN",
                token
        );

        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 365);

        response.addCookie(cookie);
    }

    @GetMapping("/perguntas/{perguntaId}/resultados")
    public ResponseEntity<ApiResponse<ResultadoVotacaoResponse>> obterResultados(
            @PathVariable Integer perguntaId
    ) {

        ResultadoVotacaoResponse response =
                votacaoService.obterResultados(perguntaId);

        ApiResponse<ResultadoVotacaoResponse> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.OK.value(),
                        "Resultado da votação",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/perguntas/{perguntaId}")
    public ResponseEntity<ApiResponse<PerguntaResponse>> atualizarPergunta(
            @PathVariable Integer perguntaId,
            @Valid @RequestBody PerguntaRequest request,
            Authentication authentication
    ) {
        PerguntaResponse response =
                votacaoService.atualizarPergunta(
                        perguntaId,
                        request,
                        authentication.getName()
                        );

        ApiResponse<PerguntaResponse> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.OK.value(),
                        "Pergunta atualizada com sucesso",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/perguntas/{perguntaId}/opcoes/{opcaoId}")
    public ResponseEntity<ApiResponse<OpcaoResponse>> atualizarOpcao(
            @PathVariable Integer perguntaId,
            @PathVariable Integer opcaoId,
            @Valid @RequestBody OpcaoRequest request,
            Authentication authentication
    ) {
        OpcaoResponse response =
                votacaoService.atualizarOpcao(
                        perguntaId,
                        opcaoId,
                        request,
                        authentication.getName()
                );

        ApiResponse<OpcaoResponse> apiResponse =
                new ApiResponse<>(
                        true,
                        HttpStatus.OK.value(),
                        "Opção atualizada com sucesso",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/perguntas/{perguntaId}")
    public ResponseEntity<ApiResponse<Void>> excluirPergunta(
            @PathVariable Integer perguntaId,
            Authentication authentication
    ) {
        votacaoService.excluirPergunta(
                perguntaId,
                authentication.getName()
        );

        ApiResponse<Void> response =
                new ApiResponse<>(
                        true,
                        HttpStatus.OK.value(),
                        "Pergunta excluída com sucesso",
                        null
                );

        return ResponseEntity.ok(response);
    }

}