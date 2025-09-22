package com.vidaplus.sghss.repository;

import com.vidaplus.sghss.model.Consulta;
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
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // Busca consultas por paciente
    List<Consulta> findByPacienteId(Long pacienteId);

    // Busca consultas por médico
    List<Consulta> findByMedicoId(Long medicoId);

    // Busca consultas por status
    List<Consulta> findByStatus(Consulta.StatusConsulta status);

    // Busca consultas por tipo
    List<Consulta> findByTipoConsulta(Consulta.TipoConsulta tipoConsulta);

    // Busca consultas por data
    List<Consulta> findByDataHoraBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    // Busca consultas por data com paginação
    Page<Consulta> findByDataHoraBetween(LocalDateTime dataInicio, LocalDateTime dataFim, Pageable pageable);

    // Busca consultas por paciente e status
    List<Consulta> findByPacienteIdAndStatus(Long pacienteId, Consulta.StatusConsulta status);

    // Busca consultas por médico e status
    List<Consulta> findByMedicoIdAndStatus(Long medicoId, Consulta.StatusConsulta status);

    // Busca consultas agendadas para hoje
    @Query("SELECT c FROM Consulta c WHERE DATE(c.dataHora) = CURRENT_DATE AND c.status IN ('AGENDADA', 'CONFIRMADA') ORDER BY c.dataHora")
    List<Consulta> findConsultasHoje();

    // Busca consultas agendadas para um dia específico
    @Query("SELECT c FROM Consulta c WHERE DATE(c.dataHora) = :data AND c.status IN ('AGENDADA', 'CONFIRMADA') ORDER BY c.dataHora")
    List<Consulta> findConsultasPorDia(@Param("data") java.time.LocalDate data);

    // Busca próximas consultas de um paciente
    @Query("SELECT c FROM Consulta c WHERE c.paciente.id = :pacienteId AND c.dataHora > CURRENT_TIMESTAMP AND c.status IN ('AGENDADA', 'CONFIRMADA') ORDER BY c.dataHora")
    List<Consulta> findProximasConsultasPaciente(@Param("pacienteId") Long pacienteId);

    // Busca próximas consultas de um médico
    @Query("SELECT c FROM Consulta c WHERE c.medico.id = :medicoId AND c.dataHora > CURRENT_TIMESTAMP AND c.status IN ('AGENDADA', 'CONFIRMADA') ORDER BY c.dataHora")
    List<Consulta> findProximasConsultasMedico(@Param("medicoId") Long medicoId);

    // Busca consultas atrasadas
    @Query("SELECT c FROM Consulta c WHERE c.dataHora < CURRENT_TIMESTAMP AND c.status IN ('AGENDADA', 'CONFIRMADA')")
    List<Consulta> findConsultasAtrasadas();

    // Busca consultas que precisam de confirmação
    @Query("SELECT c FROM Consulta c WHERE c.status = 'AGENDADA' AND c.dataHora BETWEEN CURRENT_TIMESTAMP AND :dataLimite")
    List<Consulta> findConsultasParaConfirmar(@Param("dataLimite") LocalDateTime dataLimite);

    // Conta consultas por status
    @Query("SELECT c.status, COUNT(c) FROM Consulta c GROUP BY c.status")
    List<Object[]> countConsultasPorStatus();

    // Conta consultas por tipo
    @Query("SELECT c.tipoConsulta, COUNT(c) FROM Consulta c GROUP BY c.tipoConsulta")
    List<Object[]> countConsultasPorTipo();

    // Conta consultas por médico
    @Query("SELECT c.medico.nome, COUNT(c) FROM Consulta c GROUP BY c.medico.id, c.medico.nome ORDER BY COUNT(c) DESC")
    List<Object[]> countConsultasPorMedico();

    // Conta consultas por especialidade
    @Query("SELECT c.medico.especialidade, COUNT(c) FROM Consulta c GROUP BY c.medico.especialidade ORDER BY COUNT(c) DESC")
    List<Object[]> countConsultasPorEspecialidade();

    // Busca consultas por período com múltiplos critérios
    @Query("SELECT c FROM Consulta c WHERE " +
           "c.dataHora BETWEEN :dataInicio AND :dataFim AND " +
           "(:medicoId IS NULL OR c.medico.id = :medicoId) AND " +
           "(:pacienteId IS NULL OR c.paciente.id = :pacienteId) AND " +
           "(:status IS NULL OR c.status = :status) AND " +
           "(:tipoConsulta IS NULL OR c.tipoConsulta = :tipoConsulta)")
    Page<Consulta> findByPeriodoECriterios(@Param("dataInicio") LocalDateTime dataInicio,
                                         @Param("dataFim") LocalDateTime dataFim,
                                         @Param("medicoId") Long medicoId,
                                         @Param("pacienteId") Long pacienteId,
                                         @Param("status") Consulta.StatusConsulta status,
                                         @Param("tipoConsulta") Consulta.TipoConsulta tipoConsulta,
                                         Pageable pageable);

    // Busca consultas com faturamento
    @Query("SELECT c FROM Consulta c WHERE c.valorConsulta IS NOT NULL AND c.status = 'CONCLUIDA'")
    List<Consulta> findConsultasComFaturamento();

    // Calcula faturamento por período
    @Query("SELECT SUM(c.valorConsulta) FROM Consulta c WHERE c.dataHora BETWEEN :dataInicio AND :dataFim AND c.status = 'CONCLUIDA'")
    Double calcularFaturamentoPorPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                                       @Param("dataFim") LocalDateTime dataFim);

    // Calcula faturamento por médico
    @Query("SELECT c.medico.nome, SUM(c.valorConsulta) FROM Consulta c WHERE c.dataHora BETWEEN :dataInicio AND :dataFim AND c.status = 'CONCLUIDA' GROUP BY c.medico.id, c.medico.nome ORDER BY SUM(c.valorConsulta) DESC")
    List<Object[]> calcularFaturamentoPorMedico(@Param("dataInicio") LocalDateTime dataInicio, 
                                              @Param("dataFim") LocalDateTime dataFim);

    // Busca consultas não pagas
    @Query("SELECT c FROM Consulta c WHERE c.status = 'CONCLUIDA' AND (c.valorPago IS NULL OR c.valorPago < c.valorConsulta)")
    List<Consulta> findConsultasNaoPagas();

    // Busca consultas por forma de pagamento
    List<Consulta> findByFormaPagamento(Consulta.FormaPagamento formaPagamento);

    // Conta consultas por forma de pagamento
    @Query("SELECT c.formaPagamento, COUNT(c) FROM Consulta c WHERE c.formaPagamento IS NOT NULL GROUP BY c.formaPagamento")
    List<Object[]> countConsultasPorFormaPagamento();

    // Busca consultas com duração específica
    List<Consulta> findByDuracaoMinutos(Integer duracaoMinutos);

    // Busca consultas por faixa de duração
    @Query("SELECT c FROM Consulta c WHERE c.duracaoMinutos BETWEEN :duracaoMin AND :duracaoMax")
    List<Consulta> findByDuracaoMinutosBetween(@Param("duracaoMin") Integer duracaoMin, 
                                             @Param("duracaoMax") Integer duracaoMax);

    // Busca consultas canceladas com motivo
    @Query("SELECT c FROM Consulta c WHERE c.status = 'CANCELADA' AND c.motivoCancelamento IS NOT NULL")
    List<Consulta> findConsultasCanceladasComMotivo();

    // Conta cancelamentos por motivo
    @Query("SELECT c.motivoCancelamento, COUNT(c) FROM Consulta c WHERE c.status = 'CANCELADA' AND c.motivoCancelamento IS NOT NULL GROUP BY c.motivoCancelamento ORDER BY COUNT(c) DESC")
    List<Object[]> countCancelamentosPorMotivo();

    // Busca consultas que excederam o tempo previsto
    @Query("SELECT c FROM Consulta c WHERE c.dataFimConsulta IS NOT NULL AND c.dataInicioConsulta IS NOT NULL AND " +
           "TIMESTAMPDIFF(MINUTE, c.dataInicioConsulta, c.dataFimConsulta) > c.duracaoMinutos")
    List<Consulta> findConsultasComTempoExcedido();

    // Busca consultas por paciente e médico
    List<Consulta> findByPacienteIdAndMedicoId(Long pacienteId, Long medicoId);

    // Busca última consulta de um paciente
    @Query("SELECT c FROM Consulta c WHERE c.paciente.id = :pacienteId ORDER BY c.dataHora DESC")
    Optional<Consulta> findUltimaConsultaPaciente(@Param("pacienteId") Long pacienteId);

    // Busca última consulta de um paciente com um médico específico
    @Query("SELECT c FROM Consulta c WHERE c.paciente.id = :pacienteId AND c.medico.id = :medicoId ORDER BY c.dataHora DESC")
    Optional<Consulta> findUltimaConsultaPacienteComMedico(@Param("pacienteId") Long pacienteId, 
                                                          @Param("medicoId") Long medicoId);

    // Conta consultas por dia da semana
    @Query("SELECT DAYOFWEEK(c.dataHora), COUNT(c) FROM Consulta c GROUP BY DAYOFWEEK(c.dataHora) ORDER BY DAYOFWEEK(c.dataHora)")
    List<Object[]> countConsultasPorDiaDaSemana();

    // Conta consultas por hora do dia
    @Query("SELECT HOUR(c.dataHora), COUNT(c) FROM Consulta c GROUP BY HOUR(c.dataHora) ORDER BY HOUR(c.dataHora)")
    List<Object[]> countConsultasPorHoraDoDia();

    // Busca consultas em andamento
    @Query("SELECT c FROM Consulta c WHERE c.status = 'EM_ANDAMENTO'")
    List<Consulta> findConsultasEmAndamento();

    // Busca consultas por faixa de valor
    @Query("SELECT c FROM Consulta c WHERE c.valorConsulta BETWEEN :valorMin AND :valorMax ORDER BY c.valorConsulta")
    List<Consulta> findByValorConsultaBetween(@Param("valorMin") Double valorMin, 
                                            @Param("valorMax") Double valorMax);
}
