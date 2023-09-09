package com.sberbank.may.lesson.controller;

import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.lesson.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер для работы с домашними заданиями.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/homework")
public class HomeworkController {

    private final LessonService lessonService;

    /**
     * Отображает форму обновления для указанного домашнего задания.
     *
     * @param id    ID домашнего задания, которое нужно обновить.
     * @param model Модель для добавления атрибутов.
     * @return Маршрут к странице обновления домашнего задания.
     */
    @PostMapping("/edit")
    public String showUpdateForm(@RequestParam("id") long id, Model model) {
        Lesson lesson = lessonService.findLessonById(id);
        model.addAttribute("lesson", lesson);
        return "lesson_pages/update_homework";
    }

    /**
     * Обновляет домашнее задание на основе полученной модели урока.
     *
     * @param lesson Модель урока с обновленными данными.
     * @return Перенаправление на страницу поиска уроков после обновления.
     */
    @PostMapping("/update")
    public String updateHomework(@ModelAttribute("lesson") Lesson lesson) {
        lessonService.updateLesson(lesson);
        return "redirect:/lesson/searchLesson";
    }
}
