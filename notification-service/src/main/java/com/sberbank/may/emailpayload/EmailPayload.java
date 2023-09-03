package com.sberbank.may.emailpayload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.thymeleaf.context.Context;

/**
 * Класс представляет собой модель данных для отправки электронной почты.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmailPayload {
    private String parentEmail;
    private String subject;
    private String templateName;
    private Context context;
}

