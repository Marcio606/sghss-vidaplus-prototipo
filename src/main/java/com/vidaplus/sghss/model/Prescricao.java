package com.vidaplus.sghss.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prescricoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id", nullable = false)
    @NotNull(message = "Consulta é obrigatória")
    private Consulta consulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicamento_id", nullable = false)
    @NotNull(message = "Medicamento é obrigatório")
    private Medicamento medicamento;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
    @Max(value = 1000, message = "Quantidade não pode ser maior que 1000")
    @Column(nullable = false)
    private Integer quantidade;

    @NotBlank(message = "Dosagem é obrigatória")
    @Size(max = 100, message = "Dosagem deve ter no máximo 100 caracteres")
    @Column(nullable = false)
    private String dosagem;

    @NotBlank(message = "Frequência é obrigatória")
    @Size(max = 100, message = "Frequência deve ter no máximo 100 caracteres")
    @Column(nullable = false)
    private String frequencia;

    @NotBlank(message = "Duração é obrigatória")
    @Size(max = 100, message = "Duração deve ter no máximo 100 caracteres")
    @Column(nullable = false)
    private String duracao;

    @Size(max = 500, message = "Instruções devem ter no máximo 500 caracteres")
    @Column(columnDefinition = "TEXT")
    private String instrucoes;

    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status da prescrição é obrigatório")
    @Column(name = "status", nullable = false)
    private StatusPrescricao status = StatusPrescricao.ATIVA;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo de prescrição é obrigatório")
    @Column(name = "tipo_prescricao", nullable = false)
    private TipoPrescricao tipoPrescricao;

    @Column(name = "quantidade_dispensada")
    @Min(value = 0, message = "Quantidade dispensada não pode ser negativa")
    private Integer quantidadeDispensada = 0;

    @Column(name = "data_dispensacao")
    private LocalDateTime dataDispensacao;

    @Column(name = "responsavel_dispensacao")
    @Size(max = 100, message = "Responsável pela dispensação deve ter no máximo 100 caracteres")
    private String responsavelDispensacao;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    // Enums
    public enum StatusPrescricao {
        ATIVA,
        SUSPENSA,
        CONCLUIDA,
        CANCELADA,
        DISPENSADA
    }

    public enum TipoPrescricao {
        MEDICAMENTO,
        DIETA,
        EXERCICIO,
        REPOUSO,
        OUTRO
    }

    // Métodos auxiliares
    public boolean podeSerDispensada() {
        return status == StatusPrescricao.ATIVA && 
               quantidadeDispensada < quantidade;
    }

    public boolean estaCompleta() {
        return quantidadeDispensada != null && 
               quantidadeDispensada >= quantidade;
    }

    public int getQuantidadePendente() {
        if (quantidadeDispensada != null) {
            return Math.max(0, quantidade - quantidadeDispensada);
        }
        return quantidade;
    }

    public boolean estaVencida() {
        return dataFim != null && dataFim.isBefore(LocalDateTime.now());
    }

    public boolean estaAtiva() {
        return status == StatusPrescricao.ATIVA && 
               (dataFim == null || !dataFim.isBefore(LocalDateTime.now()));
    }

    public String getPrescricaoCompleta() {
        return medicamento.getNome() + " " + 
               dosagem + " - " + 
               frequencia + " por " + 
               duracao;
    }

    public double getValorTotalPrescricao() {
        if (medicamento.getPrecoVenda() != null) {
            return quantidade * medicamento.getPrecoVenda();
        }
        return 0.0;
    }

    public double getValorPendente() {
        if (medicamento.getPrecoVenda() != null) {
            return getQuantidadePendente() * medicamento.getPrecoVenda();
        }
        return 0.0;
    }

    public String getStatusFormatado() {
        switch (status) {
            case ATIVA: return "Ativa";
            case SUSPENSA: return "Suspensa";
            case CONCLUIDA: return "Concluída";
            case CANCELADA: return "Cancelada";
            case DISPENSADA: return "Dispensada";
            default: return "Desconhecido";
        }
    }

    public boolean precisaReceitaControlada() {
        return medicamento.getTipoReceita() == Medicamento.TipoReceita.CONTROLADA ||
               medicamento.getTipoReceita() == Medicamento.TipoReceita.ESPECIAL ||
               medicamento.getTipoReceita() == Medicamento.TipoReceita.AMARELA ||
               medicamento.getTipoReceita() == Medicamento.TipoReceita.AZUL;
    }
}
