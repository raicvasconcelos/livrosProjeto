package com.example.livrosProjeto.controler;

import com.example.livrosProjeto.dto.LivroDTO;
import com.example.livrosProjeto.dto.PessoaDTO;
import com.example.livrosProjeto.entity.Livro;
import com.example.livrosProjeto.entity.Pessoa;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    @Autowired
    private List<Pessoa> pessoas = Collections.synchronizedList(new ArrayList<>());

    @Operation(summary = "Cria uma nova pessoa")
    @PostMapping
    public ResponseEntity criaLivro(@RequestBody PessoaDTO novaPessoa){
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(novaPessoa.getPessoa().getNome());
        pessoa.setCpf(novaPessoa.getPessoa().getCpf());
        pessoas.add(pessoa);
        return ResponseEntity.ok().body(pessoa);
    }
    @Operation(summary = "Retorna toda a lista pessoas ")
    @GetMapping
    public List<Pessoa> listapessoas(){
        return pessoas;
    }
    @Operation(summary = "Remove uma nova pessoa da lista")
    @DeleteMapping("/{pessoaNome}")
    public ResponseEntity deletaAluguel(@PathVariable String pessoaNome)throws Exception{
        Pessoa pessoaEncontrada = pessoas.stream().filter(p -> p.getNome()
                .equalsIgnoreCase(pessoaNome)).findFirst().orElseThrow(() -> new Exception(
                "Pessoa " + pessoaNome + " não encontrada"));
        pessoas.remove(pessoaEncontrada);

        return ResponseEntity.ok("Pessoa removida da lista com sucesso");
    }
}
