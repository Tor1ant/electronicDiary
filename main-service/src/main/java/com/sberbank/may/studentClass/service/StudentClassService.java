package com.sberbank.may.studentClass.service;

import com.sberbank.may.studentClass.dto.StudentClassDto;
import com.sberbank.may.studentClass.model.StudentClass;

import java.util.List;

/**
 * Интерфейс StudentClassService представляет сервис для работы с классами студентов.
 */
public interface StudentClassService {

    /**
     * Сохраняет информацию о классе студента.
     *
     * @param studentClass класс студента, который необходимо сохранить
     */
    void saveClass(StudentClass studentClass);

    /**
     * Ищет классы студентов по заданным параметрам.
     *
     * @param studentClassDto объект с параметрами поиска
     * @return список классов студентов, соответствующих указанным параметрам
     */
    List<StudentClass> searchClass(StudentClassDto studentClassDto);

    /**
     * Удаляет класс студента по заданному идентификатору.
     *
     * @param id идентификатор класса студента, который необходимо удалить
     */
    void deleteById(long id);

    /**
     * Получает все классы студентов.
     *
     * @return список всех классов студентов
     */
    List<StudentClass> searchAllClass();

    /**
     * Находит класс студента по заданному идентификатору.
     *
     * @param id идентификатор класса студента
     * @return объект класса студента
     */
    StudentClass findStudentClassById(long id);

    /**
     * Обновляет информацию о классе студента.
     *
     * @param studentClass объект класса студента с обновленной информацией
     */
    void patchClass(StudentClass studentClass);
}
