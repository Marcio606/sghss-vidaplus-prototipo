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
import java.util.List;

@Entity
@Table(name = "medicamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 200, message = "Nome deve ter entre 2 e 200 caracteres")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Princípio ativo é obrigatório")
    @Size(min = 2, max = 200, message = "Princípio ativo deve ter entre 2 e 200 caracteres")
    @Column(name = "principio_ativo", nullable = false)
    private String principioAtivo;

    @NotBlank(message = "Concentração é obrigatória")
    @Size(min = 1, max = 50, message = "Concentração deve ter entre 1 e 50 caracteres")
    @Column(nullable = false)
    private String concentracao;

    @NotBlank(message = "Forma farmacêutica é obrigatória")
    @Size(min = 2, max = 50, message = "Forma farmacêutica deve ter entre 2 e 50 caracteres")
    @Column(name = "forma_farmaceutica", nullable = false)
    private String formaFarmaceutica;

    @Size(max = 50, message = "Laboratório deve ter no máximo 50 caracteres")
    private String laboratorio;

    @Pattern(regexp = "\\d{13}", message = "Código de barras deve conter 13 dígitos")
    @Column(name = "codigo_barras", unique = true)
    private String codigoBarras;

    @Column(name = "numero_registro_anvisa")
    @Size(max = 20, message = "Número de registro ANVISA deve ter no máximo 20 caracteres")
    private String numeroRegistroAnvisa;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Categoria é obrigatória")
    private CategoriaMedicamento categoria;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo de receita é obrigatório")
    @Column(name = "tipo_receita", nullable = false)
    private TipoReceita tipoReceita;

    @Column(name = "quantidade_estoque", nullable = false)
    @Min(value = 0, message = "Quantidade em estoque não pode ser negativa")
    private Integer quantidadeEstoque = 0;

    @Column(name = "quantidade_minima", nullable = false)
    @Min(value = 0, message = "Quantidade mínima não pode ser negativa")
    private Integer quantidadeMinima = 0;

    @Column(name = "quantidade_maxima")
    @Min(value = 0, message = "Quantidade máxima não pode ser negativa")
    private Integer quantidadeMaxima;

    @Column(name = "preco_compra", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "Preço de compra deve ser positivo")
    private Double precoCompra;

    @Column(name = "preco_venda", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "Preço de venda deve ser positivo")
    private Double precoVenda;

    @Column(name = "data_validade")
    private LocalDate dataValidade;

    @Column(name = "lote")
    @Size(max = 50, message = "Lote deve ter no máximo 50 caracteres")
    private String lote;

    @Column(name = "fornecedor")
    @Size(max = 100, message = "Fornecedor deve ter no máximo 100 caracteres")
    private String fornecedor;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "contraindicacoes", columnDefinition = "TEXT")
    private String contraindicacoes;

    @Column(name = "efeitos_colaterais", columnDefinition = "TEXT")
    private String efeitosColaterais;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "medicamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prescricao> prescricoes;

    @OneToMany(mappedBy = "medicamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MovimentacaoEstoque> movimentacoes;

    // Enums
    public enum CategoriaMedicamento {
        ANALGESICO,
        ANTI_INFLAMATORIO,
        ANTIBIOTICO,
        ANTIALERGICO,
        ANTIDEPRESSIVO,
        ANTI_HIPERTENSIVO,
        ANTIDIABETICO,
        VITAMINA,
        SUPLEMENTO,
        OUTRO
    }

    public enum TipoReceita {
        COMUM,
        CONTROLADA,
        ESPECIAL,
        AMARELA,
        AZUL,
        SEM_RECEITA
    }

    // Métodos auxiliares
    public boolean estaVencido() {
        return dataValidade != null && dataValidade.isBefore(LocalDate.now());
    }

    public boolean estaProximoVencimento() {
        return dataValidade != null && 
               dataValidade.isBefore(LocalDate.now().plusDays(30));
    }

    public boolean estoqueBaixo() {
        return quantidadeEstoque <= quantidadeMinima;
    }

    public boolean estoqueCritico() {
        return quantidadeEstoque <= (quantidadeMinima * 0.5);
    }

    public boolean estoqueAlto() {
        return quantidadeMaxima != null && quantidadeEstoque >= quantidadeMaxima;
    }

    public String getStatusEstoque() {
        if (estoqueCritico()) return "CRÍTICO";
        if (estoqueBaixo()) return "BAIXO";
        if (estoqueAlto()) return "ALTO";
        return "NORMAL";
    }

    public double getMargemLucro() {
        if (precoCompra != null && precoVenda != null && precoCompra > 0) {
            return ((precoVenda - precoCompra) / precoCompra) * 100;
        }
        return 0.0;
    }

    public int getDiasParaVencimento() {
        if (dataValidade != null) {
            return (int) java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), dataValidade);
        }
        return Integer.MAX_VALUE;
    }
}
