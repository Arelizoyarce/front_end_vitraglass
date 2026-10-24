package com.vitroglass.backend.controller;

import com.vitroglass.backend.model.Cliente;
import com.vitroglass.backend.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/buscar")
    public List<Cliente> buscarClientes(
            @RequestParam String nombre
    ) {

        return clienteService.buscarPorNombre(nombre);

    }

    @PostMapping
    public Cliente crearCliente(
            @RequestBody Cliente cliente
    ) {

        return clienteService.crearCliente(cliente);

    }

    @GetMapping("/{id}")
    public Cliente obtenerPorId(
            @PathVariable Integer id
    ) {

        return clienteService.obtenerPorId(id);

    }

}