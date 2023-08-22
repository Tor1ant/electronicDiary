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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> searchUser(UserDto userDto) {
        if (userDto.getName().isBlank() && userDto.getEmail().isBlank() && userDto.getPhone().isBlank()) {
            throw new IllegalArgumentException("Все поля не могут быть пустыми");
        }
        return userRepository.searchUser(userDto.getName(), userDto.getEmail(),
                userDto.getPhone());
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        findUserById(id);
        userRepository.deleteById(id);
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id=" + id + " не найден"));
    }

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

    @Override
    public List<User> searchAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Set<User> getAllTeachers() {
        return userRepository.findUserByRole(Role.ROLE_TEACHER);
    }

    @Override
    public User findUserByName(String name) {
        return userRepository.findUserByName(name)
                .orElseThrow(() -> new NotFoundException("Пользователь с именем " + name + " не найден"));
    }
}
