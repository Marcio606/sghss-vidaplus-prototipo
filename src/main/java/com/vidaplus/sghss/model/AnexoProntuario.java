package com.vidaplus.sghss.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "anexos_prontuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnexoProntuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prontuario_id", nullable = false)
    @NotNull(message = "Prontuário é obrigatório")
    private Prontuario prontuario;

    @NotBlank(message = "Nome do arquivo é obrigatório")
    @Size(max = 255, message = "Nome do arquivo deve ter no máximo 255 caracteres")
    @Column(name = "nome_arquivo", nullable = false)
    private String nomeArquivo;

    @NotBlank(message = "Tipo do arquivo é obrigatório")
    @Size(max = 100, message = "Tipo do arquivo deve ter no máximo 100 caracteres")
    @Column(name = "tipo_arquivo", nullable = false)
    private String tipoArquivo;

    @NotNull(message = "Tamanho do arquivo é obrigatório")
    @Min(value = 1, message = "Tamanho do arquivo deve ser pelo menos 1 byte")
    @Max(value = 10485760, message = "Tamanho do arquivo não pode ser maior que 10MB")
    @Column(name = "tamanho_arquivo", nullable = false)
    private Long tamanhoArquivo;

    @NotBlank(message = "Caminho do arquivo é obrigatório")
    @Size(max = 500, message = "Caminho do arquivo deve ter no máximo 500 caracteres")
    @Column(name = "caminho_arquivo", nullable = false)
    private String caminhoArquivo;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo de anexo é obrigatório")
    @Column(name = "tipo_anexo", nullable = false)
    private TipoAnexo tipoAnexo;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Size(max = 100, message = "Usuário que fez upload deve ter no máximo 100 caracteres")
    @Column(name = "usuario_upload")
    private String usuarioUpload;

    @Column(name = "hash_arquivo")
    @Size(max = 64, message = "Hash do arquivo deve ter no máximo 64 caracteres")
    private String hashArquivo;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "data_upload", nullable = false, updatable = false)
    private LocalDateTime dataUpload;

    // Enums
    public enum TipoAnexo {
        EXAME_LABORATORIO("Exame de Laboratório"),
        EXAME_IMAGEM("Exame de Imagem"),
        RECEITA_MEDICA("Receita Médica"),
        ATESTADO_MEDICO("Atestado Médico"),
        RELATORIO_MEDICO("Relatório Médico"),
        HISTORICO_MEDICO("Histórico Médico"),
        DOCUMENTO_IDENTIDADE("Documento de Identidade"),
        DOCUMENTO_PLANO_SAUDE("Documento do Plano de Saúde"),
        OUTRO("Outro");

        private final String descricao;

        TipoAnexo(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    // Métodos auxiliares
    public String getTamanhoFormatado() {
        if (tamanhoArquivo == null) return "0 B";
        
        long bytes = tamanhoArquivo;
        String[] unidades = {"B", "KB", "MB", "GB"};
        int unidade = 0;
        
        while (bytes >= 1024 && unidade < unidades.length - 1) {
            bytes /= 1024;
            unidade++;
        }
        
        return String.format("%.1f %s", (double) bytes, unidades[unidade]);
    }

    public String getExtensaoArquivo() {
        if (nomeArquivo != null && nomeArquivo.contains(".")) {
            return nomeArquivo.substring(nomeArquivo.lastIndexOf(".") + 1).toLowerCase();
        }
        return "";
    }

    public boolean isImagem() {
        String extensao = getExtensaoArquivo();
        return extensao.matches("jpg|jpeg|png|gif|bmp|tiff|webp");
    }

    public boolean isDocumento() {
        String extensao = getExtensaoArquivo();
        return extensao.matches("pdf|doc|docx|xls|xlsx|ppt|pptx|txt|rtf");
    }

    public boolean isVideo() {
        String extensao = getExtensaoArquivo();
        return extensao.matches("mp4|avi|mov|wmv|flv|mkv|webm");
    }

    public boolean isAudio() {
        String extensao = getExtensaoArquivo();
        return extensao.matches("mp3|wav|flac|aac|ogg|wma");
    }

    public String getTipoMidia() {
        if (isImagem()) return "Imagem";
        if (isDocumento()) return "Documento";
        if (isVideo()) return "Vídeo";
        if (isAudio()) return "Áudio";
        return "Arquivo";
    }

    public String getDataFormatada() {
        return dataUpload.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public boolean isArquivoGrande() {
        return tamanhoArquivo != null && tamanhoArquivo > 5242880; // 5MB
    }

    public boolean isArquivoPequeno() {
        return tamanhoArquivo != null && tamanhoArquivo < 1048576; // 1MB
    }

    public String getStatusArquivo() {
        if (!ativo) return "Inativo";
        if (isArquivoGrande()) return "Grande";
        if (isArquivoPequeno()) return "Pequeno";
        return "Normal";
    }

    public String getTipoFormatado() {
        return tipoAnexo.getDescricao();
    }

    public boolean isExameMedico() {
        return tipoAnexo == TipoAnexo.EXAME_LABORATORIO ||
               tipoAnexo == TipoAnexo.EXAME_IMAGEM;
    }

    public boolean isDocumentoOficial() {
        return tipoAnexo == TipoAnexo.RECEITA_MEDICA ||
               tipoAnexo == TipoAnexo.ATESTADO_MEDICO ||
               tipoAnexo == TipoAnexo.RELATORIO_MEDICO;
    }

    public boolean isDocumentoPessoal() {
        return tipoAnexo == TipoAnexo.DOCUMENTO_IDENTIDADE ||
               tipoAnexo == TipoAnexo.DOCUMENTO_PLANO_SAUDE;
    }

    public String getIconeArquivo() {
        String extensao = getExtensaoArquivo();
        switch (extensao) {
            case "pdf": return "📄";
            case "jpg":
            case "jpeg":
            case "png":
            case "gif": return "🖼️";
            case "doc":
            case "docx": return "📝";
            case "xls":
            case "xlsx": return "📊";
            case "ppt":
            case "pptx": return "📈";
            case "mp4":
            case "avi":
            case "mov": return "🎥";
            case "mp3":
            case "wav": return "🎵";
            case "zip":
            case "rar": return "📦";
            default: return "📁";
        }
    }
}
