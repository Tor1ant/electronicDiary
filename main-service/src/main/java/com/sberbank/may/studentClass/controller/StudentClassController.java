package com.sberbank.may.studentClass.controller;

import com.sberbank.may.studentClass.dto.StudentClassDto;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.studentClass.service.StudentClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления классами студентов.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/class")
public class StudentClassController {

    private final StudentClassService studentClassService;

    /**
     * Сохраняет класс студентов.
     *
     * @param studentClass класс студентов для сохранения.
     * @return перенаправляет на форму класса.
     */
    @PostMapping("/saveClass")
    public String saveClass(@ModelAttribute("studentClass") StudentClass studentClass) {
        studentClassService.saveClass(studentClass);
        return "redirect:/class/classForm";
    }

    /**
     * Показывает форму создания класса студентов.
     *
     * @param studentClass класс студентов.
     * @return возвращает вид create_class.
     */
    @GetMapping("/classForm")
    public String showClassForm(@ModelAttribute("studentClass") StudentClass studentClass) {
        return "class_pages/create_class";
    }

    /**
     * Показывает форму поиска класса студентов.
     *
     * @param studentClassDto ДТО класса студентов.
     * @return возвращает вид classSearch.
     */
    @GetMapping("/searchClassForm")
    public String showSearchClassForm(@ModelAttribute("class") StudentClassDto studentClassDto) {
        return "class_pages/classSearch";
    }

    /**
     * Ищет классы студентов.
     *
     * @param studentClassDto ДТО класса студентов.
     * @param model модель в MVC.
     * @return возвращает вид classList.
     */
    @GetMapping("/searchClass")
    public String searchClass(@ModelAttribute("class") StudentClassDto studentClassDto, Model model) {
        List<StudentClass> studentClasses = studentClassService.searchClass(studentClassDto);
        model.addAttribute("studentClasses", studentClasses);
        return "class_pages/classList";
    }

    /**
     * Удаляет класс студентов по его идентификатору.
     *
     * @param id идентификатор класса студентов для удаления.
     * @return перенаправляет на страницу со всеми классами.
     */
    @PostMapping("/delete")
    public String deleteClass(@RequestParam("id") long id) {
        studentClassService.deleteById(id);
        return "redirect:/class/allClasses";
    }

    /**
     * Ищет все классы студентов.
     *
     * @param model модель в MVC.
     * @return возвращает вид classList.
     */
    @GetMapping("/allClasses")
    public String searchAllClass(Model model) {
        List<StudentClass> studentClasses = studentClassService.searchAllClass();
        model.addAttribute("studentClasses", studentClasses);
        return "class_pages/classList";
    }

    /**
     * Показывает форму для обновления класса студента.
     *
     * @param id идентификатор класса студента для обновления.
     * @param model модель в MVC.
     * @return возвращает вид update_class.
     */
    @PostMapping("/edit")
    public String showUpdateForm(@RequestParam("id") long id, Model model) {
        StudentClass studentClass = studentClassService.findStudentClassById(id);
        model.addAttribute("studentClass", studentClass);
        return "class_pages/update_class";
    }

    /**
     * Обновляет класс студента.
     *
     * @param studentClass класс студента для обновления.
     * @return перенаправляет на страницу со всеми классами.
     */
    @PostMapping("/editClass")
    public String updateUser(@ModelAttribute("studentClass") StudentClass studentClass) {
        studentClassService.patchClass(studentClass);
        return "redirect:/class/allClasses";
    }
}
