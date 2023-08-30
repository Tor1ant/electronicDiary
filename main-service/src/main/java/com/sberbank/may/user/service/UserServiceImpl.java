package com.sberbank.may.user.service;

import com.sberbank.may.exception.NotFoundException;
import com.sberbank.may.user.dto.UserDto;
import com.sberbank.may.user.enums.Role;
import com.sberbank.may.user.model.User;
import com.sberbank.may.user.repository.UserRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Класс реализация сервисов для работы с объектами типа {@link User}.
 *
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * Используется для получения доступа к данным {@link User}
     */
    private final UserRepository userRepository;

    /**
     * Сохраняет указанного пользователя. Действие обернуто в транзакцию.
     *
     * @param user пользователя которого нужно сохранить
     */
    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * Возвращает список пользователей по заданным параметрам запроса.
     * Если все поля запроса пустые, выдается исключение.
     *
     * @param userDto Данные для поиска пользователя
     * @return список пользователей удовлетворяющий условиям запроса
     */
    @Override
    public List<User> searchUser(UserDto userDto) {
        if (userDto.getName().isBlank() && userDto.getEmail().isBlank() && userDto.getPhone().isBlank()) {
            throw new IllegalArgumentException("Все поля не могут быть пустыми");
        }
        return userRepository.searchUser(userDto.getName(), userDto.getEmail(),
                userDto.getPhone());
    }

    /**
     * Удаляет пользователя по указанному идентификатору. Действие обернуто в транзакцию.
     * Если пользователь с таким идентификатором не найден, выдается исключение.
     *
     * @param id идентификатор пользователя
     */
    @Override
    @Transactional
    public void deleteById(long id) {
        findUserById(id);
        userRepository.deleteById(id);
    }

    /**
     * Возвращает пользователя по указанному идентификатору.
     * Если пользователь с таким идентификатором не найден, выдается исключение.
     *
     * @param id идентификатор пользователя
     * @return {@link User} объект пользователя
     */
    @Override
    public User findUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id=" + id + " не найден"));
    }

    /**
     * Обновляет информацию о пользователе.
     * Если пользователь с таким идентификатором не найден, выдается исключение.
     *
     * @param user объект пользователя с новыми данными
     */
    @Override
    public void patchUser(User user) {
        User userForUpdate = findUserById(user.getId());
        userForUpdate.setEmail(user.getEmail());
        userForUpdate.setRole(user.getRole());
        userForUpdate.setPhone(user.getPhone());
        userForUpdate.setPassword(user.getPassword());
        userForUpdate.setName(user.getName());
        userRepository.save(userForUpdate);
    }

    /**
     * Возвращает все существующие объекты {@link User}.
     *
     * @return список всех пользователей
     */
    @Override
    public List<User> searchAllUser() {
        return userRepository.findAll();
    }

    /**
     * Возвращает всех пользователей с ролью "Родитель".
     *
     * @return список пользователей с ролью "Родитель"
     */
    @Override
    public Set<User> getAllParents() {
        return userRepository.findUserByRole(Role.ROLE_PARENT);
    }

    /**
     * Возвращает всех пользователей с ролью "Учитель".
     *
     * @return список пользователей с ролью "Учитель"
     */
    @Override
    public Set<User> getAllTeachers() {
        return userRepository.findUserByRole(Role.ROLE_TEACHER);
    }

    /**
     * Возвращает пользователя по указанному имени.
     * Если пользователь с таким именем не найден, выдается исключение.
     *
     * @param name имя пользователя
     * @return {@link User} объект пользователя
     */
    @Override
    public User findUserByName(String name) {
        return userRepository.findUserByName(name)
                .orElseThrow(() -> new NotFoundException("Пользователь с именем " + name + " не найден"));
    }
}
