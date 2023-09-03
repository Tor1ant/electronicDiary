package com.sberbank.may.user.dto;

import lombok.Data;

/**
 * Класс {@code UserDto} представляет собой Data Transfer Object, используемый для работы с пользователями.
 * <p>
 * Предоставляет информацию о пользователе, включая {@code name}, {@code email} и {@code phone}.
 */
@Data
public class UserDto {
    private String name;
    private String email;
    private String phone;
}
