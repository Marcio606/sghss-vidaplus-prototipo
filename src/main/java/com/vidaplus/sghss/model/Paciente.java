package com.vidaplus.sghss.model;

<<<<<<< HEAD
import javax.persistence.*;

@Entity
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String historicoClinico;

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getHistoricoClinico() { return historicoClinico; }
    public void setHistoricoClinico(String historicoClinico) { this.historicoClinico = historicoClinico; }
=======
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
@Table(name = "pacientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")
    @Column(nullable = false)
    private String telefone;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Sexo é obrigatório")
    private Sexo sexo;

    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    private String endereco;

    @Size(max = 10, message = "CEP deve ter no máximo 10 caracteres")
    private String cep;

    @Size(max = 50, message = "Cidade deve ter no máximo 50 caracteres")
    private String cidade;

    @Size(max = 2, message = "UF deve ter 2 caracteres")
    private String uf;

    @Column(name = "numero_prontuario", unique = true)
    private String numeroProntuario;

    @Column(name = "nome_mae")
    @Size(max = 100, message = "Nome da mãe deve ter no máximo 100 caracteres")
    private String nomeMae;

    @Column(name = "nome_pai")
    @Size(max = 100, message = "Nome do pai deve ter no máximo 100 caracteres")
    private String nomePai;

    @Column(name = "estado_civil")
    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;

    @Column(name = "profissao")
    @Size(max = 100, message = "Profissão deve ter no máximo 100 caracteres")
    private String profissao;

    @Column(name = "plano_saude")
    @Size(max = 100, message = "Plano de saúde deve ter no máximo 100 caracteres")
    private String planoSaude;

    @Column(name = "numero_plano")
    @Size(max = 50, message = "Número do plano deve ter no máximo 50 caracteres")
    private String numeroPlano;

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

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consulta> consultas;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prontuario> prontuarios;

    // Enums
    public enum Sexo {
        MASCULINO, FEMININO, OUTRO
    }

    public enum EstadoCivil {
        SOLTEIRO, CASADO, DIVORCIADO, VIUVO, UNIAO_ESTAVEL
    }

    // Métodos auxiliares
    public int getIdade() {
        return LocalDate.now().getYear() - dataNascimento.getYear();
    }

    public String getCpfFormatado() {
        if (cpf != null && cpf.length() == 11) {
            return cpf.substring(0, 3) + "." + 
                   cpf.substring(3, 6) + "." + 
                   cpf.substring(6, 9) + "-" + 
                   cpf.substring(9, 11);
        }
        return cpf;
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
>>>>>>> 8065f719c37ca4ef855eaa2d4cbbfd9201d911fc
}
