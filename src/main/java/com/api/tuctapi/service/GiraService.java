package com.api.tuctapi.service;

import com.api.tuctapi.dto.GiraRequest;
import com.api.tuctapi.dto.GiraResponse;
import com.api.tuctapi.model.Gira;
import com.api.tuctapi.repository.GiraRepository;
import com.api.tuctapi.response.PaginacaoResponse;
import com.api.tuctapi.exception.ResourceNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class GiraService {

    private final GiraRepository giraRepository;

    public GiraService(GiraRepository giraRepository) {
        this.giraRepository = giraRepository;
    }

    public List<GiraResponse> listarGirasPublicas() {

        List<Gira> giras =
                giraRepository.findByIsPublicTrue();

        return giras.stream()
                .map(GiraResponse::new)
                .toList();
    }

    public List<GiraResponse> listarGirasPublicasMesAtual() {

        LocalDate hoje = LocalDate.now();

        LocalDate primeiroDia =
                hoje.withDayOfMonth(1);

        LocalDate ultimoDia =
                hoje.withDayOfMonth(
                        hoje.lengthOfMonth()
                );

        LocalDateTime inicio =
                primeiroDia.atStartOfDay();

        LocalDateTime fim =
                ultimoDia.atTime(LocalTime.MAX);

        List<Gira> giras =
                giraRepository.findByIsPublicTrueAndDateGiraBetween(
                        inicio,
                        fim
                );

        return giras.stream()
                .map(GiraResponse::new)
                .toList();
    }

    public List<GiraResponse> listarGirasPublicasPorMes(Integer mes) {

        if (mes == null || mes < 1 || mes > 12) {
            throw new IllegalArgumentException(
                    "O mês deve estar entre 1 e 12"
            );
        }

        LocalDate hoje = LocalDate.now();

        int anoAtual = hoje.getYear();

        YearMonth yearMonth =
                YearMonth.of(anoAtual, mes);

        LocalDateTime inicio =
                yearMonth
                        .atDay(1)
                        .atStartOfDay();

        LocalDateTime fim =
                yearMonth
                        .atEndOfMonth()
                        .atTime(LocalTime.MAX);

        List<Gira> giras =
                giraRepository.findByIsPublicTrueAndDateGiraBetween(
                        inicio,
                        fim
                );

        return giras.stream()
                .map(GiraResponse::new)
                .toList();
    }

    public GiraResponse criar(GiraRequest request) {

        Gira gira = new Gira();

        gira.setTitle(request.getTitle());
        gira.setDescription(request.getDescription());
        gira.setImageGira(request.getImageGira());
        gira.setDateGira(request.getDateGira());
        gira.setIsPublic(request.getIsPublic());
        gira.setConfirmGira(request.getConfirmGira());

        Gira giraSalva = giraRepository.save(gira);

        return new GiraResponse(giraSalva);
    }

    @Transactional
    public List<GiraResponse> criarEmLote(List<GiraRequest> requests) {

        List<Gira> giras = requests.stream()
                .map(request -> {

                    Gira gira = new Gira();

                    gira.setTitle(request.getTitle());
                    gira.setDescription(request.getDescription());
                    gira.setImageGira(request.getImageGira());
                    gira.setDateGira(request.getDateGira());
                    gira.setIsPublic(request.getIsPublic());
                    gira.setConfirmGira(request.getConfirmGira());

                    return gira;
                })
                .toList();

        List<Gira> girasSalvas = giraRepository.saveAll(giras);

        return girasSalvas.stream()
                .map(GiraResponse::new)
                .toList();
    }

    public PaginacaoResponse<GiraResponse> listar(Pageable pageable) {

        Page<Gira> pagina = giraRepository.findAll(pageable);

        var giras = pagina.getContent()
                .stream()
                .map(GiraResponse::new)
                .toList();

        return new PaginacaoResponse<>(
                giras,
                pagina.getNumber(),
                pagina.getSize(),
                pagina.getTotalElements(),
                pagina.getTotalPages()
        );
    }
    public GiraResponse buscarPorId(Integer id) {

        Gira gira = giraRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Gira não encontrada"
                        )
                );

        return new GiraResponse(gira);
    }

    public List<GiraResponse> listarMesAtual() {

        LocalDate hoje = LocalDate.now();

        LocalDate primeiroDia = hoje.withDayOfMonth(1);

        LocalDate ultimoDia = hoje.withDayOfMonth(
                hoje.lengthOfMonth()
        );

        LocalDateTime inicio = primeiroDia.atStartOfDay();

        LocalDateTime fim = ultimoDia.atTime(
                LocalTime.MAX
        );

        List<Gira> giras = giraRepository.findByDateGiraBetween(
                inicio,
                fim
        );

        return giras.stream()
                .map(GiraResponse::new)
                .toList();
    }

    public List<GiraResponse> listarPorMes(Integer mes) {

        if (mes == null || mes < 1 || mes > 12) {
            throw new IllegalArgumentException(
                    "O mês deve estar entre 1 e 12"
            );
        }

        LocalDate hoje = LocalDate.now();

        int anoAtual = hoje.getYear();

        YearMonth yearMonth = YearMonth.of(anoAtual, mes);

        LocalDateTime inicio = yearMonth
                .atDay(1)
                .atStartOfDay();

        LocalDateTime fim = yearMonth
                .atEndOfMonth()
                .atTime(LocalTime.MAX);

        List<Gira> giras = giraRepository.findByDateGiraBetween(
                inicio,
                fim
        );

        return giras.stream()
                .map(GiraResponse::new)
                .toList();
    }

    public GiraResponse atualizar(Integer id, GiraRequest request) {

        Gira gira = giraRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Gira não encontrada")
                );

        gira.setTitle(request.getTitle());
        gira.setDescription(request.getDescription());
        gira.setImageGira(request.getImageGira());
        gira.setDateGira(request.getDateGira());
        gira.setIsPublic(request.getIsPublic());
        gira.setConfirmGira(request.getConfirmGira());

        Gira giraAtualizada = giraRepository.save(gira);

        return new GiraResponse(giraAtualizada);
    }

    public void deletar(Integer id) {

        Gira gira = giraRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Gira não encontrada")
                );

        giraRepository.delete(gira);
    }
}

