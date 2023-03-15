package com.example.livrosProjeto.dto;

import com.example.livrosProjeto.entity.Livro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LivroDTO {
    private Livro livro;
}
