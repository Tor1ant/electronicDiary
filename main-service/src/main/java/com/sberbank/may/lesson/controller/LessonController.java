package com.sberbank.may.lesson.controller;

import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.lesson.service.LessonService;
import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.predmet.service.PredmetService;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.studentClass.service.StudentClassService;
import com.sberbank.may.user.dto.UserDto;
import com.sberbank.may.user.model.User;
import com.sberbank.may.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Класс LessonController отвечает за обработку входящих HTTP запросов, связанных с объектами Lesson (Уроки).
 * Этот класс взаимодействует с сервисами, такими как LessonService, UserService, StudentClassService и PredmetService
 * для обработки бизнес-логики.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;
    private final UserService userService;
    private final StudentClassService studentClassService;
    private final PredmetService predmetService;

    /**
     * Метод {@code showCreationForm} отображает форму создания нового урока.
     * Метод добавляет пустой объект урока, список всех учителей, классов и предметов в модель.
     *
     * @param model объект Model, используемый для добавления атрибутов.
     * @return строка, которая определяет имя вьюхи.
     */
    @GetMapping("/lessonForm")
    public String showCreationForm(Model model) {
        model.addAttribute("lesson", new Lesson());
        model.addAttribute("teachers", userService.getAllTeachers());
        model.addAttribute("classes", studentClassService.searchAllClass());
        model.addAttribute("predmets", predmetService.getAllPredmets());
        return "lesson_pages/create_lesson";
    }

    /**
     * Метод {@code saveLesson} сохраняет урок в базу данных и перенаправляет обратно на форму создания уроков.
     *
     * @param lesson объект Lesson, который должен быть сохранен.
     * @return строка, которая определяет URL для перенаправления.
     */
    @PostMapping("/saveLesson")
    public String saveLesson(@ModelAttribute("lesson") Lesson lesson) {
        lessonService.createLesson(lesson);
        return "redirect:/lesson/lessonForm";
    }

    /**
     * Метод {@code showSearchUserForm} отображает форму поиска уроков.
     *
     * @param model объект Model, используемый для добавления атрибутов.
     * @return строка, которая определяет имя вьюхи.
     */
    @GetMapping("/searchLessonForm")
    public String showSearchUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("teachers", userService.getAllTeachers());
        model.addAttribute("classes", studentClassService.searchAllClass());
        model.addAttribute("predmets", predmetService.getAllPredmets());
        return "lesson_pages/lesson_search";
    }

    /**
     * Метод searchLessons ищет уроки на основе имени преподавателя, имени класса, имени темы и времени урока.
     *
     * @param teacherName Имя преподавателя.
     * @param className Имя класса.
     * @param subjectName Имя предмета.
     * @param lessonTime Время урока.
     * @param model Модель для представления данных.
     *
     * @return Список уроков.
     */
    @GetMapping("/search")
    public String searchLessons(@RequestParam("teacherName") String teacherName,
                                @RequestParam("className") String className,
                                @RequestParam("subjectName") String subjectName,
                                @RequestParam("lessonTime") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime lessonTime,
                                Model model) {
        List<Lesson> lessons = lessonService.searchLessons(teacherName, className, subjectName, lessonTime);
        model.addAttribute("lessons", lessons);
        return "lesson_pages/lesson_list";
    }

    /**
     * Метод {@code deleteLesson} удаляет урок с указанным идентификатором из базы данных и перенаправляет обратно на форму поиска уроков.
     *
     * @param id идентификатор урока, который должен быть удален.
     * @return строка, которая определяет URL для перенаправления.
     */
    @PostMapping("/delete")
    public String deleteLesson(@RequestParam("id") long id) {
        lessonService.deleteById(id);
        return "redirect:/lesson/searchLessonForm";
    }

    /**
     * Метод {@code showUpdateForm} отображает форму для обновления урока с указанным идентификатором.
     *
     * @param id идентификатор урока, который должен быть обновлен.
     * @param model объект Model, используемый для добавления атрибутов.
     * @return строка, которая определяет имя вьюхи.
     */
    @PostMapping("/edit")
    public String showUpdateForm(@RequestParam("id") long id, Model model) {
        Lesson lesson = lessonService.findLessonById(id);
        Set<User> teachers = userService.getAllTeachers();
        List<Predmet> predmets = predmetService.getAllPredmets();
        List<StudentClass> studentClasses = studentClassService.searchAllClass();
        model.addAttribute("lesson", lesson);
        model.addAttribute("teachers", teachers);
        model.addAttribute("predmets", predmets);
        model.addAttribute("studentClasses", studentClasses);
        return "lesson_pages/update_lesson";
    }

    /**
     * Метод {@code updateLesson} обновляет урок в базе данных и перенаправляет обратно на форму поиска уроков.
     *
     * @param lesson объект Lesson, который должен быть обновлен.
     * @return строка, которая определяет URL для перенаправления.
     */
    @PostMapping("/update")
    public String updateLesson(@ModelAttribute("lesson") Lesson lesson) {
        lessonService.updateLesson(lesson);
        return "redirect:/lesson/searchLessonForm";
    }

    /**
     * Метод {@code getAllLessons} возвращает все уроки и отображает их на странице уроков.
     *
     * @param model объект Model, используемый для добавления атрибутов.
     * @return строка, которая определяет имя вьюхи.
     */
    @GetMapping("/allLessons")
    public String getAllLessons(Model model) {
        model.addAttribute("lessons", lessonService.getAllLessons());
        return "lesson_pages/lesson_list";
    }

    /**
     * Метод {@code showSearchFormForTeacher} отображает форму для поиска уроков, проводимых определенным учителем.
     * Добавляет в модель список всех учителей, классов и предметов.
     *
     * @param model объект Model, используемый для добавления атрибутов.
     * @return строка, которая определяет имя вьюхи.
     */
    @GetMapping("/searchLesson")
    public String showSearchFormForTeacher(Model model) {
        model.addAttribute("teachers", userService.getAllTeachers());
        model.addAttribute("classes", studentClassService.searchAllClass());
        model.addAttribute("predmets", predmetService.getAllPredmets());
        return "lesson_pages/lesson_search_teacher";
    }

    /**
     * Метод searchLessonsForTeacher ищет уроки для определенного преподавателя в указанный промежуток времени.
     *
     * @param teacherName Имя преподавателя.
     * @param lessonTimeFrom Начало временного интервала.
     * @param lessonTimeTo Конец временного интервала.
     * @param model Модель для представления данных.
     *
     * @return Список уроков для преподавателя.
     */
    @GetMapping("/newSearch")
    public String searchLessonsForTeacher(@RequestParam("teacherName") String teacherName,
                                          @RequestParam("className") String className,
                                          @RequestParam("subjectName") String subjectName,
                                          @RequestParam("lessonTimeFrom") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
                                          LocalDateTime lessonTimeFrom,
                                          @RequestParam("lessonTimeTo") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
                                          LocalDateTime lessonTimeTo,
                                          Model model) {

        List<Lesson> lessons = lessonService.searchLessonsForTeacher(teacherName, className, subjectName,
                lessonTimeFrom, lessonTimeTo);
        model.addAttribute("lessons", lessons);
        return "lesson_pages/lesson_list_teacher";
    }

    @GetMapping("/searchMyLesson")
    public String showSearchLessonFormForTeacher(Model model) {
        model.addAttribute("teachers", userService.getAllTeachers());
        return "lesson_pages/all_lesson_search_teacher";
    }

    /**
     * Метод searchLessonsForTeacher ищет уроки для определенного преподавателя в указанный промежуток времени.
     *
     * @param teacherName Имя преподавателя.
     * @param lessonTimeFrom Начало временного интервала.
     * @param lessonTimeTo Конец временного интервала.
     * @param model Модель для представления данных.
     *
     * @return Список уроков для преподавателя.
     */
    @GetMapping("/mySearch")
    public String searchLessonsForTeacher(@RequestParam("teacherName") String teacherName,
                                          @RequestParam("lessonTimeFrom") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
                                          LocalDateTime lessonTimeFrom,
                                          @RequestParam("lessonTimeTo") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
                                          LocalDateTime lessonTimeTo,
                                          Model model) {
        List<Lesson> lessons = lessonService.searchLessonsForTeacher(teacherName, lessonTimeFrom, lessonTimeTo);
        model.addAttribute("lessons", lessons);
        return "lesson_pages/lesson_list_teacher";
    }
}
