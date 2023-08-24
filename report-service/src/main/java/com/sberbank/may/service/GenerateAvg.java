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

public class GenerateAvg {

    public ResponseEntity<byte[]> generatePdfAvg(ReportData reportData){
        try {
            // Загрузка шаблона отчета
            ClassPathResource resource = new ClassPathResource("reportAvg.jrxml");
            InputStream inputStream = resource.getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Создание источника данных для отчета
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData.getReportItems());

            // Заполнение шаблона данными и создание объекта JasperPrint
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            // Экспортирование отчета в формат PDF
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            // Установка заголовков ответа
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
