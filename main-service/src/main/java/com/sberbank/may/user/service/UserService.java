package com.sberbank.may.user.service;

import com.sberbank.may.user.dto.UserDto;
import com.sberbank.may.user.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    void saveUser(User user);

    List<User> searchUser(UserDto userDto);

    void deleteById(long id);

    User findUserById(long id);

    User findUserByName(String name);

    void patchUser(User user);

    List<User> searchAllUser();

    Set<User> getAllParents();

    Set<User> getAllTeachers();
}
