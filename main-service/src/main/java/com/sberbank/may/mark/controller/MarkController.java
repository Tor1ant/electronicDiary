package com.sberbank.may.mark.controller;

import com.sberbank.may.lesson.service.LessonService;
import com.sberbank.may.mark.model.Mark;
import com.sberbank.may.mark.service.MarkService;
import com.sberbank.may.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для управления оценками.
 * Предоставляет методы для работы с оценками студентов.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/mark")
public class MarkController {

    private final StudentService studentService;
    private final MarkService markService;
    private final LessonService lessonService;

    /**
     * Показывает форму для добавления оценки.
     *
     * @param id Идентификатор урока.
     * @param mark Оценка.
     * @param model Модель.
     * @return Страница создания оценки.
     */
    @PostMapping("/add")
    public String showForm(@RequestParam("id") long id, @ModelAttribute("mark") Mark mark, Model model) {
        model.addAttribute("students", studentService.searchAllStudentsOnLesson(id));
        model.addAttribute("lesson", lessonService.findLessonById(id));
        return "mark_pages/create_mark";
    }

    /**
     * Сохраняет оценку студента.
     *
     * @param mark Оценка студента для сохранения.
     * @return Редирект на страницу поиска уроков.
     */
    @PostMapping("/saveMark")
    public String saveStudent(@ModelAttribute("mark") Mark mark) {
        markService.saveMark(mark);
        return "redirect:/lesson/searchMyLesson";
    }

    /**
     * Показывает оценки студента.
     *
     * @param id Идентификатор урока.
     * @param model Модель.
     * @return Страница со списком оценок.
     */
    @PostMapping("/get")
    public String showStudentMarks(@RequestParam("id") long id, Model model) {
        model.addAttribute("students", markService.searchStudentsMarksOnLesson(id));
        model.addAttribute("lesson", lessonService.findLessonById(id));
        return "mark_pages/markList";
    }

    /**
     * Удаляет оценку.
     *
     * @param id Идентификатор оценки.
     * @return Редирект на страницу поиска уроков.
     */
    @PostMapping("/delete")
    public String deleteMark(@RequestParam("id") long id) {
        markService.deleteById(id);
        return "redirect:/lesson/searchMyLesson";
    }

    /**
     * Показывает форму обновления оценки.
     *
     * @param id Идентификатор оценки.
     * @param model Модель.
     * @return Страница обновления оценки.
     */
    @PostMapping("/edit")
    public String showUpdateForm(@RequestParam("id") long id, Model model) {
        Mark mark = markService.findMarkById(id);
        model.addAttribute("mark", mark);
        return "mark_pages/update_mark";
    }

    /**
     * Обновляет оценку студента.
     *
     * @param mark Обновленная оценка студента.
     * @return Редирект на страницу поиска уроков.
     */
    @PostMapping("/editMark")
    public String updateMark(@ModelAttribute("mark") Mark mark) {
        markService.patchMark(mark);
        return "redirect:/lesson/searchMyLesson";
    }
}
