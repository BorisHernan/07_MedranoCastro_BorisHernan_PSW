package com.gestion.unidadesoperativas.web;

import com.gestion.unidadesoperativas.domain.dto.OperationalUnitRequestDto;
import com.gestion.unidadesoperativas.domain.dto.OperationalUnitResponseDto;
import com.gestion.unidadesoperativas.repository.OperationalUnitRepository;
import com.gestion.unidadesoperativas.service.OperationalUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.*;




@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/ms-soa")
@RequiredArgsConstructor
public class OperationalUnitController {


    final OperationalUnitService operationalUnitService;

    final OperationalUnitRepository operationalUnitRepository;

    @GetMapping("{id_ou}")
    public Mono<OperationalUnitResponseDto> getDataOperatinalUnitById(@PathVariable Integer id_ou) {
        return this.operationalUnitService.findById(id_ou);
    }

    @GetMapping("/listData")
    public Flux<OperationalUnitResponseDto> getDataOperatinalUnitAll() {
        return this.operationalUnitService.findAll();
    }

    @GetMapping("/listData/active")
    public Flux<OperationalUnitResponseDto> getDataOperatinalUnitActive() { return this.operationalUnitService.findAllActive();
    }

    @GetMapping("/listData/inactive")
    public Flux<OperationalUnitResponseDto> getDataOperatinalUnitInactive() { return this.operationalUnitService.findAllInactive();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<OperationalUnitResponseDto> saveNewDataOperatinalUnit(@RequestBody OperationalUnitRequestDto dto) {
        return this.operationalUnitService.saveNewLegalGuardian(dto);
    }

    @PutMapping("/{id_ou}")
    public Mono<OperationalUnitResponseDto> updateDataOperatinalUnit(@RequestBody OperationalUnitRequestDto dto, @PathVariable Integer id_ou) {
        return this.operationalUnitService.updateLegalGuardian(dto, id_ou);
    }

    @PatchMapping("/deleteLogical/{id_ou}")
    public Mono<OperationalUnitResponseDto> deleteLogicalOperatinalUnit(@PathVariable Integer id_ou) {
        return this.operationalUnitService.deleteLogicalLegalGuardian(id_ou);
    }

    @PatchMapping("/reactiveLogical/{id_ou}")
    public Mono<OperationalUnitResponseDto> reactiveLogicalOperatinalUnit(@PathVariable Integer id_ou) {
        return this.operationalUnitService.reactiveLogicalLegalGuardian(id_ou);
    }

    @DeleteMapping("/{id_ou}")
    public Mono<Void> deleteTotalOperatinalUnit(@PathVariable Integer id_ou) {
        return this.operationalUnitService.deleteLegalGuardian(id_ou);
    }

    @GetMapping("/export-pdf")
    public Mono<ResponseEntity<byte[]>> exportPdf() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("FuncReport", "OperativeUnit.pdf");

        return operationalUnitService.exportPdf()
                .flatMap(pdfBytes -> pdfBytes)
                .map(pdfBytes -> ResponseEntity.ok().headers(headers).body(pdfBytes));
    }

    @GetMapping("/export-xls")
    public Mono<ResponseEntity<byte[]>> exportXls() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("OperativeUnitReport.xls").build());

        return operationalUnitService.exportXls()
                .flatMap(xlsBytes -> xlsBytes)
                .map(xlsBytes -> ResponseEntity.ok().headers(headers).body(xlsBytes));
    }

}
