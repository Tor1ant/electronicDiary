package com.sberbank.may.user.controller;

import com.sberbank.may.user.dto.UserDto;
import com.sberbank.may.user.model.User;
import com.sberbank.may.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/user/userForm";
    }

    @GetMapping("/userForm")
    public String showUserForm(@ModelAttribute("user") User user) {
        return "create_user";
    }

    @GetMapping("/searchUserForm")
    public String showSearchUserForm(@ModelAttribute("user") UserDto userDto) {
        return "userSearch";
    }

    @GetMapping("/searchUser")
    public String searchUser(@ModelAttribute("user") UserDto userDto, Model model) {
        List<User> users =  userService.searchUser(userDto);
        model.addAttribute("users", users);
        return "userList";
    }
}

