package com.sberbank.may.user.service;

import com.sberbank.may.user.dto.UserDto;
import com.sberbank.may.user.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    List<User> searchUser(UserDto userDto);
}
