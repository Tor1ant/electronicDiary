package com.sberbank.may.mark.service;

import com.sberbank.may.exception.NotFoundException;
import com.sberbank.may.mark.model.Mark;
import com.sberbank.may.mark.repository.MarkRepository;
import com.sberbank.may.student.dto.StudentWithMarkOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервисный класс для работы с оценками студентов. Использует репозиторий для работы с данными.
 */
@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;

    /**
     * Сохраняет или обновляет оценку студента в базе данных.
     *
     * @param mark Оценка студента
     */
    @Transactional
    @Override
    public void saveMark(Mark mark) {
        markRepository.save(mark);
    }

    /**
     * Возвращает список студентов с оценками по определенному уроку.
     *
     * @param id ID урока
     * @return список студентов с оценками
     */
    @Override
    public List<StudentWithMarkOut> searchStudentsMarksOnLesson(long id) {
        return markRepository.searchStudentsMarksOnLesson(id).stream()
                .map(mark -> StudentWithMarkOut.builder()
                        .name(mark.getStudent().getName())
                        .id(mark.getId())
                        .mark(mark.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Удаляет оценку студента из базы данных по id.
     *
     * @param id ID оценки студента
     */
    @Transactional
    @Override
    public void deleteById(long id) {
        markRepository.deleteById(id);
    }

    /**
     * Находит и возвращает оценку студента по ее id.
     *
     * @param id Id оценки студента
     * @return оценку студента, если она найдена
     * @throws NotFoundException если оценка не найдена
     */
    @Override
    public Mark findMarkById(long id) {
        return markRepository.findById(id).orElseThrow(() -> new NotFoundException("Оценка с id=" + id + " не найдена"));
    }

    /**
     * Обновляет значение оценки студента.
     *
     * @param mark новые данные оценки студента
     */
    @Override
    public void patchMark(Mark mark) {
        Mark markForUpdate = findMarkById(mark.getId());
        markForUpdate.setValue(mark.getValue());
        markRepository.save(markForUpdate);
    }
}
