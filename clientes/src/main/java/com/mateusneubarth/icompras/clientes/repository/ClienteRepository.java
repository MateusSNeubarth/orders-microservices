package com.mateusneubarth.icompras.clientes.repository;

import com.mateusneubarth.icompras.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
