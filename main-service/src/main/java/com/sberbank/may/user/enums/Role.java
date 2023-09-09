package com.sberbank.may.user.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Перечисление {@code Role} представляет собой набор ролей, которые могут быть назначены пользователям.
 * Роли определяют уровень доступа пользователя к различным частям системы.
 *
 * <p> Роли включают в себя:
 *
 * <ul>
 *   <li> {@code ROLE_ADMIN} - Роль администратора. У пользователей с этой ролью есть доступ ко всем функциям системы.
 *   <li> {@code ROLE_PARENT} - Роль родителя. У пользователей с этой ролью есть ограниченный доступ к системе,
 *        который определяется политиками системы в отношении родительского контроля.
 *   <li> {@code ROLE_TEACHER} - Роль учителя. У пользователей с этой ролью есть доступ к образовательным ресурсам системы.
 * </ul>
 */
public enum Role implements GrantedAuthority {

    ROLE_ADMIN,
    ROLE_PARENT,
    ROLE_TEACHER;
  
    @Override
    public String getAuthority() {
        return name();
    }
}