package com.vidaplus.sghss.service;

import com.vidaplus.sghss.model.Medico;
import com.vidaplus.sghss.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public List<Medico> buscarTodos() {
        log.info("Buscando todos os médicos");
        return medicoRepository.findAll();
    }

    public Page<Medico> buscarTodos(Pageable pageable) {
        log.info("Buscando médicos paginados: {}", pageable);
        return medicoRepository.findAll(pageable);
    }

    public Optional<Medico> buscarPorId(Long id) {
        log.info("Buscando médico por ID: {}", id);
        return medicoRepository.findById(id);
    }

    public Optional<Medico> buscarPorCrm(String crm) {
        log.info("Buscando médico por CRM: {}", crm);
        return medicoRepository.findByCrm(crm);
    }

    public List<Medico> buscarPorEspecialidade(String especialidade) {
        log.info("Buscando médicos por especialidade: {}", especialidade);
        return medicoRepository.findByEspecialidadeContainingIgnoreCase(especialidade);
    }

    public List<Medico> buscarPorNome(String nome) {
        log.info("Buscando médicos por nome: {}", nome);
        return medicoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Medico salvar(Medico medico) {
        log.info("Salvando médico: {}", medico.getNome());
        
        // Validar CRM único
        if (medico.getId() == null) {
            Optional<Medico> medicoExistente = medicoRepository.findByCrm(medico.getCrm());
            if (medicoExistente.isPresent()) {
                throw new IllegalArgumentException("CRM já cadastrado: " + medico.getCrm());
            }
        } else {
            Optional<Medico> medicoExistente = medicoRepository.findByCrm(medico.getCrm());
            if (medicoExistente.isPresent() && !medicoExistente.get().getId().equals(medico.getId())) {
                throw new IllegalArgumentException("CRM já cadastrado para outro médico: " + medico.getCrm());
            }
        }
        
        // Validar formato do CRM (exemplo: 123456)
        if (medico.getCrm() != null && !medico.getCrm().matches("\\d{4,6}")) {
            throw new IllegalArgumentException("Formato de CRM inválido. Use apenas números (4-6 dígitos)");
        }
        
        return medicoRepository.save(medico);
    }

    public void deletar(Long id) {
        log.info("Deletando médico com ID: {}", id);
        
        if (!medicoRepository.existsById(id)) {
            throw new IllegalArgumentException("Médico não encontrado com ID: " + id);
        }
        
        medicoRepository.deleteById(id);
    }

    public List<Object[]> obterEstatisticasPorEspecialidade() {
        log.info("Obtendo estatísticas por especialidade");
        return medicoRepository.contarPorEspecialidade();
    }

    public long contarMedicosAtivos() {
        log.info("Contando médicos ativos");
        return medicoRepository.count();
    }

    public List<Medico> buscarMedicosRecentes() {
        log.info("Buscando médicos contratados recentemente");
        return medicoRepository.findTop10ByOrderByDataContratacaoDesc();
    }
}
