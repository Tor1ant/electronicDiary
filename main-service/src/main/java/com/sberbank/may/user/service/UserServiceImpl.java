package com.sberbank.may.user.service;

import com.sberbank.may.exception.NotFoundException;
import com.sberbank.may.user.dto.UserDto;
import com.sberbank.may.user.model.User;
import com.sberbank.may.user.repository.UserRepository;
import java.util.List;
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
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id=" + id + " не найден"));
        userRepository.deleteById(id);
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id=" + id + " не найден"));
    }

    @Override
    public User patchUser(User user) {
        User userForUpdate = userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("Пользователь с id=" + user.getId() + " не найден"));
        userForUpdate.setEmail(user.getEmail());
        userForUpdate.setRole(user.getRole());
        userForUpdate.setPhone(user.getPhone());
        userForUpdate.setPassword(user.getPassword());
        userForUpdate.setName(user.getName());
        userRepository.save(userForUpdate);
        return userRepository.save(userForUpdate);
    }

    @Override
    public User findUserByName(String name) {
        return userRepository.findUserByName(name)
                .orElseThrow(() -> new NotFoundException("Пользователь с именем " + name + " не найден"));
    }
}
