package com.sberbank.may.studentClass.repository;

import com.sberbank.may.studentClass.model.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {
    List<StudentClass> findStudentClassByTitleLikeIgnoreCase(String title);
}
