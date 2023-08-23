package com.sberbank.may.student.service;

import com.sberbank.may.exception.NotFoundException;
import com.sberbank.may.student.dto.StudentDto;
import com.sberbank.may.student.model.Student;
import com.sberbank.may.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public List<Student> searchStudent(StudentDto studentDto) {
        return studentRepository.findStudentByNameLikeIgnoreCaseAndStudentClass(studentDto.getName(),
                studentDto.getStudentClass());
    }

    @Transactional
    @Override
    public List<Student> searchAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional
    @Override
    public Student findStudentById(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ученик с id=" + id + " не найден"));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        studentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void patchStudent(Student student) {
        Student studentForUpdate = findStudentById(student.getId());
        studentForUpdate.setName(student.getName());
        studentForUpdate.setStudentClass(student.getStudentClass());
        studentForUpdate.setUser(student.getUser());
        studentRepository.save(studentForUpdate);
    }

    @Override
    public List<Student> searchAllStudentsOnLesson(long id) {
        return studentRepository.searchAllStudentsOnLesson(id)
                .orElseThrow(() -> new NotFoundException("Ученики с id урока = " + id + " не найдены"));
    }
}

