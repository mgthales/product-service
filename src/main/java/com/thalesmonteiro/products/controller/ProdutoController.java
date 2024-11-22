package com.thalesmonteiro.products.controller;

import com.thalesmonteiro.products.entidy.Produto;
import com.thalesmonteiro.products.entidy.ProdutoLog;
import com.thalesmonteiro.products.repository.ProdutoLogRepository;
import com.thalesmonteiro.products.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoLogRepository produtoLogRepository;

    @GetMapping
    public List<Produto> getAll() {
        return produtoRepository.findAll();
    }
    @PostMapping
    public Produto adicionar(@RequestBody Produto produto) {
        Produto produtoSalvo = produtoRepository.save(produto);

        ProdutoLog log = new ProdutoLog();
        log.setProdutoId(produtoSalvo.getId());
        log.setProdutoNome(produtoSalvo.getNome());
        log.setDataHora(LocalDateTime.now());
        produtoLogRepository.save(log);

        return produtoSalvo;
    }
    @PutMapping("/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoAtualizado.getNome());
                    produto.setPreco(produtoAtualizado.getPreco());
                    return produtoRepository.save(produto);
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id) {
        produtoRepository.deleteById(id);
    }


}
