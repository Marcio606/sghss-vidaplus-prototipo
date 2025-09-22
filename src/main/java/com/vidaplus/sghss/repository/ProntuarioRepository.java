package com.vidaplus.sghss.repository;

import com.vidaplus.sghss.model.Prontuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {

    // Busca prontuários por paciente
    List<Prontuario> findByPacienteId(Long pacienteId);

    // Busca prontuários por médico
    List<Prontuario> findByMedicoId(Long medicoId);

    // Busca prontuários por consulta
    List<Prontuario> findByConsultaId(Long consultaId);

    // Busca prontuários por tipo
    List<Prontuario> findByTipoProntuario(Prontuario.TipoProntuario tipoProntuario);

    // Busca prontuários por data de criação
    List<Prontuario> findByDataCriacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    // Busca prontuários por data de criação com paginação
    Page<Prontuario> findByDataCriacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim, Pageable pageable);

    // Busca último prontuário de um paciente
    @Query("SELECT p FROM Prontuario p WHERE p.paciente.id = :pacienteId ORDER BY p.dataCriacao DESC")
    Optional<Prontuario> findUltimoProntuarioPaciente(@Param("pacienteId") Long pacienteId);

    // Busca prontuários de um paciente ordenados por data
    @Query("SELECT p FROM Prontuario p WHERE p.paciente.id = :pacienteId ORDER BY p.dataCriacao DESC")
    List<Prontuario> findProntuariosPacienteOrdenados(@Param("pacienteId") Long pacienteId);

    // Busca prontuários de um paciente com um médico específico
    @Query("SELECT p FROM Prontuario p WHERE p.paciente.id = :pacienteId AND p.medico.id = :medicoId ORDER BY p.dataCriacao DESC")
    List<Prontuario> findProntuariosPacienteComMedico(@Param("pacienteId") Long pacienteId, 
                                                    @Param("medicoId") Long medicoId);

    // Busca prontuários por múltiplos critérios
    @Query("SELECT p FROM Prontuario p WHERE " +
           "(:pacienteId IS NULL OR p.paciente.id = :pacienteId) AND " +
           "(:medicoId IS NULL OR p.medico.id = :medicoId) AND " +
           "(:tipoProntuario IS NULL OR p.tipoProntuario = :tipoProntuario) AND " +
           "(:dataInicio IS NULL OR p.dataCriacao >= :dataInicio) AND " +
           "(:dataFim IS NULL OR p.dataCriacao <= :dataFim)")
    Page<Prontuario> findByMultiplosCriterios(@Param("pacienteId") Long pacienteId,
                                            @Param("medicoId") Long medicoId,
                                            @Param("tipoProntuario") Prontuario.TipoProntuario tipoProntuario,
                                            @Param("dataInicio") LocalDateTime dataInicio,
                                            @Param("dataFim") LocalDateTime dataFim,
                                            Pageable pageable);

    // Conta prontuários por tipo
    @Query("SELECT p.tipoProntuario, COUNT(p) FROM Prontuario p GROUP BY p.tipoProntuario ORDER BY COUNT(p) DESC")
    List<Object[]> countProntuariosPorTipo();

    // Conta prontuários por médico
    @Query("SELECT p.medico.nome, COUNT(p) FROM Prontuario p GROUP BY p.medico.id, p.medico.nome ORDER BY COUNT(p) DESC")
    List<Object[]> countProntuariosPorMedico();

    // Conta prontuários por especialidade
    @Query("SELECT p.medico.especialidade, COUNT(p) FROM Prontuario p GROUP BY p.medico.especialidade ORDER BY COUNT(p) DESC")
    List<Object[]> countProntuariosPorEspecialidade();

    // Busca prontuários com diagnóstico específico
    @Query("SELECT p FROM Prontuario p WHERE LOWER(p.diagnosticoFinal) LIKE LOWER(CONCAT('%', :diagnostico, '%'))")
    List<Prontuario> findByDiagnosticoContaining(@Param("diagnostico") String diagnostico);

    // Busca prontuários com prescrição específica
    @Query("SELECT p FROM Prontuario p WHERE LOWER(p.prescricaoMedicamentos) LIKE LOWER(CONCAT('%', :medicamento, '%'))")
    List<Prontuario> findByPrescricaoContaining(@Param("medicamento") String medicamento);

    // Busca prontuários com exame solicitado
    @Query("SELECT p FROM Prontuario p WHERE LOWER(p.examesSolicitados) LIKE LOWER(CONCAT('%', :exame, '%'))")
    List<Prontuario> findByExameSolicitadoContaining(@Param("exame") String exame);

    // Busca prontuários de pacientes com febre
    @Query("SELECT p FROM Prontuario p WHERE p.temperaturaCelsius > 37.5")
    List<Prontuario> findProntuariosComFebre();

    // Busca prontuários de pacientes hipertensos
    @Query("SELECT p FROM Prontuario p WHERE (p.pressaoArterialSistolica >= 140 OR p.pressaoArterialDiastolica >= 90)")
    List<Prontuario> findProntuariosHipertensos();

    // Busca prontuários de pacientes hipotensos
    @Query("SELECT p FROM Prontuario p WHERE (p.pressaoArterialSistolica < 90 OR p.pressaoArterialDiastolica < 60)")
    List<Prontuario> findProntuariosHipotensos();

    // Busca prontuários de pacientes obesos
    @Query("SELECT p FROM Prontuario p WHERE p.pesoKg IS NOT NULL AND p.alturaCm IS NOT NULL AND " +
           "(p.pesoKg / POWER(p.alturaCm / 100.0, 2)) >= 30")
    List<Prontuario> findProntuariosObesos();

    // Busca prontuários de pacientes com IMC normal
    @Query("SELECT p FROM Prontuario p WHERE p.pesoKg IS NOT NULL AND p.alturaCm IS NOT NULL AND " +
           "(p.pesoKg / POWER(p.alturaCm / 100.0, 2)) BETWEEN 18.5 AND 24.9")
    List<Prontuario> findProntuariosIMCNormal();

    // Busca prontuários por faixa de peso
    @Query("SELECT p FROM Prontuario p WHERE p.pesoKg BETWEEN :pesoMin AND :pesoMax")
    List<Prontuario> findByPesoBetween(@Param("pesoMin") Double pesoMin, @Param("pesoMax") Double pesoMax);

    // Busca prontuários por faixa de altura
    @Query("SELECT p FROM Prontuario p WHERE p.alturaCm BETWEEN :alturaMin AND :alturaMax")
    List<Prontuario> findByAlturaBetween(@Param("alturaMin") Integer alturaMin, @Param("alturaMax") Integer alturaMax);

    // Busca prontuários por faixa de pressão arterial
    @Query("SELECT p FROM Prontuario p WHERE " +
           "(p.pressaoArterialSistolica BETWEEN :sistolicaMin AND :sistolicaMax) AND " +
           "(p.pressaoArterialDiastolica BETWEEN :diastolicaMin AND :diastolicaMax)")
    List<Prontuario> findByPressaoArterialBetween(@Param("sistolicaMin") Integer sistolicaMin,
                                                @Param("sistolicaMax") Integer sistolicaMax,
                                                @Param("diastolicaMin") Integer diastolicaMin,
                                                @Param("diastolicaMax") Integer diastolicaMax);

    // Busca prontuários por faixa de temperatura
    @Query("SELECT p FROM Prontuario p WHERE p.temperaturaCelsius BETWEEN :tempMin AND :tempMax")
    List<Prontuario> findByTemperaturaBetween(@Param("tempMin") Double tempMin, @Param("tempMax") Double tempMax);

    // Busca prontuários por faixa de frequência cardíaca
    @Query("SELECT p FROM Prontuario p WHERE p.frequenciaCardiaca BETWEEN :freqMin AND :freqMax")
    List<Prontuario> findByFrequenciaCardiacaBetween(@Param("freqMin") Integer freqMin, @Param("freqMax") Integer freqMax);

    // Busca prontuários por saturação de oxigênio
    @Query("SELECT p FROM Prontuario p WHERE p.saturacaoOxigenio < 95")
    List<Prontuario> findProntuariosSaturacaoBaixa();

    // Busca prontuários por glicemia
    @Query("SELECT p FROM Prontuario p WHERE p.glicemia > 126")
    List<Prontuario> findProntuariosGlicemiaAlta();

    // Busca prontuários por glicemia baixa
    @Query("SELECT p FROM Prontuario p WHERE p.glicemia < 70")
    List<Prontuario> findProntuariosGlicemiaBaixa();

    // Busca prontuários mais recentes
    @Query("SELECT p FROM Prontuario p ORDER BY p.dataCriacao DESC")
    List<Prontuario> findProntuariosMaisRecentes(Pageable pageable);

    // Busca prontuários mais antigos
    @Query("SELECT p FROM Prontuario p ORDER BY p.dataCriacao ASC")
    List<Prontuario> findProntuariosMaisAntigos(Pageable pageable);

    // Conta total de prontuários
    @Query("SELECT COUNT(p) FROM Prontuario p")
    long countTotalProntuarios();

    // Busca prontuários por período com estatísticas
    @Query("SELECT " +
           "COUNT(p) as total, " +
           "AVG(p.pesoKg) as pesoMedio, " +
           "AVG(p.alturaCm) as alturaMedia, " +
           "AVG(p.pressaoArterialSistolica) as pressaoSistolicaMedia, " +
           "AVG(p.pressaoArterialDiastolica) as pressaoDiastolicaMedia, " +
           "AVG(p.temperaturaCelsius) as temperaturaMedia, " +
           "AVG(p.frequenciaCardiaca) as frequenciaMedia, " +
           "AVG(p.saturacaoOxigenio) as saturacaoMedia, " +
           "AVG(p.glicemia) as glicemiaMedia " +
           "FROM Prontuario p WHERE p.dataCriacao BETWEEN :dataInicio AND :dataFim")
    Object[] calcularEstatisticasPorPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                                          @Param("dataFim") LocalDateTime dataFim);

    // Busca prontuários por paciente com paginação
    Page<Prontuario> findByPacienteId(Long pacienteId, Pageable pageable);

    // Busca prontuários por médico com paginação
    Page<Prontuario> findByMedicoId(Long medicoId, Pageable pageable);

    // Busca prontuários por tipo com paginação
    Page<Prontuario> findByTipoProntuario(Prontuario.TipoProntuario tipoProntuario, Pageable pageable);
}
