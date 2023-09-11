package com.sberbank.may.logincontroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "home_pages/login";
    }

    @GetMapping("/login/success")
    public String loginSuccess(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role;
        if (auth != null) {
            role = auth.getAuthorities().iterator().next().getAuthority();
            switch (role) {
                case "ROLE_ADMIN" -> {
                    return "redirect:/admin";
                }
                case "ROLE_TEACHER" -> {
                    return "redirect:/teacher";
                }
                case "ROLE_PARENT" -> {
                    return "redirect:/parent";
                }
            }
        }
        return "redirect:/login?error";
    }

@GetMapping("/logout")
public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login";
    }
}



