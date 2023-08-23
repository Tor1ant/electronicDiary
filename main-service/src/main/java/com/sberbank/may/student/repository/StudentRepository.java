package com.sberbank.may.student.repository;

import com.sberbank.may.student.model.Student;
import com.sberbank.may.studentClass.model.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findStudentByNameLikeIgnoreCaseAndStudentClass(String name, StudentClass studentClass);
}
