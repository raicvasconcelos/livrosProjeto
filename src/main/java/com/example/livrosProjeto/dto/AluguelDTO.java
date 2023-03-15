package com.example.livrosProjeto.dto;

import com.example.livrosProjeto.entity.Aluguel;
import com.example.livrosProjeto.entity.Livro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AluguelDTO {

    @NotBlank(message="O nome é obrigatório.")
    private String pessoaNome;

    private List<Livro> livroList;
}

