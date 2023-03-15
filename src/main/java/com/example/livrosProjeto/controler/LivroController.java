package com.example.livrosProjeto.controler;

import ch.qos.logback.classic.util.LevelToSyslogSeverity;
import com.example.livrosProjeto.dto.LivroDTO;
import com.example.livrosProjeto.entity.Categoria;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private List<Livro> livros = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    private List<Categoria> categorias = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    private List<Pessoa> pessoas = Collections.synchronizedList(new ArrayList<>());

    @Operation(summary = "Adiciona um novo livro a lista")
    @PostMapping
    public ResponseEntity criaLivro(@RequestBody LivroDTO novolivro) throws Exception {


        Optional<Categoria> categoriaFind = categorias.stream().filter(c -> c.getCategoriaNome()
                        .equalsIgnoreCase(novolivro.getLivro().getCategoria().getCategoriaNome())).findFirst();

        try{
            Categoria categoriaEncontrado = categoriaFind.orElseThrow(() -> new Exception(
                    "Categoria " + novolivro.getLivro().getCategoria().getCategoriaNome()+ " não encontrada"));

            Livro livro =new Livro();
            livro.setTitulo(novolivro.getLivro().getTitulo());
            livro.setAutor(novolivro.getLivro().getAutor());
            livro.setAno(novolivro.getLivro().getAno());
            livro.setCategoria(categoriaEncontrado);
            livros.add(livro);
            return ResponseEntity.ok().body(livro);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @Operation(summary = "Retorna todos os livros de uma mesma categoria")
    @GetMapping("/{categoriaNome}")
    public ResponseEntity listaLivrosCategoria(@PathVariable String categoriaNome)throws Exception{

        Optional<Categoria> categoriaFind = categorias.stream().filter(c -> c.getCategoriaNome()
                .equalsIgnoreCase(categoriaNome)).findFirst();

        try{
            Categoria categoriaEncontrado = categoriaFind.orElseThrow(() -> new Exception(
                    "Categoria " + categoriaNome + " não encontrada"));

            List<Livro> livroFind = livros.stream().filter(l -> l.getCategoria().getCategoriaNome()
                    .equalsIgnoreCase(categoriaNome)).collect(Collectors.toList());
            return ResponseEntity.ok(livroFind);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

    }

    @Operation(summary = "Retorna toda a lista de livros")
    @GetMapping
    public List<Livro> listaLivros(){
        return livros;
    }


}
