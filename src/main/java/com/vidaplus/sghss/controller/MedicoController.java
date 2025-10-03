package com.vidaplus.sghss.controller;

import com.vidaplus.sghss.model.Medico;
import com.vidaplus.sghss.service.MedicoService;
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
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class MedicoController {

    private final MedicoService medicoService;

    @GetMapping
    public ResponseEntity<List<Medico>> listarMedicos() {
        try {
            List<Medico> medicos = medicoService.buscarTodos();
            return ResponseEntity.ok(medicos);
        } catch (Exception e) {
            log.error("Erro ao listar médicos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Medico>> listarMedicosPaginado(Pageable pageable) {
        try {
            Page<Medico> medicos = medicoService.buscarTodos(pageable);
            return ResponseEntity.ok(medicos);
        } catch (Exception e) {
            log.error("Erro ao listar médicos paginado", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> buscarMedicoPorId(@PathVariable Long id) {
        try {
            Optional<Medico> medico = medicoService.buscarPorId(id);
            return medico.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Erro ao buscar médico por ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/crm/{crm}")
    public ResponseEntity<Medico> buscarMedicoPorCrm(@PathVariable String crm) {
        try {
            Optional<Medico> medico = medicoService.buscarPorCrm(crm);
            return medico.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Erro ao buscar médico por CRM: {}", crm, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/especialidade/{especialidade}")
    public ResponseEntity<List<Medico>> buscarMedicosPorEspecialidade(@PathVariable String especialidade) {
        try {
            List<Medico> medicos = medicoService.buscarPorEspecialidade(especialidade);
            return ResponseEntity.ok(medicos);
        } catch (Exception e) {
            log.error("Erro ao buscar médicos por especialidade: {}", especialidade, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Medico>> buscarMedicosPorNome(@RequestParam String nome) {
        try {
            List<Medico> medicos = medicoService.buscarPorNome(nome);
            return ResponseEntity.ok(medicos);
        } catch (Exception e) {
            log.error("Erro ao buscar médicos por nome: {}", nome, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Medico> cadastrarMedico(@Valid @RequestBody Medico medico) {
        try {
            Medico medicoSalvo = medicoService.salvar(medico);
            return ResponseEntity.status(HttpStatus.CREATED).body(medicoSalvo);
        } catch (IllegalArgumentException e) {
            log.warn("Erro de validação ao cadastrar médico: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Erro ao cadastrar médico", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> atualizarMedico(@PathVariable Long id, 
                                                 @Valid @RequestBody Medico medico) {
        try {
            if (!medicoService.buscarPorId(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            medico.setId(id);
            Medico medicoAtualizado = medicoService.salvar(medico);
            return ResponseEntity.ok(medicoAtualizado);
        } catch (IllegalArgumentException e) {
            log.warn("Erro de validação ao atualizar médico: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Erro ao atualizar médico com ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMedico(@PathVariable Long id) {
        try {
            if (!medicoService.buscarPorId(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            medicoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao deletar médico com ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/estatisticas/especialidade")
    public ResponseEntity<List<Object[]>> obterEstatisticasPorEspecialidade() {
        try {
            List<Object[]> estatisticas = medicoService.obterEstatisticasPorEspecialidade();
            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            log.error("Erro ao obter estatísticas por especialidade", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/contar")
    public ResponseEntity<Long> contarMedicosAtivos() {
        try {
            long total = medicoService.contarMedicosAtivos();
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            log.error("Erro ao contar médicos ativos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
