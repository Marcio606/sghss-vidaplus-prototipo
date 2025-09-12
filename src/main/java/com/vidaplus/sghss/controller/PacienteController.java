package com.vidaplus.sghss.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    
    @GetMapping
    public ResponseEntity<String> listarPacientes() {
        return ResponseEntity.ok("Lista de pacientes");
    }
    
    @PostMapping
    public ResponseEntity<String> cadastrarPaciente(@RequestBody String paciente) {
        return ResponseEntity.ok("Paciente cadastrado com sucesso");
    }
}