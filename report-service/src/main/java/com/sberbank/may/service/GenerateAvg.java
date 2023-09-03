package com.sberbank.may.service;

import com.sberbank.may.dto.ReportData;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;

/**
 * Класс отчетов GenerateAvg.
 * <p>
 * Класс предназначен для генерации отчетов в формате PDF на основе данных ReportData.
 */
public class GenerateAvg {

    /**
     * Создает PDF отчет на основе данных ReportData.
     *
     * @param reportData Данные для создания отчета.
     * @return ResponseEntity с готовым PDF отчетом или кодом ошибки, если возникла ошибка в процессе создания отчета.
     */
    public ResponseEntity<byte[]> generatePdfAvg(ReportData reportData){
        try {
            ClassPathResource resource = new ClassPathResource("reportAvg.jrxml");
            InputStream inputStream = resource.getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData.getReportItems());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "report.pdf");

            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
