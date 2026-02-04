package com.mateusneubarth.icompras.produtos.repository;

import com.mateusneubarth.icompras.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
