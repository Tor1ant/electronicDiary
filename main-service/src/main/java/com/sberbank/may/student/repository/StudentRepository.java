package com.sberbank.may.student.repository;

import com.sberbank.may.student.model.Student;
import com.sberbank.may.studentClass.model.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы со студентами.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Находит студентов по полному или частичному совпадению имени (без учета регистра букв) и классу студента.
     *
     * @param name         имя студента (полное или частичное совпадение)
     * @param studentClass класс студента
     * @return список студентов соответствующих заданным параметрам
     */
    List<Student> findStudentByNameLikeIgnoreCaseAndStudentClass(String name, StudentClass studentClass);

    /**
     * Запрос к базе данных для поиска всех студентов, которые записаны на определенный урок.
     * Запрос использует идентификатор урока, чтобы получить идентификатор класса студентов,
     * а затем получает список всех студентов из этого класса.
     *
     * @param id идентификатор урока
     * @return список студентов, записанных на урок
     */
    @Query("Select s from Student s "
            + "where s.studentClass.id = "
            + "(select l.studentClass.id from Lesson l "
            + "where l.id = :lesson_id)"
    )
    Optional<List<Student>> searchAllStudentsOnLesson(@Param("lesson_id") long id);
}
