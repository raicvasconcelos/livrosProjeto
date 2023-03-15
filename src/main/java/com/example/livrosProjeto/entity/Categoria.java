package com.example.livrosProjeto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Categoria {

    private String categoriaNome;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Livro> livros;

}
