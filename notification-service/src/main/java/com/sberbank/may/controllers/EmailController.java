package com.sberbank.may.controllers;

import com.sberbank.may.emailpayload.EmailPayload;
import com.sberbank.may.services.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для обработки писем. Содержит один endpoint для отправки электронных писем.
 */
@RestController
public class EmailController {

    private final EmailService emailService;

    /**
     * Конструктор контроллера - внедряет зависимость emailService.
     *
     * @param emailService служба/сервис для отправки электронных писем.
     */
    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Метод обрабатывает HTTP POST запросы на "/send-email" эндпоинт, использует службу emailService для отправки
     * электронного письма.
     *
     * @param payload объект содержит информацию для отправки электронного письма.
     * @return HTTP-ответ со статусом OK и сообщением "Email sent successfully", если письмо было успешно отправлено. В
     * случае ошибки при отправке письма возвращает HTTP-ответ со статусом INTERNAL_SERVER_ERROR и сообщением "Failed to
     * send email".
     */
    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmailToParent(@RequestBody EmailPayload payload) {
        try {
            emailService.sendEmail(payload.getParentEmail(), payload.getSubject(), payload.getTemplateName(),
                    payload.getContext());
            return ResponseEntity.ok("Email sent successfully");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }
}