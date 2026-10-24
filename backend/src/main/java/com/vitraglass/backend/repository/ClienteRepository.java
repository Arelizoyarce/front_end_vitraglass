package com.vitroglass.backend.repository;

import com.vitroglass.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNombresContainingIgnoreCase(String nombres);

}