package com.sberbank.may.controller;

import com.sberbank.may.dto.ReportData;
import com.sberbank.may.service.GenerateAvg;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ReportController {

    @PostMapping(value = "/report-avg", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateReport(@RequestBody ReportData reportData) {
        GenerateAvg generateAvg = new GenerateAvg();
       return generateAvg.generatePdfAvg(reportData);
    }
}
