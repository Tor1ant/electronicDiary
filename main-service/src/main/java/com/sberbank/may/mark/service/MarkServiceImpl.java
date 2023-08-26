package com.sberbank.may.mark.service;

import com.sberbank.may.mark.model.Mark;
import com.sberbank.may.mark.repository.MarkRepository;
import com.sberbank.may.student.dto.StudentWithMarkOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;

    @Transactional
    @Override
    public void saveMark(Mark mark) {
        markRepository.save(mark);
    }

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
}
