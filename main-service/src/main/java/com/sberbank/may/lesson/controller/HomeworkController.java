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

@Controller
@RequiredArgsConstructor
@RequestMapping("/homework")
public class HomeworkController {

    private final LessonService lessonService;
    @PostMapping("/edit")
    public String showUpdateForm(@RequestParam("id") long id, Model model) {
        Lesson lesson = lessonService.findLessonById(id);
        model.addAttribute("lesson", lesson);
        return "lesson_pages/update_homework";
    }

    @PostMapping("/update")
    public String updateHomework(@ModelAttribute("lesson") Lesson lesson) {
        lessonService.updateLesson(lesson);
        return "redirect:/lesson/searchLesson";
    }
}
