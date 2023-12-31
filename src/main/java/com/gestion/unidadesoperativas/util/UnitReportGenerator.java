package com.gestion.unidadesoperativas.util;


import com.gestion.unidadesoperativas.domain.model.OperationalUnit;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UnitReportGenerator {
    private final Scheduler jdbcScheduler = Schedulers.boundedElastic();

    public Mono<byte[]> exportToPdf(List<OperationalUnit> list) {
        return Mono.fromCallable(() -> generatePdf(list))
                .subscribeOn(jdbcScheduler);
    }

    public Mono<byte[]> exportToXls(List<OperationalUnit> list) {
        return Mono.fromCallable(() -> generateXls(list))
                .subscribeOn(jdbcScheduler);
    }

    private byte[] generatePdf(List<OperationalUnit> list) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReport(list));
    }

    private byte[] generateXls(List<OperationalUnit> list) throws JRException, FileNotFoundException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(getReport(list)));
        exporter.setExporterOutput(output);
        exporter.exportReport();
        output.close();
        return byteArray.toByteArray();
    }

    private JasperPrint getReport(List<OperationalUnit> list) throws FileNotFoundException, JRException {
        Map<String, Object> params = new HashMap<>();
        params.put("funcdata", new JRBeanCollectionDataSource(list));

        return JasperFillManager.fillReport(JasperCompileManager.compileReport(
                ResourceUtils.getFile("classpath:UnidadesOperativas.jrxml")
                        .getAbsolutePath()), params, new JREmptyDataSource());
    }
}