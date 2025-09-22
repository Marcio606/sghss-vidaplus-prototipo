package com.vidaplus.sghss.repository;

import com.vidaplus.sghss.model.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    // Busca por CPF
    Optional<Paciente> findByCpf(String cpf);

    // Busca por email
    Optional<Paciente> findByEmail(String email);

    // Busca por nome (case insensitive)
    List<Paciente> findByNomeContainingIgnoreCase(String nome);

    // Busca por nome com paginação
    Page<Paciente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    // Busca pacientes ativos
    List<Paciente> findByAtivoTrue();

    // Busca pacientes inativos
    List<Paciente> findByAtivoFalse();

    // Busca por sexo
    List<Paciente> findBySexo(Paciente.Sexo sexo);

    // Busca por cidade
    List<Paciente> findByCidadeContainingIgnoreCase(String cidade);

    // Busca por UF
    List<Paciente> findByUf(String uf);

    // Busca por plano de saúde
    List<Paciente> findByPlanoSaudeContainingIgnoreCase(String planoSaude);

    // Busca por data de nascimento
    List<Paciente> findByDataNascimento(LocalDate dataNascimento);

    // Busca por faixa etária
    @Query("SELECT p FROM Paciente p WHERE p.dataNascimento BETWEEN :dataInicio AND :dataFim")
    List<Paciente> findByFaixaEtaria(@Param("dataInicio") LocalDate dataInicio, 
                                   @Param("dataFim") LocalDate dataFim);

    // Busca por número do prontuário
    Optional<Paciente> findByNumeroProntuario(String numeroProntuario);

    // Busca pacientes com consultas em um período
    @Query("SELECT DISTINCT p FROM Paciente p JOIN p.consultas c WHERE c.dataHora BETWEEN :dataInicio AND :dataFim")
    List<Paciente> findPacientesComConsultasNoPeriodo(@Param("dataInicio") java.time.LocalDateTime dataInicio,
                                                     @Param("dataFim") java.time.LocalDateTime dataFim);

    // Busca pacientes sem consultas recentes
    @Query("SELECT p FROM Paciente p WHERE p.ativo = true AND p.id NOT IN " +
           "(SELECT DISTINCT c.paciente.id FROM Consulta c WHERE c.dataHora >= :dataLimite)")
    List<Paciente> findPacientesSemConsultasRecentes(@Param("dataLimite") java.time.LocalDateTime dataLimite);

    // Busca por múltiplos critérios
    @Query("SELECT p FROM Paciente p WHERE " +
           "(:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
           "(:cpf IS NULL OR p.cpf = :cpf) AND " +
           "(:email IS NULL OR LOWER(p.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
           "(:sexo IS NULL OR p.sexo = :sexo) AND " +
           "(:cidade IS NULL OR LOWER(p.cidade) LIKE LOWER(CONCAT('%', :cidade, '%'))) AND " +
           "(:uf IS NULL OR p.uf = :uf) AND " +
           "(:ativo IS NULL OR p.ativo = :ativo)")
    Page<Paciente> findByMultiplosCriterios(@Param("nome") String nome,
                                          @Param("cpf") String cpf,
                                          @Param("email") String email,
                                          @Param("sexo") Paciente.Sexo sexo,
                                          @Param("cidade") String cidade,
                                          @Param("uf") String uf,
                                          @Param("ativo") Boolean ativo,
                                          Pageable pageable);

    // Conta pacientes por sexo
    @Query("SELECT p.sexo, COUNT(p) FROM Paciente p WHERE p.ativo = true GROUP BY p.sexo")
    List<Object[]> countPacientesPorSexo();

    // Conta pacientes por cidade
    @Query("SELECT p.cidade, COUNT(p) FROM Paciente p WHERE p.ativo = true AND p.cidade IS NOT NULL GROUP BY p.cidade ORDER BY COUNT(p) DESC")
    List<Object[]> countPacientesPorCidade();

    // Conta pacientes por UF
    @Query("SELECT p.uf, COUNT(p) FROM Paciente p WHERE p.ativo = true AND p.uf IS NOT NULL GROUP BY p.uf ORDER BY COUNT(p) DESC")
    List<Object[]> countPacientesPorUf();

    // Conta pacientes por faixa etária
    @Query("SELECT " +
           "CASE " +
           "WHEN YEAR(CURRENT_DATE) - YEAR(p.dataNascimento) < 18 THEN '0-17' " +
           "WHEN YEAR(CURRENT_DATE) - YEAR(p.dataNascimento) BETWEEN 18 AND 30 THEN '18-30' " +
           "WHEN YEAR(CURRENT_DATE) - YEAR(p.dataNascimento) BETWEEN 31 AND 50 THEN '31-50' " +
           "WHEN YEAR(CURRENT_DATE) - YEAR(p.dataNascimento) BETWEEN 51 AND 70 THEN '51-70' " +
           "ELSE '70+' " +
           "END as faixaEtaria, COUNT(p) " +
           "FROM Paciente p WHERE p.ativo = true GROUP BY faixaEtaria ORDER BY faixaEtaria")
    List<Object[]> countPacientesPorFaixaEtaria();

    // Busca pacientes com aniversário hoje
    @Query("SELECT p FROM Paciente p WHERE " +
           "MONTH(p.dataNascimento) = MONTH(CURRENT_DATE) AND " +
           "DAY(p.dataNascimento) = DAY(CURRENT_DATE) AND " +
           "p.ativo = true")
    List<Paciente> findPacientesAniversariantesHoje();

    // Busca pacientes com aniversário no mês
    @Query("SELECT p FROM Paciente p WHERE " +
           "MONTH(p.dataNascimento) = MONTH(CURRENT_DATE) AND " +
           "p.ativo = true ORDER BY DAY(p.dataNascimento)")
    List<Paciente> findPacientesAniversariantesDoMes();

    // Verifica se CPF já existe (excluindo o próprio paciente)
    @Query("SELECT COUNT(p) > 0 FROM Paciente p WHERE p.cpf = :cpf AND (:id IS NULL OR p.id != :id)")
    boolean existsByCpfAndIdNot(@Param("cpf") String cpf, @Param("id") Long id);

    // Verifica se email já existe (excluindo o próprio paciente)
    @Query("SELECT COUNT(p) > 0 FROM Paciente p WHERE p.email = :email AND (:id IS NULL OR p.id != :id)")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);

    // Busca pacientes que precisam de atualização de dados
    @Query("SELECT p FROM Paciente p WHERE p.ativo = true AND " +
           "(p.endereco IS NULL OR p.telefone IS NULL OR p.nomeMae IS NULL)")
    List<Paciente> findPacientesComDadosIncompletos();

    // Busca pacientes por especialidade do médico
    @Query("SELECT DISTINCT p FROM Paciente p JOIN p.consultas c JOIN c.medico m WHERE m.especialidade = :especialidade")
    List<Paciente> findPacientesPorEspecialidadeMedica(@Param("especialidade") String especialidade);

    // Conta total de pacientes ativos
    @Query("SELECT COUNT(p) FROM Paciente p WHERE p.ativo = true")
    long countPacientesAtivos();

    // Busca pacientes mais antigos
    @Query("SELECT p FROM Paciente p WHERE p.ativo = true ORDER BY p.dataCadastro ASC")
    List<Paciente> findPacientesMaisAntigos(Pageable pageable);

    // Busca pacientes mais recentes
    @Query("SELECT p FROM Paciente p WHERE p.ativo = true ORDER BY p.dataCadastro DESC")
    List<Paciente> findPacientesMaisRecentes(Pageable pageable);
}
