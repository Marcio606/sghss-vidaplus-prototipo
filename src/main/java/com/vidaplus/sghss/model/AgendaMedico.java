package com.vidaplus.sghss.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "agendas_medico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    @NotNull(message = "Médico é obrigatório")
    private Medico medico;

    @NotNull(message = "Data é obrigatória")
    @FutureOrPresent(message = "Data deve ser hoje ou no futuro")
    @Column(nullable = false)
    private LocalDate data;

    @NotNull(message = "Hora de início é obrigatória")
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @NotNull(message = "Hora de fim é obrigatória")
    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    @NotNull(message = "Duração da consulta é obrigatória")
    @Min(value = 15, message = "Duração mínima é 15 minutos")
    @Max(value = 180, message = "Duração máxima é 180 minutos")
    @Column(name = "duracao_consulta_minutos", nullable = false)
    private Integer duracaoConsultaMinutos = 60;

    @Column(name = "intervalo_entre_consultas_minutos")
    @Min(value = 0, message = "Intervalo não pode ser negativo")
    @Max(value = 60, message = "Intervalo máximo é 60 minutos")
    private Integer intervaloEntreConsultasMinutos = 0;

    @Column(name = "max_consultas_dia")
    @Min(value = 1, message = "Máximo de consultas deve ser pelo menos 1")
    @Max(value = 50, message = "Máximo de consultas não pode ser maior que 50")
    private Integer maxConsultasDia = 20;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo de agenda é obrigatório")
    @Column(name = "tipo_agenda", nullable = false)
    private TipoAgenda tipoAgenda;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "agendaMedico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consulta> consultas;

    // Enums
    public enum TipoAgenda {
        NORMAL,
        URGENCIA,
        RETORNO,
        PRIMEIRA_CONSULTA,
        EXAME,
        PROCEDIMENTO
    }

    // Métodos auxiliares
    public boolean isDentroDoHorario(LocalTime hora) {
        return !hora.isBefore(horaInicio) && !hora.isAfter(horaFim);
    }

    public boolean isDiaUtil() {
        return data.getDayOfWeek().getValue() <= 5; // Segunda a sexta
    }

    public int getHorariosDisponiveis() {
        int duracaoTotal = duracaoConsultaMinutos + intervaloEntreConsultasMinutos;
        long minutosDisponiveis = java.time.Duration.between(horaInicio, horaFim).toMinutes();
        return (int) (minutosDisponiveis / duracaoTotal);
    }

    public boolean temVagasDisponiveis() {
        if (consultas == null) return true;
        long consultasAgendadas = consultas.stream()
            .filter(c -> c.getStatus() == Consulta.StatusConsulta.AGENDADA || 
                        c.getStatus() == Consulta.StatusConsulta.CONFIRMADA)
            .count();
        return consultasAgendadas < maxConsultasDia;
    }

    public LocalTime getProximoHorarioDisponivel() {
        if (consultas == null || consultas.isEmpty()) {
            return horaInicio;
        }

        LocalTime proximoHorario = horaInicio;
        int duracaoTotal = duracaoConsultaMinutos + intervaloEntreConsultasMinutos;

        while (proximoHorario.isBefore(horaFim)) {
            LocalTime horarioFinal = proximoHorario.plusMinutes(duracaoConsultaMinutos);
            
            boolean horarioOcupado = consultas.stream()
                .filter(c -> c.getStatus() == Consulta.StatusConsulta.AGENDADA || 
                            c.getStatus() == Consulta.StatusConsulta.CONFIRMADA)
                .anyMatch(c -> {
                    LocalTime inicioConsulta = c.getDataHora().toLocalTime();
                    LocalTime fimConsulta = inicioConsulta.plusMinutes(c.getDuracaoMinutos());
                    
                    return !proximoHorario.isAfter(inicioConsulta) && 
                           !horarioFinal.isBefore(fimConsulta);
                });

            if (!horarioOcupado) {
                return proximoHorario;
            }

            proximoHorario = proximoHorario.plusMinutes(duracaoTotal);
        }

        return null; // Nenhum horário disponível
    }

    public boolean podeAgendarConsulta(LocalTime horario) {
        return isDentroDoHorario(horario) && 
               temVagasDisponiveis() && 
               getProximoHorarioDisponivel() != null;
    }

    public String getHorarioFormatado() {
        return horaInicio + " às " + horaFim;
    }

    public String getStatusAgenda() {
        if (!ativo) return "INATIVA";
        if (!temVagasDisponiveis()) return "LOTADA";
        if (getProximoHorarioDisponivel() == null) return "SEM_VAGAS";
        return "DISPONIVEL";
    }
}
