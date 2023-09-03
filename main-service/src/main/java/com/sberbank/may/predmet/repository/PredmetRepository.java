package com.sberbank.may.predmet.repository;

import com.sberbank.may.predmet.model.Predmet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для взаимодействия с объектами класса Predmet в базе данных.
 * Repository - это Spring аннотация, которая указывает, что класс может использоваться для доступа к данным.
 * JpaRepository - это интерфейс фреймворка Spring Data JPA, он обеспечивает функции поиска (retrieve), обновления
 * (update) и удаления (delete) для сущностей. JpaRepository<Predmet, Long> означает, что данный интерфейс будет
 * работать с объектами типа Predmet, у которых id типа Long.
 */
@Repository
public interface PredmetRepository extends JpaRepository<Predmet, Long> {

    /**
     * Метод для поиска предмета по его заголовку.
     *
     * @param title - заголовок предмета.
     * @return Опциональный (Optional) объект Predmet. Он может быть пустым, если предмет с данным заголовком не найден.
     */
    Optional<Predmet> findByTitle(String title);
}
