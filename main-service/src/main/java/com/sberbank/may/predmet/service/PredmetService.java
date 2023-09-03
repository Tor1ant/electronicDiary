package com.sberbank.may.predmet.service;

import com.sberbank.may.predmet.model.Predmet;
import java.util.List;

/**
 * Интерфейс, представляющий сервис для работы с предметами.
 */
public interface PredmetService {

    /**
     * Создает новый предмет.
     *
     * @param predmet Объект предмета, который нужно создать.
     */
    void createPredmet(Predmet predmet);

    /**
     * Получает список всех предметов.
     *
     * @return Список всех предметов.
     */
    List<Predmet> getAllPredmets();

    /**
     * Удаляет предмет по указанному идентификатору.
     *
     * @param id Идентификатор предмета, который нужно удалить.
     */
    void deleteById(long id);
}
