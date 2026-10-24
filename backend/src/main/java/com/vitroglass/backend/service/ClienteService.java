package com.vitroglass.backend.service;

import com.vitroglass.backend.model.Cliente;
import com.vitroglass.backend.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> buscarPorNombre(String nombre) {

        return clienteRepository
                .findByNombresContainingIgnoreCase(nombre);

    }

    public Cliente crearCliente(Cliente cliente) {

        cliente.setFechaRegistro(LocalDateTime.now());

        return clienteRepository.save(cliente);

    }

    public Cliente obtenerPorId(Integer id) {

        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

    }

}