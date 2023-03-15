package com.example.livrosProjeto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Livro {

    private String titulo;
    private String autor;
    private int ano;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Categoria categoria;

}


