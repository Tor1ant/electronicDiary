package com.sberbank.may.predmet.service;

import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.predmet.repository.PredmetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@code PredmetServiceImpl} класс предоставляет реализацию интерфейса {@code PredmetService}.
 * Этот класс представляет сервис уровня приложения для работы с сущностями {@code Predmet}.
 */
@Service
@RequiredArgsConstructor
public class PredmetServiceImpl implements PredmetService {

    /**
     * Репозиторий, обеспечивающий операции CRUD для сущностей {@code Predmet}.
     */
    private final PredmetRepository predmetRepository;

    /**
     * Создает новый объект {@code Predmet} или обновляет существующий
     * (если такой же объект уже существует в базе данных), сохраняя его в базу данных.
     *
     * @param predmet объект {@code Predmet} для сохранения
     */
    @Transactional
    @Override
    public void createPredmet(Predmet predmet) {
        predmetRepository.findByTitle(predmet.getTitle()).ifPresent(existingPredmet -> predmetRepository.deleteById(
                existingPredmet.getId()));
        predmetRepository.save(predmet);
    }

    /**
     * Получает список всех существующих объектов {@code Predmet} из базы данных.
     *
     * @return список всех существующих объектов {@code Predmet}
     */
    @Override
    public List<Predmet> getAllPredmets() {
        return predmetRepository.findAll();
    }

    /**
     * Удаляет объект {@code Predmet} из базы данных по его {@code id}.
     *
     * @param id идентификатор объекта {@code Predmet} для удаления
     */
    @Transactional
    @Override
    public void deleteById(long id) {
        predmetRepository.deleteById(id);
    }
}
