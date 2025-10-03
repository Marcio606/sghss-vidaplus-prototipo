package com.vidaplus.sghss.controller;

import com.vidaplus.sghss.model.Paciente;
import com.vidaplus.sghss.service.PacienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        try {
            List<Paciente> pacientes = pacienteService.buscarTodos();
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            log.error("Erro ao listar pacientes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Paciente>> listarPacientesPaginado(Pageable pageable) {
        try {
            Page<Paciente> pacientes = pacienteService.buscarTodos(pageable);
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            log.error("Erro ao listar pacientes paginado", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Long id) {
        try {
            Optional<Paciente> paciente = pacienteService.buscarPorId(id);
            return paciente.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Erro ao buscar paciente por ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Paciente> buscarPacientePorCpf(@PathVariable String cpf) {
        try {
            Optional<Paciente> paciente = pacienteService.buscarPorCpf(cpf);
            return paciente.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Erro ao buscar paciente por CPF: {}", cpf, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Paciente> buscarPacientePorEmail(@PathVariable String email) {
        try {
            Optional<Paciente> paciente = pacienteService.buscarPorEmail(email);
            return paciente.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Erro ao buscar paciente por email: {}", email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Paciente>> buscarPacientesPorNome(@RequestParam String nome) {
        try {
            List<Paciente> pacientes = pacienteService.buscarPorNome(nome);
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            log.error("Erro ao buscar pacientes por nome: {}", nome, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Paciente> cadastrarPaciente(@Valid @RequestBody Paciente paciente) {
        try {
            Paciente pacienteSalvo = pacienteService.salvar(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteSalvo);
        } catch (IllegalArgumentException e) {
            log.warn("Erro de validação ao cadastrar paciente: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Erro ao cadastrar paciente", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizarPaciente(@PathVariable Long id, 
                                                    @Valid @RequestBody Paciente paciente) {
        try {
            if (!pacienteService.buscarPorId(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            paciente.setId(id);
            Paciente pacienteAtualizado = pacienteService.salvar(paciente);
            return ResponseEntity.ok(pacienteAtualizado);
        } catch (IllegalArgumentException e) {
            log.warn("Erro de validação ao atualizar paciente: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Erro ao atualizar paciente com ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable Long id) {
        try {
            if (!pacienteService.buscarPorId(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            pacienteService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao deletar paciente com ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/aniversariantes/hoje")
    public ResponseEntity<List<Paciente>> buscarAniversariantesHoje() {
        try {
            List<Paciente> aniversariantes = pacienteService.buscarAniversariantesHoje();
            return ResponseEntity.ok(aniversariantes);
        } catch (Exception e) {
            log.error("Erro ao buscar aniversariantes de hoje", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/aniversariantes/mes")
    public ResponseEntity<List<Paciente>> buscarAniversariantesDoMes() {
        try {
            List<Paciente> aniversariantes = pacienteService.buscarAniversariantesDoMes();
            return ResponseEntity.ok(aniversariantes);
        } catch (Exception e) {
            log.error("Erro ao buscar aniversariantes do mês", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/estatisticas/sexo")
    public ResponseEntity<List<Object[]>> obterEstatisticasPorSexo() {
        try {
            List<Object[]> estatisticas = pacienteService.obterEstatisticasPorSexo();
            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            log.error("Erro ao obter estatísticas por sexo", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/estatisticas/cidade")
    public ResponseEntity<List<Object[]>> obterEstatisticasPorCidade() {
        try {
            List<Object[]> estatisticas = pacienteService.obterEstatisticasPorCidade();
            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            log.error("Erro ao obter estatísticas por cidade", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/contar")
    public ResponseEntity<Long> contarPacientesAtivos() {
        try {
            long total = pacienteService.contarPacientesAtivos();
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            log.error("Erro ao contar pacientes ativos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}