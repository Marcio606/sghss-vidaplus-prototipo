package com.vidaplus.sghss.service;

import com.vidaplus.sghss.model.Paciente;
import com.vidaplus.sghss.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    // CRUD básico
    public Paciente salvar(Paciente paciente) {
        log.info("Salvando paciente: {}", paciente.getNome());
        
        // Validar CPF único
        if (paciente.getId() == null) {
            if (pacienteRepository.existsByCpfAndIdNot(paciente.getCpf(), null)) {
                throw new IllegalArgumentException("CPF já cadastrado: " + paciente.getCpf());
            }
        } else {
            if (pacienteRepository.existsByCpfAndIdNot(paciente.getCpf(), paciente.getId())) {
                throw new IllegalArgumentException("CPF já cadastrado: " + paciente.getCpf());
            }
        }

        // Validar email único
        if (paciente.getId() == null) {
            if (pacienteRepository.existsByEmailAndIdNot(paciente.getEmail(), null)) {
                throw new IllegalArgumentException("Email já cadastrado: " + paciente.getEmail());
            }
        } else {
            if (pacienteRepository.existsByEmailAndIdNot(paciente.getEmail(), paciente.getId())) {
                throw new IllegalArgumentException("Email já cadastrado: " + paciente.getEmail());
            }
        }

        // Gerar número do prontuário se não existir
        if (paciente.getNumeroProntuario() == null || paciente.getNumeroProntuario().isEmpty()) {
            paciente.setNumeroProntuario(gerarNumeroProntuario());
        }

        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    public List<Paciente> buscarTodos() {
        return pacienteRepository.findByAtivoTrue();
    }

    public Page<Paciente> buscarTodos(Pageable pageable) {
        return pacienteRepository.findByAtivoTrue(pageable);
    }

    public void deletar(Long id) {
        log.info("Desativando paciente com ID: {}", id);
        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado: " + id));
        
        paciente.setAtivo(false);
        pacienteRepository.save(paciente);
    }

    // Buscas específicas
    public Optional<Paciente> buscarPorCpf(String cpf) {
        return pacienteRepository.findByCpf(cpf);
    }

    public Optional<Paciente> buscarPorEmail(String email) {
        return pacienteRepository.findByEmail(email);
    }

    public Optional<Paciente> buscarPorNumeroProntuario(String numeroProntuario) {
        return pacienteRepository.findByNumeroProntuario(numeroProntuario);
    }

    public List<Paciente> buscarPorNome(String nome) {
        return pacienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Page<Paciente> buscarPorNome(String nome, Pageable pageable) {
        return pacienteRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public List<Paciente> buscarPorCidade(String cidade) {
        return pacienteRepository.findByCidadeContainingIgnoreCase(cidade);
    }

    public List<Paciente> buscarPorUf(String uf) {
        return pacienteRepository.findByUf(uf);
    }

    public List<Paciente> buscarPorPlanoSaude(String planoSaude) {
        return pacienteRepository.findByPlanoSaudeContainingIgnoreCase(planoSaude);
    }

    public List<Paciente> buscarPorSexo(Paciente.Sexo sexo) {
        return pacienteRepository.findBySexo(sexo);
    }

    // Busca avançada
    public Page<Paciente> buscarPorMultiplosCriterios(String nome, String cpf, String email, 
                                                    Paciente.Sexo sexo, String cidade, 
                                                    String uf, Boolean ativo, Pageable pageable) {
        return pacienteRepository.findByMultiplosCriterios(nome, cpf, email, sexo, cidade, uf, ativo, pageable);
    }

    // Buscas por faixa etária
    public List<Paciente> buscarPorFaixaEtaria(int idadeMin, int idadeMax) {
        LocalDate dataFim = LocalDate.now().minusYears(idadeMin);
        LocalDate dataInicio = LocalDate.now().minusYears(idadeMax + 1);
        return pacienteRepository.findByFaixaEtaria(dataInicio, dataFim);
    }

    public List<Paciente> buscarMenoresDeIdade() {
        return buscarPorFaixaEtaria(0, 17);
    }

    public List<Paciente> buscarIdosos() {
        return buscarPorFaixaEtaria(65, 120);
    }

    // Buscas especiais
    public List<Paciente> buscarAniversariantesHoje() {
        return pacienteRepository.findPacientesAniversariantesHoje();
    }

    public List<Paciente> buscarAniversariantesDoMes() {
        return pacienteRepository.findPacientesAniversariantesDoMes();
    }

    public List<Paciente> buscarComDadosIncompletos() {
        return pacienteRepository.findPacientesComDadosIncompletos();
    }

    public List<Paciente> buscarSemConsultasRecentes(int dias) {
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(dias);
        return pacienteRepository.findPacientesSemConsultasRecentes(dataLimite);
    }

    public List<Paciente> buscarComConsultasNoPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return pacienteRepository.findPacientesComConsultasNoPeriodo(dataInicio, dataFim);
    }

    // Estatísticas
    public long contarPacientesAtivos() {
        return pacienteRepository.countPacientesAtivos();
    }

    public List<Object[]> obterEstatisticasPorSexo() {
        return pacienteRepository.countPacientesPorSexo();
    }

    public List<Object[]> obterEstatisticasPorCidade() {
        return pacienteRepository.countPacientesPorCidade();
    }

    public List<Object[]> obterEstatisticasPorUf() {
        return pacienteRepository.countPacientesPorUf();
    }

    public List<Object[]> obterEstatisticasPorFaixaEtaria() {
        return pacienteRepository.countPacientesPorFaixaEtaria();
    }

    // Validações
    public boolean existePorCpf(String cpf) {
        return pacienteRepository.findByCpf(cpf).isPresent();
    }

    public boolean existePorEmail(String email) {
        return pacienteRepository.findByEmail(email).isPresent();
    }

    public boolean existePorNumeroProntuario(String numeroProntuario) {
        return pacienteRepository.findByNumeroProntuario(numeroProntuario).isPresent();
    }

    // Utilitários
    private String gerarNumeroProntuario() {
        long totalPacientes = pacienteRepository.count();
        return String.format("P%06d", totalPacientes + 1);
    }

    public List<Paciente> buscarMaisRecentes(int limite) {
        return pacienteRepository.findPacientesMaisRecentes(
            org.springframework.data.domain.PageRequest.of(0, limite)
        );
    }

    public List<Paciente> buscarMaisAntigos(int limite) {
        return pacienteRepository.findPacientesMaisAntigos(
            org.springframework.data.domain.PageRequest.of(0, limite)
        );
    }

    // Atualização de dados
    public Paciente atualizarDadosBasicos(Long id, String nome, String telefone, String endereco) {
        Paciente paciente = buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado: " + id));
        
        if (nome != null && !nome.trim().isEmpty()) {
            paciente.setNome(nome);
        }
        if (telefone != null && !telefone.trim().isEmpty()) {
            paciente.setTelefone(telefone);
        }
        if (endereco != null && !endereco.trim().isEmpty()) {
            paciente.setEndereco(endereco);
        }
        
        return salvar(paciente);
    }

    public Paciente ativar(Long id) {
        Paciente paciente = buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado: " + id));
        
        paciente.setAtivo(true);
        return salvar(paciente);
    }

    public Paciente desativar(Long id) {
        Paciente paciente = buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado: " + id));
        
        paciente.setAtivo(false);
        return salvar(paciente);
    }

    // Busca por especialidade médica
    public List<Paciente> buscarPorEspecialidadeMedica(String especialidade) {
        return pacienteRepository.findPacientesPorEspecialidadeMedica(especialidade);
    }
}
