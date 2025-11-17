package com.vidaplus.sghss.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    @NotNull(message = "Médico é obrigatório")
    private Medico medico;

    @NotNull(message = "Data e hora da consulta são obrigatórias")
    @Future(message = "Data da consulta deve ser no futuro")
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @NotNull(message = "Duração é obrigatória")
    @Min(value = 15, message = "Duração mínima é 15 minutos")
    @Max(value = 180, message = "Duração máxima é 180 minutos")
    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracaoMinutos = 60;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo da consulta é obrigatório")
    @Column(name = "tipo_consulta", nullable = false)
    private TipoConsulta tipoConsulta;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status da consulta é obrigatório")
    @Column(name = "status", nullable = false)
    private StatusConsulta status = StatusConsulta.AGENDADA;

    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "sintomas", columnDefinition = "TEXT")
    private String sintomas;

    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;

    @Column(name = "prescricao", columnDefinition = "TEXT")
    private String prescricao;

    @Column(name = "exames_solicitados", columnDefinition = "TEXT")
    private String examesSolicitados;

    @Column(name = "valor_consulta", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "Valor da consulta deve ser positivo")
    private Double valorConsulta;

    @Column(name = "valor_pago", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "Valor pago deve ser positivo")
    private Double valorPago;

    @Column(name = "forma_pagamento")
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Column(name = "data_inicio_consulta")
    private LocalDateTime dataInicioConsulta;

    @Column(name = "data_fim_consulta")
    private LocalDateTime dataFimConsulta;

    @Column(name = "motivo_cancelamento")
    @Size(max = 500, message = "Motivo do cancelamento deve ter no máximo 500 caracteres")
    private String motivoCancelamento;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prontuario> prontuarios;

    // Enums
    public enum TipoConsulta {
        PRIMEIRA_CONSULTA,
        RETORNO,
        URGENCIA,
        EXAME,
        PROCEDIMENTO,
        CONSULTA_ONLINE
    }

    public enum StatusConsulta {
        AGENDADA,
        CONFIRMADA,
        EM_ANDAMENTO,
        CONCLUIDA,
        CANCELADA,
        REMARCADA,
        FALTA
    }

    public enum FormaPagamento {
        DINHEIRO,
        CARTAO_DEBITO,
        CARTAO_CREDITO,
        PIX,
        TRANSFERENCIA,
        PLANO_SAUDE,
        SUS
    }

    // Métodos auxiliares
    public boolean podeSerCancelada() {
        return status == StatusConsulta.AGENDADA || status == StatusConsulta.CONFIRMADA;
    }

    public boolean podeSerRemarcada() {
        return status == StatusConsulta.AGENDADA || status == StatusConsulta.CONFIRMADA;
    }

    public boolean podeSerIniciada() {
        return status == StatusConsulta.CONFIRMADA && dataHora != null && dataHora.isBefore(LocalDateTime.now().plusMinutes(30));
    }

    public boolean podeSerConcluida() {
        return status == StatusConsulta.EM_ANDAMENTO;
    }

    public boolean estaAtrasada() {
        return status == StatusConsulta.CONFIRMADA && dataHora != null && dataHora.isBefore(LocalDateTime.now().minusMinutes(15));
    }

    public double getValorPendente() {
        if (valorConsulta != null && valorPago != null) {
            return Math.max(0, valorConsulta - valorPago);
        }
        return valorConsulta != null ? valorConsulta : 0.0;
    }

    public boolean estaPaga() {
        return valorPago != null && valorConsulta != null && valorPago >= valorConsulta;
    }

}