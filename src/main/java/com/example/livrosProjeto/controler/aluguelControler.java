package com.example.livrosProjeto.controler;

import com.example.livrosProjeto.dto.AluguelDTO;
import com.example.livrosProjeto.entity.Aluguel;
import com.example.livrosProjeto.entity.Categoria;
import com.example.livrosProjeto.entity.Livro;
import com.example.livrosProjeto.entity.Pessoa;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/aluguel")
public class aluguelControler {

    @Autowired
    private List<Livro> livros = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    private List<Categoria> categorias = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    private List<Pessoa> pessoas = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    private List<Aluguel> alugueis = Collections.synchronizedList(new ArrayList<>());

    @Operation(summary = "Cria um novo aluguel")
    @PostMapping
    public ResponseEntity criaAluguel(@RequestBody AluguelDTO novoAluguel) throws Exception {

        Pessoa pessoaEncontrada = pessoas.stream().filter(p -> p.getNome()
                .equalsIgnoreCase(novoAluguel.getPessoaNome()))
                .findFirst().orElseThrow(() -> new Exception("Pessoa não encontrada"));

        boolean aluguelEncontrado = alugueis.stream().anyMatch(al -> al.getPessoa().
                getNome().equalsIgnoreCase(novoAluguel.getPessoaNome()));

        if(aluguelEncontrado){
            return ResponseEntity.badRequest().body("Já existe um aluguel em aberto para essa pessoa");
        }

        List<Livro> listalivrosEncontrados = new ArrayList<>();

        for (Livro livroNovos : novoAluguel.getLivroList()) {
            for (Livro livroLista : livros) {
                if (livroNovos.getTitulo().equalsIgnoreCase(livroLista.getTitulo())) {
                    listalivrosEncontrados.add(livroLista);
                    break;
                }
            }
        }

        boolean todosLivrosEncontrados = listalivrosEncontrados.size() == novoAluguel.getLivroList().size();

        if(todosLivrosEncontrados){

         Aluguel aluguel = new Aluguel();
         aluguel.setPessoa(pessoaEncontrada);
         aluguel.setLivros(listalivrosEncontrados);
         aluguel.setDataAluguel(LocalDate.now());
         aluguel.setDataEntrega(LocalDate.now().plusDays(5));

         alugueis.add(aluguel);

         return ResponseEntity.ok().body(aluguel);
        }else{
         return ResponseEntity.badRequest().body("Um ou mais dos livros solicitados não foram encontrados na lista");
     }

    }
    @Operation(summary = "Encontra aluguel registrado no nome da pessoa")
    @GetMapping("/{pessoaNome}")
    public ResponseEntity listaLivrosCategoria(@PathVariable String pessoaNome)throws Exception{
        Optional<Pessoa> pessoaFind = pessoas.stream().filter(p -> p.getNome()
                .equalsIgnoreCase(pessoaNome)).findFirst();

        try{
            Pessoa pessoaEncontrada = pessoaFind.orElseThrow(() -> new Exception(
                    "Pessoa " + pessoaNome + " não encontrada"));

            List<Aluguel> aluguelFind = alugueis.stream().filter(aluguel -> aluguel.getPessoa().getNome()
                    .equalsIgnoreCase(pessoaEncontrada.getNome())).collect(Collectors.toList());
            return ResponseEntity.ok(aluguelFind);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @Operation(summary = "Retorna a lista de alugueis")
    @GetMapping
    public List<Aluguel> encontraAlugueis(){
        return alugueis;
    }

    @Operation(summary = "Remove da lista o aluguel registrado no nome de uma pessoa")
    @DeleteMapping("/{pessoaNome}")
    public ResponseEntity deletaAluguel(@PathVariable String pessoaNome)throws Exception{
        Optional<Pessoa> pessoaFind = pessoas.stream().filter(p -> p.getNome()
                .equalsIgnoreCase(pessoaNome)).findFirst();


        try{
            Pessoa pessoaEncontrada = pessoaFind.orElseThrow(() -> new Exception(
                    "Pessoa " + pessoaNome + " não encontrada"));

           Aluguel aluguelFind = alugueis.stream().filter(aluguel -> aluguel.getPessoa().getNome()
                           .equalsIgnoreCase(pessoaEncontrada.getNome())).
                   findFirst().
                   orElseThrow(() -> new Exception("Aluguel não encontrado"));


            alugueis.remove(aluguelFind);

            List<Aluguel> aluguelRestante = alugueis.stream().filter(aluguel -> aluguel.getPessoa().getNome()
                    .equalsIgnoreCase(pessoaEncontrada.getNome())).collect(Collectors.toList());

            return ResponseEntity.ok(aluguelRestante);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }





}
