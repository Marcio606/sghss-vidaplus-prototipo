package com.vidaplus.sghss.repository;

import com.vidaplus.sghss.model.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    // Busca por CRM
    Optional<Medico> findByCrmAndCrmUf(String crm, String crmUf);

    // Busca por email
    Optional<Medico> findByEmail(String email);

    // Busca por nome (case insensitive)
    List<Medico> findByNomeContainingIgnoreCase(String nome);

    // Busca por especialidade
    List<Medico> findByEspecialidadeContainingIgnoreCase(String especialidade);

    // Busca médicos ativos
    List<Medico> findByAtivoTrue();

    // Busca médicos inativos
    List<Medico> findByAtivoFalse();

    // Busca por especialidade secundária
    List<Medico> findByEspecialidadeSecundariaContainingIgnoreCase(String especialidadeSecundaria);

    // Busca médicos com agenda disponível
    @Query("SELECT DISTINCT m FROM Medico m JOIN m.agendas a WHERE a.ativo = true AND m.ativo = true")
    List<Medico> findMedicosComAgendaDisponivel();

    // Busca médicos por especialidade com agenda
    @Query("SELECT DISTINCT m FROM Medico m JOIN m.agendas a WHERE m.especialidade = :especialidade AND a.ativo = true AND m.ativo = true")
    List<Medico> findMedicosPorEspecialidadeComAgenda(@Param("especialidade") String especialidade);

    // Conta médicos por especialidade
    @Query("SELECT m.especialidade, COUNT(m) FROM Medico m WHERE m.ativo = true GROUP BY m.especialidade ORDER BY COUNT(m) DESC")
    List<Object[]> countMedicosPorEspecialidade();

    // Busca médicos com mais consultas
    @Query("SELECT m, COUNT(c) as totalConsultas FROM Medico m LEFT JOIN m.consultas c WHERE m.ativo = true GROUP BY m ORDER BY totalConsultas DESC")
    List<Object[]> findMedicosComMaisConsultas(Pageable pageable);

    // Busca médicos sem consultas recentes
    @Query("SELECT m FROM Medico m WHERE m.ativo = true AND m.id NOT IN " +
           "(SELECT DISTINCT c.medico.id FROM Consulta c WHERE c.dataHora >= :dataLimite)")
    List<Medico> findMedicosSemConsultasRecentes(@Param("dataLimite") java.time.LocalDateTime dataLimite);

    // Busca por múltiplos critérios
    @Query("SELECT m FROM Medico m WHERE " +
           "(:nome IS NULL OR LOWER(m.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
           "(:especialidade IS NULL OR LOWER(m.especialidade) LIKE LOWER(CONCAT('%', :especialidade, '%'))) AND " +
           "(:crm IS NULL OR m.crm = :crm) AND " +
           "(:crmUf IS NULL OR m.crmUf = :crmUf) AND " +
           "(:ativo IS NULL OR m.ativo = :ativo)")
    Page<Medico> findByMultiplosCriterios(@Param("nome") String nome,
                                        @Param("especialidade") String especialidade,
                                        @Param("crm") String crm,
                                        @Param("crmUf") String crmUf,
                                        @Param("ativo") Boolean ativo,
                                        Pageable pageable);

    // Verifica se CRM já existe
    boolean existsByCrmAndCrmUf(String crm, String crmUf);

    // Verifica se CRM já existe (excluindo o próprio médico)
    @Query("SELECT COUNT(m) > 0 FROM Medico m WHERE m.crm = :crm AND m.crmUf = :crmUf AND (:id IS NULL OR m.id != :id)")
    boolean existsByCrmAndCrmUfAndIdNot(@Param("crm") String crm, @Param("crmUf") String crmUf, @Param("id") Long id);

    // Verifica se email já existe (excluindo o próprio médico)
    @Query("SELECT COUNT(m) > 0 FROM Medico m WHERE m.email = :email AND (:id IS NULL OR m.id != :id)")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);

    // Busca médicos com maior valor de consulta
    @Query("SELECT m FROM Medico m WHERE m.ativo = true ORDER BY m.valorConsulta DESC")
    List<Medico> findMedicosComMaiorValorConsulta(Pageable pageable);

    // Busca médicos com menor valor de consulta
    @Query("SELECT m FROM Medico m WHERE m.ativo = true AND m.valorConsulta IS NOT NULL ORDER BY m.valorConsulta ASC")
    List<Medico> findMedicosComMenorValorConsulta(Pageable pageable);

    // Busca médicos por instituição de formação
    List<Medico> findByInstituicaoFormacaoContainingIgnoreCase(String instituicaoFormacao);

    // Busca médicos por ano de formação
    List<Medico> findByDataFormacao(Integer dataFormacao);

    // Busca médicos por faixa de valor de consulta
    @Query("SELECT m FROM Medico m WHERE m.ativo = true AND m.valorConsulta BETWEEN :valorMin AND :valorMax ORDER BY m.valorConsulta")
    List<Medico> findByValorConsultaBetween(@Param("valorMin") Double valorMin, @Param("valorMax") Double valorMax);

    // Conta total de médicos ativos
    @Query("SELECT COUNT(m) FROM Medico m WHERE m.ativo = true")
    long countMedicosAtivos();

    // Busca médicos que precisam de atualização de dados
    @Query("SELECT m FROM Medico m WHERE m.ativo = true AND " +
           "(m.instituicaoFormacao IS NULL OR m.dataFormacao IS NULL OR m.valorConsulta IS NULL)")
    List<Medico> findMedicosComDadosIncompletos();

    // Busca médicos mais antigos
    @Query("SELECT m FROM Medico m WHERE m.ativo = true ORDER BY m.dataCadastro ASC")
    List<Medico> findMedicosMaisAntigos(Pageable pageable);

    // Busca médicos mais recentes
    @Query("SELECT m FROM Medico m WHERE m.ativo = true ORDER BY m.dataCadastro DESC")
    List<Medico> findMedicosMaisRecentes(Pageable pageable);

    // Busca médicos por especialidade com paginação
    Page<Medico> findByEspecialidadeContainingIgnoreCase(String especialidade, Pageable pageable);

    // Busca médicos com tempo de consulta específico
    List<Medico> findByTempoConsultaMinutos(Integer tempoConsultaMinutos);

    // Busca médicos por faixa de tempo de consulta
    @Query("SELECT m FROM Medico m WHERE m.ativo = true AND m.tempoConsultaMinutos BETWEEN :tempoMin AND :tempoMax")
    List<Medico> findByTempoConsultaMinutosBetween(@Param("tempoMin") Integer tempoMin, @Param("tempoMax") Integer tempoMax);
}
