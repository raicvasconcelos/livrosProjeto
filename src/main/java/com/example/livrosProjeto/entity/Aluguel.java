package com.example.livrosProjeto.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Aluguel {

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Pessoa pessoa;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Livro> livros;
    private LocalDate dataAluguel;
    private LocalDate dataEntrega;


}
