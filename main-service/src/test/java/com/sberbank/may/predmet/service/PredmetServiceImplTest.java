package com.sberbank.may.predmet.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.predmet.repository.PredmetRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PredmetServiceImplTest {

    @InjectMocks
    private PredmetServiceImpl predmetService;

    @Mock
    private PredmetRepository predmetRepository;

    private Predmet predmet;

    @BeforeEach
    void setUp() {
        predmet = new Predmet();
        predmet.setTitle("Алгебра");
    }

    @DisplayName("Проверка создания предмета")
    @Test
    void createPredmet() {
        when(predmetRepository.save(any())).thenReturn(predmet);
        predmetService.createPredmet(predmet);
        verify(predmetRepository).save(any());
    }

    @DisplayName("Проверка получения всех предметов")
    @Test
    void getAllPredmets() {
        when(predmetRepository.findAll()).thenReturn(List.of(predmet));
        List<Predmet> allPredmets = predmetService.getAllPredmets();
        assertThat(allPredmets, contains(predmet));
        verify(predmetRepository).findAll();
    }

    @DisplayName("Проверка удаления предмета по id")
    @Test
    void deleteById() {
        Mockito.doNothing().when(predmetRepository).deleteById(anyLong());
        predmetService.deleteById(1L);
        verify(predmetRepository).deleteById(anyLong());
    }
}