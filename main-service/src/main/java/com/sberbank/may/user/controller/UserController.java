package com.sberbank.may.user.controller;

import com.sberbank.may.user.dto.UserDto;
import com.sberbank.may.user.model.User;
import com.sberbank.may.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер предоставляет методы для работы с пользователями.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * Метод принимает объект User, сохраняет его и перенаправляет пользователя на форму пользователя.
     */
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/user/userForm";
    }

    /**
     * Метод возвращает представление формы пользователя.
     */
    @GetMapping("/userForm")
    public String showUserForm(@ModelAttribute("user") User user) {
        return "user_pages/create_user";
    }

    /**
     * Метод возвращает представление формы поиска пользователя.
     */
    @GetMapping("/searchUserForm")
    public String showSearchUserForm(@ModelAttribute("user") UserDto userDto) {
        return "user_pages/userSearch";
    }

    /**
     * Метод ищет пользователя согласно деталям, передаваемым в пользовательском DTO, и возвращает представление списка
     * пользователей.
     */
    @GetMapping("/searchUser")
    public String searchUser(@ModelAttribute("user") UserDto userDto, Model model) {
        List<User> users = userService.searchUser(userDto);
        model.addAttribute("users", users);
        return "user_pages/userList";
    }

    /**
     * Метод удаляет пользователя по его id и перенаправляет пользователя на страницу со всеми пользователями.
     */
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteById(id);
        return "redirect:/user/allUsers";
    }

    /**
     * Метод возвращает представление формы обновления пользователя.
     */
    @PostMapping("/edit")
    public String showUpdateForm(@RequestParam("id") long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user_pages/update_user";
    }

    /**
     * Метод обновляет пользователя и перенаправляет пользователя на страницу со всеми пользователями.
     */
    @PostMapping("/editUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.patchUser(user);
        return "redirect:/user/allUsers";
    }

    /**
     * Метод возвращает представление списка всех пользователей.
     */
    @GetMapping("/allUsers")
    public String searchAllUser(Model model) {
        List<User> users = userService.searchAllUser();
        model.addAttribute("users", users);
        return "user_pages/userList";
    }
}

