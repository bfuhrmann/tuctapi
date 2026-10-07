package com.api.tuctapi.service;

import com.api.tuctapi.enums.AcaoAuditoria;
import com.api.tuctapi.enums.EntidadeAuditoria;
import com.api.tuctapi.model.Auditoria;
import com.api.tuctapi.repository.AuditoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaService(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    @Transactional
    public void registrar(
            Integer usuarioId,
            String usuarioEmail,
            AcaoAuditoria acao,
            EntidadeAuditoria entidade,
            Integer entidadeId,
            String descricao
    ) {
        Auditoria auditoria = new Auditoria();

        auditoria.setUsuarioId(usuarioId);
        auditoria.setUsuarioEmail(usuarioEmail);
        auditoria.setAcao(acao);
        auditoria.setEntidade(entidade);
        auditoria.setEntidadeId(entidadeId);
        auditoria.setDescricao(descricao);

        auditoriaRepository.save(auditoria);
    }
}