package com.sberbank.may.user.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sberbank.may.user.dto.UserDto;
import com.sberbank.may.user.enums.Role;
import com.sberbank.may.user.model.User;
import com.sberbank.may.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    private User parent2;
    private User parent3;
    private User teacher1;

    @BeforeEach
    void setUp() {
        teacher1 = new User(null, "Румянцева Анастасия Ивановна", "password",
                "testMail@mail.ru", "+79211554785",
                Role.ROLE_TEACHER);
        parent2 = new User(null, "Расторгуева Мария Александровна", "zdanie255",
                "MAR@mail.ru", "+7523654789",
                Role.ROLE_PARENT);
        parent3 = new User(null, "Расторгуев Павел Владимирович", "1998",
                "PVR@mail.ru", "+79635284512",
                Role.ROLE_PARENT);
    }

    @DisplayName("Проверка создания пользователя")
    @Test
    void saveUser() {
        when(userRepository.save(any())).thenReturn(any());
        userService.saveUser(teacher1);
        userService.saveUser(parent2);
        userService.saveUser(parent3);
        verify(userRepository, times(3)).save(any());
    }

    @DisplayName("Проверка поиска пользователя")
    @Test
    void searchUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail(parent2.getEmail());
        userDto.setName(parent2.getName());
        userDto.setPhone(parent2.getPhone());
        when(userRepository.searchUser(anyString(), anyString(), anyString())).thenReturn(List.of(parent2));
        assertThat(userService.searchUser(userDto), contains(parent2));
        verify(userRepository).searchUser(anyString(), anyString(), anyString());

    }

    @DisplayName("Проверка удаления пользователя по id")
    @Test
    void deleteById() {
        doNothing().when(userRepository).deleteById(anyLong());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(teacher1));
        userService.deleteById(1L);
        verify(userRepository).deleteById(anyLong());
    }

    @DisplayName("Проверка получения пользователя по id")
    @Test
    void findUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(teacher1));
        userService.findUserById(1L);
        verify(userRepository).findById(anyLong());
    }

    @DisplayName("Проверка обновления данных пользователя")
    @Test
    void patchUser() {
        User newTeacher = new User(1L, "Пропатченный пользователь", "новый пароль",
                "neMail@mail.ru", "+79211554745", Role.ROLE_TEACHER);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(teacher1));
        userService.patchUser(newTeacher);
        verify(userRepository).save(any());
    }

    @DisplayName("Проверка получения всех пользователей")
    @Test
    void searchAllUser() {
        when(userRepository.findAll()).thenReturn(List.of(teacher1, parent2, parent3));
        assertThat(userService.searchAllUser(), contains(teacher1, parent2, parent3));
        verify(userRepository).findAll();
    }

    @DisplayName("Проверка получения всех пользователей с ролью PARENT")
    @Test
    void getAllParents() {
        when(userRepository.findUserByRole(any())).thenReturn(Set.of(parent2, parent3));
        assertThat(userService.getAllParents(), hasItems(parent2, parent3));
        verify(userRepository).findUserByRole(any());
    }

    @DisplayName("Проверка поиска пользователя по имени")
    @Test
    void findUserByName() {
        when(userRepository.findUserByName(anyString())).thenReturn(Optional.of(parent3));
        assertThat(userService.findUserByName("test"), is(parent3));
        verify(userRepository).findUserByName(anyString());
    }
}