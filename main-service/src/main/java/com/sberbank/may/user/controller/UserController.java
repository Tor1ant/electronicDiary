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
        List<User> users = userService.searchUser(userDto);
        model.addAttribute("users", users);
        return "userList";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteById(id);
        return "redirect:/user/searchUserForm";
    }

    @GetMapping("/edit")
    public String showUpdateForm(@RequestParam("id") long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        System.out.println(user);
        return "update_user";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.patchUser(user);
        return "redirect:/user/searchUserForm";
    }
}

