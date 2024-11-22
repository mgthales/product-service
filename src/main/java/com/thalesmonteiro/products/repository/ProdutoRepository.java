package com.thalesmonteiro.products.repository;

import com.thalesmonteiro.products.entidy.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
