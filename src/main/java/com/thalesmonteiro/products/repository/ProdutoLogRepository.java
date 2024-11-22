package com.thalesmonteiro.products.repository;

import com.thalesmonteiro.products.entidy.ProdutoLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoLogRepository extends JpaRepository<ProdutoLog, Long> {
}
