package com.sberbank.may.predmet.controller;

import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.predmet.service.PredmetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер, обрабатывающий запросы, связанные с Предметом.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/predmet")
public class PredmetController {

    /**
     * Сервис предметов, необходимый для выполнения бизнес-логики.
     */
    private final PredmetService predmetService;

    /**
     * Обрабатывает GET-запросы на "/predmetForm".
     *
     * @param model модель данных Spring
     * @return строка, указывающая на соответствующую html-страницу
     */
    @GetMapping("/predmetForm")
    public String showCreationForm(Model model) {
        model.addAttribute("predmet", new Predmet());
        return "predmet_pages/create_predmet";
    }

    /**
     * Обрабатывает POST-запросы на "/savePredmet".
     *
     * @param predmet объект предмета, полученный из формы
     * @return строка, указывающая на перенаправление после сохранения предмета
     */
    @PostMapping("/savePredmet")
    public String savePredmet(@ModelAttribute("predmet") Predmet predmet) {
        predmetService.createPredmet(predmet);
        return "redirect:/predmet/allPredmets";
    }

    /**
     * Обрабатывает GET-запросы на "/allPredmets".
     *
     * @param model модель данных Spring
     * @return строка, указывающая на страницу со списком всех предметов
     */
    @GetMapping("/allPredmets")
    public String getAllLessons(Model model) {
        model.addAttribute("predmets", predmetService.getAllPredmets());
        return "predmet_pages/predmet_list";
    }

    /**
     * Обрабатывает POST-запросы на "/delete".
     *
     * @param id идентификатор удаляемого предмета
     * @return строка, указывающая на перенаправление после удаления предмета
     */
    @PostMapping("/delete")
    public String deleteLesson(@RequestParam("id") long id) {
        predmetService.deleteById(id);
        return "redirect:/predmet/allPredmets";
    }
}
