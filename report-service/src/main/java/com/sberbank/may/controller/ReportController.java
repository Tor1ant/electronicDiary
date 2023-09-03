package com.sberbank.may.controller;

import com.sberbank.may.dto.ReportData;
import com.sberbank.may.service.GenerateAvg;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для отчета.
 */
@RestController
public class ReportController {

    /**
     * Метод генерирует PDF отчет с использованием входных данных отчета {@link ReportData}.
     *
     * @param reportData Данные отчета. Этот параметр используется для генерации отчета.
     * @return ResponseEntity, содержащий в себе сгенерированный PDF отчет.
     * В случае успешного создания отчета возвращается HTTP статус 200.
     * В содержимое ответа включается сгенерированный PDF отчет.
     */
    @PostMapping(value = "/report-avg", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateReport(@RequestBody ReportData reportData) {
        GenerateAvg generateAvg = new GenerateAvg();
       return generateAvg.generatePdfAvg(reportData);
    }
}
