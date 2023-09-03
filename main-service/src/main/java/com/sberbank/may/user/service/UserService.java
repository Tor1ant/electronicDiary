package com.sberbank.may.user.service;

import com.sberbank.may.user.dto.UserDto;
import com.sberbank.may.user.model.User;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс UserService предоставляет методы для работы с пользователями системы.
 */
public interface UserService {

    /**
     * Сохраняет пользователя в базе данных.
     * @param user объект пользователя, который необходимо сохранить.
     */
    void saveUser(User user);

    /**
     * Ищет пользователей по заданным критериям.
     * @param userDto объект DTO, содержащий критерии для поиска.
     * @return Список пользователей, удовлетворяющих критериям поиска.
     */
    List<User> searchUser(UserDto userDto);

    /**
     * Удаляет пользователя по-заданному ID.
     * @param id идентификатор пользователя, которого необходимо удалить.
     */
    void deleteById(long id);

    /**
     * Находит пользователя по-заданному ID.
     * @param id идентификатор пользователя.
     * @return Объект пользователя с заданным ID.
     */
    User findUserById(long id);

    /**
     * Находит пользователя по заданному имени.
     * @param name имя пользователя.
     * @return Объект пользователя с заданным именем.
     */
    User findUserByName(String name);

    /**
     * Обновляет информацию о пользователе.
     * @param user объект пользователя с новой информацией.
     */
    void patchUser(User user);

    /**
     * Получает всех пользователей из системы.
     * @return Список всех пользователей в системе.
     */
    List<User> searchAllUser();

    /**
     * Получает всех "родителей" из системы.
     * @return Набор всех "родителей" в системе.
     */
    Set<User> getAllParents();

    /**
     * Получает всех "учителей" из системы.
     * @return Набор всех "учителей" в системе.
     */
    Set<User> getAllTeachers();
}
