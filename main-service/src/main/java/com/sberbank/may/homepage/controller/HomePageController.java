package com.sberbank.may.homepage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для отображения различных домашних страниц.
 * Обрабатывает запросы на пути "/user"
 */
@Controller
@RequiredArgsConstructor
public class HomePageController {
  
   /**
     * Отображает домашнюю страницу для администратора
     *
     * @return путь к домашней странице администратора
     */
    @GetMapping("/admin")
    public String showAdminPage() {
        return "home_pages/homepage_admin";
    }

    /**
     * Отображает домашнюю страницу для учителя
     *
     * @return путь к домашней странице учителя
     */
    @GetMapping("/teacher")
    public String showTeacherPage() {
        return "home_pages/homepage_teacher";
    }

    /**
     * Отображает домашнюю страницу для родителя
     *
     * @return путь к домашней странице родителя
     */
    @GetMapping("/parent")
    public String showParentPage() {
        return "home_pages/homepage_parent";
    }
}
