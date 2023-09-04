package com.sberbank.may.homepage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    @GetMapping("/admin")
    public String showAdminPage() {
        return "home_pages/homepage_admin";
    }

    @GetMapping("/teacher")
    public String showTeacherPage() {
        return "home_pages/homepage_teacher";
    }

    @GetMapping("/parent")
    public String showParentPage() {
        return "home_pages/homepage_parent";
    }
}
