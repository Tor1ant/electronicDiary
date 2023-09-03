package com.sberbank.may.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Сервис для отправки электронной почты.
 */
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    /**
     * Конструктор EmailService.
     *
     * @param javaMailSender объект для отправки электронных писем
     * @param templateEngine объект для обработки шаблонов писем
     */
    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * Отправить электронное письмо.
     *
     * @param parentEmail емейл получателя
     * @param subject тема письма
     * @param templateName имя шаблона письма
     * @param context контекст, на основе которого будет создаваться контент письма
     * @throws MessagingException если произошла ошибка при отправке письма
     */
    public void sendEmail(String parentEmail, String subject, String templateName, Context context) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(parentEmail);
        helper.setSubject(subject);

        String emailContent = templateEngine.process(templateName, context);
        helper.setText(emailContent, true);

        javaMailSender.send(message);
    }
}
