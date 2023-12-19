package com.gestion.unidadesoperativas.service.impl;

import com.gestion.unidadesoperativas.domain.dto.OperationalUnitRequestDto;
import com.gestion.unidadesoperativas.domain.dto.OperationalUnitResponseDto;
import com.gestion.unidadesoperativas.domain.mapper.OperationalUnitMapper;
import com.gestion.unidadesoperativas.domain.model.OperationalUnit;
import com.gestion.unidadesoperativas.repository.OperationalUnitRepository;
import com.gestion.unidadesoperativas.service.OperationalUnitService;
import com.gestion.unidadesoperativas.exception.ResourceNotFoundException;
import com.gestion.unidadesoperativas.util.UnitReportGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Objects;

import static com.gestion.unidadesoperativas.domain.mapper.OperationalUnitMapper.toModel;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationalUnitImpl implements OperationalUnitService {

    final OperationalUnitRepository operationalUnitRepository;

    @Autowired
    private UnitReportGenerator unitReportGenerator;

    @Override
    public Mono<OperationalUnitResponseDto> findById(Integer id) {
        return this.operationalUnitRepository.findById(id)
                .map(OperationalUnitMapper::toDto);
    }

    @Override
    public Flux<OperationalUnitResponseDto> findAll() {
        return this.operationalUnitRepository.findAll()
                .sort(Comparator.comparing(OperationalUnit::getId_operativeunit).reversed())
                .map(OperationalUnitMapper::toDto);
    }

    @Override
    public Flux<OperationalUnitResponseDto> findAllActive() {
        return this.operationalUnitRepository.findAll()
                .sort(Comparator.comparing(OperationalUnit::getId_operativeunit).reversed())
                .filter(active -> Objects.equals(active.getStatus(), "A"))
                .map(OperationalUnitMapper::toDto);
    }

    @Override
    public Flux<OperationalUnitResponseDto> findAllInactive() {
        return this.operationalUnitRepository.findAll()
                .sort(Comparator.comparing(OperationalUnit::getId_operativeunit).reversed())
                .filter(active -> Objects.equals(active.getStatus(), "I"))
                .map(OperationalUnitMapper::toDto);
    }


    @Override
    public Mono<OperationalUnitResponseDto> saveNewLegalGuardian(OperationalUnitRequestDto request) {
        return this.operationalUnitRepository.save(toModel(request))
                .map(OperationalUnitMapper::toDto);
    }

    @Override
    public Mono<OperationalUnitResponseDto> updateLegalGuardian(OperationalUnitRequestDto request, Integer id) {
        return this.operationalUnitRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("El identificador: " + id + "no fue encontrado.")))
                .flatMap(dataFuncionary -> this.operationalUnitRepository.save(toModel(request, dataFuncionary.getId_operativeunit())))
                .map(OperationalUnitMapper::toDto);
    }

    @Override
    public Mono<OperationalUnitResponseDto> deleteLogicalLegalGuardian(Integer id) {
        return this.operationalUnitRepository.findById(id)
                .map((delete) -> {
                    delete.setStatus("I");
                    return delete;
                })
                .flatMap(operationalUnitRepository::save)
                .map(OperationalUnitMapper::toDto);
    }

    @Override
    public Mono<OperationalUnitResponseDto> reactiveLogicalLegalGuardian(Integer id) {
        return this.operationalUnitRepository.findById(id)
                .map((reactive) -> {
                    reactive.setStatus("A");
                    return reactive;
                })
                .flatMap(operationalUnitRepository::save)
                .map(OperationalUnitMapper::toDto);
    }

    @Override
    public Mono<Void> deleteLegalGuardian(Integer id) {
        return this.operationalUnitRepository.deleteById(id);
    }


    public Mono<Mono<byte[]>> exportPdf() {
        return operationalUnitRepository.findAll()
                .collectList()
                .map(unitReportGenerator::exportToPdf);
    }

    public Mono<Mono<byte[]>> exportXls() {
        return operationalUnitRepository.findAll()
                .collectList()
                .map(unitReportGenerator::exportToXls);
    }

}
