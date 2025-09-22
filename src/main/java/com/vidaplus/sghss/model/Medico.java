package com.vidaplus.sghss.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "medicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "CRM é obrigatório")
    @Pattern(regexp = "\\d{4,6}", message = "CRM deve conter 4 a 6 dígitos")
    @Column(unique = true, nullable = false)
    private String crm;

    @NotBlank(message = "UF do CRM é obrigatória")
    @Size(min = 2, max = 2, message = "UF deve ter 2 caracteres")
    @Column(nullable = false, length = 2)
    private String crmUf;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")
    @Column(nullable = false)
    private String telefone;

    @NotBlank(message = "Especialidade é obrigatória")
    @Size(min = 2, max = 100, message = "Especialidade deve ter entre 2 e 100 caracteres")
    @Column(nullable = false)
    private String especialidade;

    @Column(name = "especialidade_secundaria")
    @Size(max = 100, message = "Especialidade secundária deve ter no máximo 100 caracteres")
    private String especialidadeSecundaria;

    @Column(name = "data_formacao")
    private Integer dataFormacao;

    @Column(name = "instituicao_formacao")
    @Size(max = 200, message = "Instituição de formação deve ter no máximo 200 caracteres")
    private String instituicaoFormacao;

    @Column(name = "numero_identificacao")
    @Size(max = 50, message = "Número de identificação deve ter no máximo 50 caracteres")
    private String numeroIdentificacao;

    @Column(name = "valor_consulta", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "Valor da consulta deve ser positivo")
    private Double valorConsulta;

    @Column(name = "tempo_consulta_minutos")
    @Min(value = 15, message = "Tempo mínimo de consulta é 15 minutos")
    @Max(value = 180, message = "Tempo máximo de consulta é 180 minutos")
    private Integer tempoConsultaMinutos = 60;

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

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consulta> consultas;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AgendaMedico> agendas;

    // Métodos auxiliares
    public String getCrmCompleto() {
        return "CRM " + crm + "/" + crmUf;
    }

    public String getTelefoneFormatado() {
        if (telefone != null && telefone.length() == 11) {
            return "(" + telefone.substring(0, 2) + ") " + 
                   telefone.substring(2, 7) + "-" + 
                   telefone.substring(7, 11);
        } else if (telefone != null && telefone.length() == 10) {
            return "(" + telefone.substring(0, 2) + ") " + 
                   telefone.substring(2, 6) + "-" + 
                   telefone.substring(6, 10);
        }
        return telefone;
    }

    public boolean temAgendaDisponivel() {
        return agendas != null && !agendas.isEmpty() && 
               agendas.stream().anyMatch(agenda -> agenda.getAtivo());
    }
}
