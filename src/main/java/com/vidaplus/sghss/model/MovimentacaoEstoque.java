package com.vidaplus.sghss.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes_estoque")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicamento_id", nullable = false)
    @NotNull(message = "Medicamento é obrigatório")
    private Medicamento medicamento;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo de movimentação é obrigatório")
    @Column(name = "tipo_movimentacao", nullable = false)
    private TipoMovimentacao tipoMovimentacao;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "quantidade_anterior", nullable = false)
    private Integer quantidadeAnterior;

    @Column(name = "quantidade_atual", nullable = false)
    private Integer quantidadeAtual;

    @Column(name = "valor_unitario", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "Valor unitário deve ser positivo")
    private Double valorUnitario;

    @Column(name = "valor_total", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "Valor total deve ser positivo")
    private Double valorTotal;

    @Size(max = 200, message = "Fornecedor deve ter no máximo 200 caracteres")
    private String fornecedor;

    @Size(max = 50, message = "Lote deve ter no máximo 50 caracteres")
    private String lote;

    @Column(name = "data_validade")
    private java.time.LocalDate dataValidade;

    @Size(max = 50, message = "Nota fiscal deve ter no máximo 50 caracteres")
    @Column(name = "nota_fiscal")
    private String notaFiscal;

    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "usuario_responsavel")
    @Size(max = 100, message = "Usuário responsável deve ter no máximo 100 caracteres")
    private String usuarioResponsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescricao_id")
    private Prescricao prescricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;

    @CreationTimestamp
    @Column(name = "data_movimentacao", nullable = false, updatable = false)
    private LocalDateTime dataMovimentacao;

    // Enums
    public enum TipoMovimentacao {
        ENTRADA("Entrada"),
        SAIDA("Saída"),
        AJUSTE_POSITIVO("Ajuste Positivo"),
        AJUSTE_NEGATIVO("Ajuste Negativo"),
        TRANSFERENCIA_ENTRADA("Transferência Entrada"),
        TRANSFERENCIA_SAIDA("Transferência Saída"),
        PERDA("Perda"),
        VENCIMENTO("Vencimento"),
        ROUBO("Roubo"),
        DANO("Dano");

        private final String descricao;

        TipoMovimentacao(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        public boolean isEntrada() {
            return this == ENTRADA || this == AJUSTE_POSITIVO || this == TRANSFERENCIA_ENTRADA;
        }

        public boolean isSaida() {
            return this == SAIDA || this == AJUSTE_NEGATIVO || this == TRANSFERENCIA_SAIDA ||
                   this == PERDA || this == VENCIMENTO || this == ROUBO || this == DANO;
        }
    }

    // Métodos auxiliares
    public boolean isMovimentacaoPositiva() {
        return tipoMovimentacao.isEntrada();
    }

    public boolean isMovimentacaoNegativa() {
        return tipoMovimentacao.isSaida();
    }

    public String getMovimentacaoFormatada() {
        String simbolo = isMovimentacaoPositiva() ? "+" : "-";
        return simbolo + quantidade + " unidades";
    }

    public String getVariacaoEstoque() {
        int variacao = quantidadeAtual - quantidadeAnterior;
        String simbolo = variacao >= 0 ? "+" : "";
        return simbolo + variacao + " unidades";
    }

    public double getVariacaoPercentual() {
        if (quantidadeAnterior > 0) {
            return ((double) (quantidadeAtual - quantidadeAnterior) / quantidadeAnterior) * 100;
        }
        return 0.0;
    }

    public String getVariacaoFormatada() {
        double variacao = getVariacaoPercentual();
        String simbolo = variacao >= 0 ? "+" : "";
        return simbolo + String.format("%.1f%%", variacao);
    }

    public boolean isMovimentacaoFinanceira() {
        return valorUnitario != null && valorTotal != null;
    }

    public String getValorFormatado() {
        if (valorTotal != null) {
            return String.format("R$ %.2f", valorTotal);
        }
        return "R$ 0,00";
    }

    public String getTipoFormatado() {
        return tipoMovimentacao.getDescricao();
    }

    public boolean isMovimentacaoPorPrescricao() {
        return prescricao != null;
    }

    public boolean isMovimentacaoPorConsulta() {
        return consulta != null;
    }

    public String getOrigemMovimentacao() {
        if (prescricao != null) {
            return "Prescrição #" + prescricao.getId();
        } else if (consulta != null) {
            return "Consulta #" + consulta.getId();
        } else if (fornecedor != null) {
            return "Fornecedor: " + fornecedor;
        } else {
            return "Sistema";
        }
    }

    public boolean isMovimentacaoRecente() {
        return dataMovimentacao.isAfter(LocalDateTime.now().minusDays(7));
    }

    public String getDataFormatada() {
        return dataMovimentacao.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public boolean precisaAtencao() {
        return tipoMovimentacao == TipoMovimentacao.PERDA ||
               tipoMovimentacao == TipoMovimentacao.ROUBO ||
               tipoMovimentacao == TipoMovimentacao.DANO ||
               (tipoMovimentacao == TipoMovimentacao.VENCIMENTO && quantidade > 10);
    }
}
