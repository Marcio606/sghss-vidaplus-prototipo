package com.vidaplus.sghss.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "prontuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prontuario {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;

    @NotBlank(message = "História da doença atual é obrigatória")
    @Column(name = "historia_doenca_atual", columnDefinition = "TEXT", nullable = false)
    private String historiaDoencaAtual;

    @Column(name = "anamnese", columnDefinition = "TEXT")
    private String anamnese;

    @Column(name = "exame_fisico", columnDefinition = "TEXT")
    private String exameFisico;

    @Column(name = "hipoteses_diagnosticas", columnDefinition = "TEXT")
    private String hipotesesDiagnosticas;

    @Column(name = "diagnostico_final", columnDefinition = "TEXT")
    private String diagnosticoFinal;

    @Column(name = "prescricao_medicamentos", columnDefinition = "TEXT")
    private String prescricaoMedicamentos;

    @Column(name = "exames_solicitados", columnDefinition = "TEXT")
    private String examesSolicitados;

    @Column(name = "orientacoes", columnDefinition = "TEXT")
    private String orientacoes;

    @Column(name = "proximos_passos", columnDefinition = "TEXT")
    private String proximosPassos;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo de prontuário é obrigatório")
    @Column(name = "tipo_prontuario", nullable = false)
    private TipoProntuario tipoProntuario;

    @Column(name = "peso_kg", precision = 5, scale = 2)
    @DecimalMin(value = "0.1", message = "Peso deve ser maior que 0")
    @DecimalMax(value = "500.0", message = "Peso deve ser menor que 500kg")
    private Double pesoKg;

    @Column(name = "altura_cm")
    @Min(value = 30, message = "Altura deve ser maior que 30cm")
    @Max(value = 250, message = "Altura deve ser menor que 250cm")
    private Integer alturaCm;

    @Column(name = "pressao_arterial_sistolica")
    @Min(value = 60, message = "Pressão sistólica deve ser maior que 60")
    @Max(value = 300, message = "Pressão sistólica deve ser menor que 300")
    private Integer pressaoArterialSistolica;

    @Column(name = "pressao_arterial_diastolica")
    @Min(value = 40, message = "Pressão diastólica deve ser maior que 40")
    @Max(value = 200, message = "Pressão diastólica deve ser menor que 200")
    private Integer pressaoArterialDiastolica;

    @Column(name = "frequencia_cardiaca")
    @Min(value = 30, message = "Frequência cardíaca deve ser maior que 30")
    @Max(value = 200, message = "Frequência cardíaca deve ser menor que 200")
    private Integer frequenciaCardiaca;

    @Column(name = "temperatura_celsius", precision = 4, scale = 2)
    @DecimalMin(value = "30.0", message = "Temperatura deve ser maior que 30°C")
    @DecimalMax(value = "45.0", message = "Temperatura deve ser menor que 45°C")
    private Double temperaturaCelsius;

    @Column(name = "saturacao_oxigenio")
    @Min(value = 70, message = "Saturação de oxigênio deve ser maior que 70%")
    @Max(value = 100, message = "Saturação de oxigênio deve ser menor que 100%")
    private Integer saturacaoOxigenio;

    @Column(name = "glicemia")
    @Min(value = 20, message = "Glicemia deve ser maior que 20mg/dL")
    @Max(value = 1000, message = "Glicemia deve ser menor que 1000mg/dL")
    private Integer glicemia;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "prontuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnexoProntuario> anexos;

    // Enums
    public enum TipoProntuario {
        PRIMEIRA_CONSULTA,
        RETORNO,
        URGENCIA,
        EMERGENCIA,
        CONSULTA_PREVENTIVA,
        ATENDIMENTO_DOMICILIAR,
        TELEMEDICINA
    }

    // Métodos auxiliares
    public Double getImc() {
        if (pesoKg != null && alturaCm != null && alturaCm > 0) {
            double alturaMetros = alturaCm / 100.0;
            return pesoKg / (alturaMetros * alturaMetros);
        }
        return null;
    }

    public String getClassificacaoImc() {
        Double imc = getImc();
        if (imc == null) return "Não calculado";
        
        if (imc < 18.5) return "Abaixo do peso";
        if (imc < 25.0) return "Peso normal";
        if (imc < 30.0) return "Sobrepeso";
        if (imc < 35.0) return "Obesidade grau I";
        if (imc < 40.0) return "Obesidade grau II";
        return "Obesidade grau III";
    }

    public String getPressaoArterialFormatada() {
        if (pressaoArterialSistolica != null && pressaoArterialDiastolica != null) {
            return pressaoArterialSistolica + "/" + pressaoArterialDiastolica + " mmHg";
        }
        return "Não medida";
    }

    public String getClassificacaoPressao() {
        if (pressaoArterialSistolica == null || pressaoArterialDiastolica == null) {
            return "Não medida";
        }

        if (pressaoArterialSistolica < 120 && pressaoArterialDiastolica < 80) {
            return "Normal";
        } else if (pressaoArterialSistolica < 130 && pressaoArterialDiastolica < 80) {
            return "Elevada";
        } else if (pressaoArterialSistolica < 140 || pressaoArterialDiastolica < 90) {
            return "Hipertensão estágio 1";
        } else if (pressaoArterialSistolica < 180 || pressaoArterialDiastolica < 120) {
            return "Hipertensão estágio 2";
        } else {
            return "Crise hipertensiva";
        }
    }

    public String getTemperaturaFormatada() {
        if (temperaturaCelsius != null) {
            return String.format("%.1f°C", temperaturaCelsius);
        }
        return "Não medida";
    }

    public boolean temFebre() {
        return temperaturaCelsius != null && temperaturaCelsius > 37.5;
    }

    public boolean temHipertensao() {
        return pressaoArterialSistolica != null && pressaoArterialDiastolica != null &&
               (pressaoArterialSistolica >= 140 || pressaoArterialDiastolica >= 90);
    }

    public boolean temHipotensao() {
        return pressaoArterialSistolica != null && pressaoArterialDiastolica != null &&
               (pressaoArterialSistolica < 90 || pressaoArterialDiastolica < 60);
    }
}
