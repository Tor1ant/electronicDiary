package com.sberbank.may.student.repository;

import com.sberbank.may.student.dto.StudentWithMarkOut;
import com.sberbank.may.student.model.Student;
import com.sberbank.may.studentClass.model.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findStudentByNameLikeIgnoreCaseAndStudentClass(String name, StudentClass studentClass);

    @Query("Select s from Student s "
            + "where s.studentClass.id = "
            + "(select l.studentClass.id from Lesson l "
            + "where l.id = :lesson_id)"
    )
    Optional<List<Student>> searchAllStudentsOnLesson(@Param("lesson_id") long id);
}
