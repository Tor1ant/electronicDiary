package com.sberbank.may.studentClass.service;

import com.sberbank.may.exception.NotFoundException;
import com.sberbank.may.studentClass.dto.StudentClassDto;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.studentClass.repository.StudentClassRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentClassServiceImpl implements StudentClassService {

    private final StudentClassRepository studentClassRepository;

    @Transactional
    @Override
    public void saveClass(StudentClass studentClass) {
        studentClassRepository.save(studentClass);
    }

    @Override
    public List<StudentClass> searchClass(StudentClassDto studentClassDto) {
        return studentClassRepository.findStudentClassByTitleLikeIgnoreCase(studentClassDto.getTitle());
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        findStudentClassById(id);
        studentClassRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<StudentClass> searchAllClass() {
        return studentClassRepository.findAll();
    }


    @Override
    public StudentClass findStudentClassById(long id) {
        return studentClassRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Класс с id=" + id + " не найден"));
    }

    @Transactional
    @Override
    public void patchClass(StudentClass studentClass) {
        StudentClass studentClassForUpdate = findStudentClassById(studentClass.getId());
        studentClassForUpdate.setTitle(studentClass.getTitle());
        studentClassRepository.save(studentClassForUpdate);
    }
}
