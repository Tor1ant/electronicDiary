package com.sberbank.may.predmet.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "predmets")
@Data
public class Predmet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
}
