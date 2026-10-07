package com.api.tuctapi.service;

import com.api.tuctapi.dto.OpcaoRequest;
import com.api.tuctapi.dto.OpcaoResponse;
import com.api.tuctapi.dto.PerguntaRequest;
import com.api.tuctapi.dto.PerguntaResponse;
import com.api.tuctapi.exception.ResourceNotFoundException;
import com.api.tuctapi.model.Opcao;
import com.api.tuctapi.model.Pergunta;
import com.api.tuctapi.model.TipoAcessoVotacao;
import com.api.tuctapi.model.Usuario;
import com.api.tuctapi.model.Voto;
import com.api.tuctapi.enums.EntidadeAuditoria;
import com.api.tuctapi.enums.AcaoAuditoria;
import com.api.tuctapi.repository.OpcaoRepository;
import com.api.tuctapi.repository.PerguntaRepository;
import com.api.tuctapi.repository.UsuarioRepository;
import com.api.tuctapi.repository.VotoRepository;
import com.api.tuctapi.dto.ResultadoOpcaoResponse;
import com.api.tuctapi.dto.ResultadoVotacaoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VotacaoService {

    private final PerguntaRepository perguntaRepository;
    private final OpcaoRepository opcaoRepository;
    private final VotoRepository votoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuditoriaService auditoriaService;

    public VotacaoService(
            PerguntaRepository perguntaRepository,
            OpcaoRepository opcaoRepository,
            VotoRepository votoRepository,
            UsuarioRepository usuarioRepository,
            AuditoriaService auditoriaService
    ) {
        this.perguntaRepository = perguntaRepository;
        this.opcaoRepository = opcaoRepository;
        this.votoRepository = votoRepository;
        this.usuarioRepository = usuarioRepository;
        this.auditoriaService = auditoriaService;
    }

    @Transactional
    public PerguntaResponse criarPergunta(
            PerguntaRequest request,
            String usuarioEmail
    ) {

        Pergunta pergunta = new Pergunta();

        pergunta.setPergunta(request.getPergunta());
        pergunta.setTipoAcesso(request.getTipoAcesso());
        pergunta.setAtivo(
                request.getAtivo() != null
                        ? request.getAtivo()
                        : true
        );
        pergunta.setDataInicio(request.getDataInicio());
        pergunta.setDataFim(request.getDataFim());

        Pergunta salva = perguntaRepository.save(pergunta);

        Usuario usuario = usuarioRepository.findByEmail(usuarioEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado")
                );

        auditoriaService.registrar(
                usuario.getId(),
                usuario.getEmail(),
                AcaoAuditoria.CRIAR,
                EntidadeAuditoria.PERGUNTA,
                salva.getId(),
                "Pergunta criada"
        );

        return converterPerguntaResponse(salva);
    }

    @Transactional
    public OpcaoResponse adicionarOpcao(
            Integer perguntaId,
            OpcaoRequest request,
            String usuarioEmail
    ) {

        Pergunta pergunta = perguntaRepository.findById(perguntaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pergunta não encontrada"
                        )
                );

        Opcao opcao = new Opcao();

        opcao.setPergunta(pergunta);
        opcao.setDescricao(request.getDescricao());

        Opcao salva = opcaoRepository.save(opcao);

        Usuario usuario = usuarioRepository.findByEmail(usuarioEmail)
                .orElseThrow(()->
                        new ResourceNotFoundException(
                                "Usuário não encontrado"
                        )
                );

        auditoriaService.registrar(
                usuario.getId(),
                usuario.getEmail(),
                AcaoAuditoria.CRIAR,
                EntidadeAuditoria.OPCAO,
                salva.getId(),
                "Opção criada"
        );

        return new OpcaoResponse(
                salva.getId(),
                salva.getDescricao()
        );
    }

    @Transactional(readOnly = true)
    public PerguntaResponse buscarPergunta(Integer perguntaId) {

        Pergunta pergunta = perguntaRepository.findById(perguntaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pergunta não encontrada"
                        )
                );

        return converterPerguntaResponse(pergunta);
    }

    @Transactional
    public void votarComoRegistrado(
            Integer perguntaId,
            Integer opcaoId,
            String email
    ) {

        Pergunta pergunta = perguntaRepository.findById(perguntaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pergunta não encontrada"
                        )
                );

        validarPerguntaAberta(pergunta);

        if (pergunta.getTipoAcesso() != TipoAcessoVotacao.REGISTRADOS) {
            throw new IllegalArgumentException(
                    "Esta votação não permite votos de usuários registrados"
            );
        }

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado"
                        )
                );

        Opcao opcao = opcaoRepository.findById(opcaoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Opção não encontrada"
                        )
                );

        if (!opcao.getPergunta().getId().equals(perguntaId)) {
            throw new IllegalArgumentException(
                    "A opção não pertence a esta pergunta"
            );
        }

        boolean jaVotou =
                votoRepository.existsByPerguntaIdAndUsuarioId(
                        perguntaId,
                        usuario.getId()
                );

        if (jaVotou) {
            throw new IllegalStateException(
                    "Você já votou nesta pergunta"
            );
        }

        Voto voto = new Voto();

        voto.setPergunta(pergunta);
        voto.setOpcao(opcao);
        voto.setUsuario(usuario);

        votoRepository.save(voto);
    }

    @Transactional
    public String votarComoLivre(
            Integer perguntaId,
            Integer opcaoId,
            String tokenAnonimo
    ) {
        Pergunta pergunta = perguntaRepository.findById(perguntaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pergunta não encontrada"
                        )
                );

        validarPerguntaAberta(pergunta);

        if (pergunta.getTipoAcesso() != TipoAcessoVotacao.LIVRE) {
            throw new IllegalArgumentException(
                    "Esta votação não permite votos livres"
            );
        }

        Opcao opcao = opcaoRepository.findById(opcaoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Opção não encontrada"
                        )
                );

        if (!opcao.getPergunta().getId().equals(perguntaId)) {
            throw new IllegalArgumentException(
                    "A opção não pertence a esta pergunta"
            );
        }

        if (tokenAnonimo == null || tokenAnonimo.isBlank()) {
            tokenAnonimo = gerarTokenAnonimo();
        }

        boolean jaVotou =
                votoRepository.existsByPerguntaIdAndTokenAnonimo(
                        perguntaId,
                        tokenAnonimo
                );

        if (jaVotou) {
            throw new IllegalStateException(
                    "Você já votou nesta pergunta"
            );
        }

        Voto voto = new Voto();
        voto.setPergunta(pergunta);
        voto.setOpcao(opcao);
        voto.setTokenAnonimo(tokenAnonimo);

        votoRepository.save(voto);

        return tokenAnonimo;
    }
    private String gerarTokenAnonimo() {
        return java.util.UUID.randomUUID().toString();
    }

    private void validarPerguntaAberta(Pergunta pergunta) {

        if (!Boolean.TRUE.equals(pergunta.getAtivo())) {
            throw new IllegalArgumentException(
                    "Esta votação está inativa"
            );
        }

        LocalDateTime agora = LocalDateTime.now();

        if (pergunta.getDataInicio() != null &&
                agora.isBefore(pergunta.getDataInicio())) {

            throw new IllegalArgumentException(
                    "Esta votação ainda não foi iniciada"
            );
        }

        if (pergunta.getDataFim() != null &&
                agora.isAfter(pergunta.getDataFim())) {

            throw new IllegalArgumentException(
                    "Esta votação já foi encerrada"
            );
        }
    }

    private PerguntaResponse converterPerguntaResponse(
            Pergunta pergunta
    ) {

        List<OpcaoResponse> opcoes = pergunta.getOpcoes()
                .stream()
                .map(opcao ->
                        new OpcaoResponse(
                                opcao.getId(),
                                opcao.getDescricao()
                        )
                )
                .toList();

        return new PerguntaResponse(
                pergunta.getId(),
                pergunta.getPergunta(),
                pergunta.getTipoAcesso(),
                pergunta.getAtivo(),
                pergunta.getDataInicio(),
                pergunta.getDataFim(),
                opcoes
        );
    }

    @Transactional(readOnly = true)
    public ResultadoVotacaoResponse obterResultados(Integer perguntaId) {

        Pergunta pergunta = perguntaRepository.findById(perguntaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pergunta não encontrada"
                        )
                );

        long totalVotos =
                votoRepository.countByPerguntaId(perguntaId);

        List<ResultadoOpcaoResponse> resultados =
                pergunta.getOpcoes()
                        .stream()
                        .map(opcao -> {

                            long votos =
                                    votoRepository.countByPerguntaIdAndOpcaoId(
                                            perguntaId,
                                            opcao.getId()
                                    );

                            double percentual = totalVotos == 0
                                    ? 0.0
                                    : (votos * 100.0) / totalVotos;

                            return new ResultadoOpcaoResponse(
                                    opcao.getId(),
                                    opcao.getDescricao(),
                                    votos,
                                    percentual
                            );
                        })
                        .toList();

        return new ResultadoVotacaoResponse(
                pergunta.getId(),
                pergunta.getPergunta(),
                totalVotos,
                resultados
        );
    }

    @Transactional
    public PerguntaResponse atualizarPergunta(
            Integer perguntaId,
            PerguntaRequest request,
            String usuarioEmail
    ) {
        Pergunta pergunta = perguntaRepository.findById(perguntaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pergunta não encontrada")
                );

        pergunta.setPergunta(request.getPergunta());
        pergunta.setTipoAcesso(request.getTipoAcesso());
        pergunta.setAtivo(request.getAtivo());
        pergunta.setDataInicio(request.getDataInicio());
        pergunta.setDataFim(request.getDataFim());

        Pergunta perguntaAtualizada = perguntaRepository.save(pergunta);

        Usuario usuario = usuarioRepository.findByEmail(usuarioEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado")
                );

        auditoriaService.registrar(
                usuario.getId(),
                usuario.getEmail(),
                AcaoAuditoria.ATUALIZAR,
                EntidadeAuditoria.PERGUNTA,
                perguntaAtualizada.getId(),
                "Pergunta atualizada"
        );

        return converterPerguntaResponse(perguntaAtualizada);
    }

    @Transactional
    public OpcaoResponse atualizarOpcao(
            Integer perguntaId,
            Integer opcaoId,
            OpcaoRequest request,
            String usuarioEmail
    ) {
        Pergunta pergunta = perguntaRepository.findById(perguntaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pergunta não encontrada")
                );

        Opcao opcao = opcaoRepository.findById(opcaoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Opção não encontrada")
                );

        if (!opcao.getPergunta().getId().equals(pergunta.getId())) {
            throw new IllegalArgumentException(
                    "A opção não pertence a esta pergunta"
            );
        }

        opcao.setDescricao(request.getDescricao());

        Opcao opcaoAtualizada = opcaoRepository.save(opcao);

        Usuario usuario = usuarioRepository.findByEmail(usuarioEmail)
                .orElseThrow(()->
                        new ResourceNotFoundException("Usuário não  encontrado")
                );

        auditoriaService.registrar(
                usuario.getId(),
                usuario.getEmail(),
                AcaoAuditoria.ATUALIZAR,
                EntidadeAuditoria.OPCAO,
                opcaoAtualizada.getId(),
                "Opção atualizada"
        );

        return new OpcaoResponse(
                opcaoAtualizada.getId(),
                opcaoAtualizada.getDescricao()
        );
    }

    @Transactional
    public void excluirPergunta(
            Integer perguntaId,
            String usuarioEmail
            ) {

        Pergunta pergunta = perguntaRepository.findById(perguntaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pergunta não encontrada"
                        )
                );

        votoRepository.deleteByPerguntaId(perguntaId);

        perguntaRepository.delete(pergunta);

        Usuario usuario = usuarioRepository.findByEmail(usuarioEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado"
                        )
                );
        auditoriaService.registrar(
                usuario.getId(),
                usuario.getEmail(),
                AcaoAuditoria.EXCLUIR,
                EntidadeAuditoria.PERGUNTA,
                perguntaId,
                "Pergunta excluida"
        );
    }

}