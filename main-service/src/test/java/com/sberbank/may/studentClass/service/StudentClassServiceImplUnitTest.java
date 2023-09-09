package com.sberbank.may.studentClass.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sberbank.may.studentClass.dto.StudentClassDto;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.studentClass.repository.StudentClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class StudentClassServiceImplUnitTest {

    @InjectMocks
    private StudentClassServiceImpl studentClassService;

    @Mock
    private StudentClassRepository studentClassRepository;

    private StudentClass studentClass1;
    private StudentClass studentClass2;

    @BeforeEach
    void setUp() {
        studentClass1 = new StudentClass();
        studentClass1.setId(1L);
        studentClass1.setTitle("5А");

        studentClass2 = new StudentClass();
        studentClass2.setId(2L);
        studentClass2.setTitle("11Б");
    }

    @DisplayName("Проверка создания класса")
    @Test
    void testSaveClass() {
        when(studentClassRepository.save(any(StudentClass.class))).thenReturn(studentClass1);
        studentClassService.saveClass(studentClass1);
        verify(studentClassRepository, times(1)).save(any(StudentClass.class));
    }

    @DisplayName("Проверка поиска класса")
    @Test
    void testSearchClass() {
        when(studentClassRepository.findStudentClassByTitleLikeIgnoreCase(anyString()))
                .thenReturn(List.of(studentClass1));
        StudentClassDto studentClassDto = new StudentClassDto();
        studentClassDto.setTitle(studentClass1.getTitle());
        List<StudentClass> studentClasses = studentClassService.searchClass(studentClassDto);
        assertThat(studentClasses, contains(studentClass1));
        verify(studentClassRepository).findStudentClassByTitleLikeIgnoreCase(anyString());
    }

    @DisplayName("Проверка удаления класса по id")
    @Test
    void testDeleteById() {
        doNothing().when(studentClassRepository).deleteById(anyLong());
        when(studentClassRepository.findById(anyLong())).thenReturn(Optional.of(studentClass1));
        studentClassService.deleteById(1L);
        verify(studentClassRepository).deleteById(anyLong());
    }

    @DisplayName("Проверка получения всех классов")
    @Test
    void testSearchAllClass() {
        when(studentClassRepository.findAll()).thenReturn(List.of(studentClass1, studentClass2));
        List<StudentClass> allClasses = studentClassService.searchAllClass();
        assertThat(allClasses, contains(studentClass1, studentClass2));
        verify(studentClassRepository).findAll();
    }

    @DisplayName("Проверка поиска класса по id")
    @Test
    void testFindStudentClassById() {
        when(studentClassRepository.findById(anyLong())).thenReturn(Optional.of(studentClass1));
        StudentClass studentClassById = studentClassService.findStudentClassById(1L);
        assertThat(studentClass1, is(studentClassById));
        verify(studentClassRepository).findById(anyLong());
    }

    @DisplayName("Проверка обновления класса")
    @Test
    void testPatchClass() {
        when(studentClassRepository.findById(anyLong())).thenReturn(Optional.of(studentClass1));
        studentClassService.patchClass(studentClass1);
        verify(studentClassRepository).save(any());
    }
}