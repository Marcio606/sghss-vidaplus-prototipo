# FASE 3: IMPLEMENTAÇÃO (PROTOTIPAGEM)

## 1. VISÃO GERAL DA IMPLEMENTAÇÃO

### 1.1 Stack Tecnológico Utilizado

**Backend:**
- ✅ **Spring Boot 2.7.14** - Framework web Java
- ✅ **Spring Data JPA 2.7.14** - ORM para persistência
- ✅ **Hibernate 5.6.14** - Implementação JPA
- ✅ **MySQL 8.0.40** - Banco de dados relacional
- ✅ **Spring Security 5.7.14** - Autenticação e autorização
- ✅ **JWT 0.11.x** - Tokens stateless
- ✅ **Swagger/OpenAPI 3.0** - Documentação automática da API
- ✅ **Maven 3.9.6** - Gerenciador de dependências e build
- ✅ **Lombok 1.18.20** - Redução de boilerplate

**Frontend:**
- ✅ **HTML5** - Markup semântico
- ✅ **Bootstrap 5** - Framework CSS responsivo
- ✅ **JavaScript Vanilla** - Lógica frontend
- ✅ **Fetch API** - Requisições HTTP

**Infraestrutura:**
- ✅ **Docker** - Containerização
- ✅ **Docker Compose** - Orquestração local

### 1.2 Estrutura de Diretórios

```
sghss-vidaplus/
├── src/
│   ├── main/
│   │   ├── java/com/vidaplus/sghss/
│   │   │   ├── SghssApplication.java      (Classe principal)
│   │   │   ├── config/                    (Configurações)
│   │   │   │   ├── ExternalApiConfig.java
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── JwtConfig.java
│   │   │   ├── controller/                (REST Controllers)
│   │   │   │   ├── PacienteController.java
│   │   │   │   ├── MedicoController.java
│   │   │   │   ├── ConsultaController.java
│   │   │   │   ├── ProntuarioController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   └── ExternalApiController.java
│   │   │   ├── model/                     (JPA Entities)
│   │   │   │   ├── Paciente.java
│   │   │   │   ├── Medico.java
│   │   │   │   ├── Consulta.java
│   │   │   │   ├── Prontuario.java
│   │   │   │   ├── ProfissionalSaude.java
│   │   │   │   ├── Medicamento.java
│   │   │   │   ├── Prescricao.java
│   │   │   │   ├── Usuario.java
│   │   │   │   ├── AgendaMedico.java
│   │   │   │   ├── AnexoProntuario.java
│   │   │   │   └── MovimentacaoEstoque.java
│   │   │   ├── repository/                (Spring Data JPA)
│   │   │   │   ├── PacienteRepository.java
│   │   │   │   ├── MedicoRepository.java
│   │   │   │   ├── ConsultaRepository.java
│   │   │   │   ├── ProntuarioRepository.java
│   │   │   │   ├── UsuarioRepository.java
│   │   │   │   ├── PrescricaoRepository.java
│   │   │   │   ├── MedicamentoRepository.java
│   │   │   │   └── AuditoriaRepository.java
│   │   │   ├── service/                   (Business Logic)
│   │   │   │   ├── PacienteService.java
│   │   │   │   ├── MedicoService.java
│   │   │   │   ├── ConsultaService.java
│   │   │   │   ├── ProntuarioService.java
│   │   │   │   ├── UsuarioService.java
│   │   │   │   ├── AuditoriaService.java
│   │   │   │   └── ExternalApiService.java
│   │   │   ├── security/
│   │   │   │   ├── JwtTokenProvider.java  (JWT utils)
│   │   │   │   ├── AuthenticationFilter.java
│   │   │   │   └── UserDetailsServiceImpl.java
│   │   │   ├── dto/                       (Data Transfer Objects)
│   │   │   │   ├── PacienteDTO.java
│   │   │   │   ├── LoginDTO.java
│   │   │   │   ├── JwtResponseDTO.java
│   │   │   │   └── ErrorResponseDTO.java
│   │   │   ├── exception/                 (Custom Exceptions)
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   ├── ValidationException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── utils/                     (Utilitários)
│   │   │       ├── ValidationUtils.java
│   │   │       └── DateUtils.java
│   │   └── resources/
│   │       ├── application.yml            (Configuração principal)
│   │       ├── application-prod.yml       (Produção)
│   │       ├── application-test.yml       (Testes)
│   │       ├── static/
│   │       │   ├── index.html             (Frontend)
│   │       │   ├── css/
│   │       │   │   └── style.css
│   │       │   └── js/
│   │       │       └── app.js
│   │       └── db/migration/              (Flyway migrations)
│   │           └── V1_0__init.sql
│   ├── test/
│   │   ├── java/com/vidaplus/sghss/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   └── repository/
│   │   └── resources/
│   │       └── application-test.yml
├── pom.xml                                (Maven configuration)
├── Dockerfile
├── docker-compose.yml
├── frontend/                              (Frontend app)
│   ├── src/
│   │   ├── App.js
│   │   └── index.html
│   └── package.json
├── docs/
│   ├── API_Documentation.md
│   ├── Database_Schema.md
│   └── SETUP.md
└── projeto-documentacao/
    ├── FASE_1_PLANEJAMENTO.md
    ├── FASE_2_MODELAGEM.md
    ├── FASE_3_IMPLEMENTACAO.md
    ├── FASE_4_TESTES.md
    └── PDF_FINAL.md
```

---

## 2. IMPLEMENTAÇÃO DO BACK-END

### 2.1 Configuração Spring Boot (application.yml)

```yaml
spring:
  application:
    name: sghss-vidaplus

  datasource:
    url: jdbc:mysql://localhost:3306/sghss_vidaplus?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: root
    password: 159753
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 20000
      idle-timeout: 300000

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true
        generate_statistics: false
    open-in-view: false

  jackson:
    default-property-inclusion: non_null
    serialization:
      write-dates-as-timestamps: false

server:
  servlet:
    context-path: /sghss
  port: 8080
  compression:
    enabled: true
    min-response-size: 1024

logging:
  level:
    root: INFO
    com.vidaplus.sghss: DEBUG
    org.springframework.security: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %logger{36} - %msg%n"

# JWT Configuration
app:
  jwt:
    secret: your-secret-key-change-in-production-at-least-32-chars
    expiration: 3600000  # 1 hour in milliseconds

# Swagger/OpenAPI Configuration
springdoc:
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
  api-docs:
    path: /v3/api-docs
```

### 2.2 Entity - Paciente

```java
package com.vidaplus.sghss.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "paciente")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do paciente é obrigatório")
    @Size(min = 3, max = 100)
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Column(unique = true, nullable = false)
    @Size(min = 11, max = 14)
    private String cpf;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(nullable = false)
    private String telefone;

    @Column(length = 255)
    private String endereco;

    @Column(length = 500)
    private String historicoPrincipal;

    @Column(name = "data_registro", updatable = false)
    private LocalDateTime dataRegistro;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "deletado_em")
    private LocalDateTime deletadoEm;

    @PrePersist
    protected void onCreate() {
        dataRegistro = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    // Métodos de negócio
    public boolean isAtivo() {
        return deletadoEm == null;
    }

    public void deletar() {
        this.deletadoEm = LocalDateTime.now();
    }
}
```

### 2.3 Entity - Consulta

```java
package com.vidaplus.sghss.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consulta")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @NotNull(message = "Data da consulta é obrigatória")
    @Column(nullable = false)
    private LocalDateTime dataConsulta;

    @NotBlank(message = "Tipo de consulta é obrigatório")
    @Column(nullable = false)
    private String tipoConsulta; // "presencial", "telemedicina"

    @Column(nullable = false)
    @Builder.Default
    private String status = "agendada"; // "agendada", "confirmada", "realizada", "cancelada"

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(length = 500)
    private String motivoCancelamento;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public boolean podeSerConfirmada() {
        return "agendada".equals(status) && 
               dataConsulta.isAfter(LocalDateTime.now().plusHours(2));
    }

    public boolean podeSerCancelada() {
        return !"realizada".equals(status) && !"cancelada".equals(status);
    }
}
```

### 2.4 Entity - Prontuário

```java
package com.vidaplus.sghss.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prontuario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "consulta_id", nullable = false, unique = true)
    private Consulta consulta;

    @Column(columnDefinition = "TEXT")
    private String anamnese; // História do paciente

    @Column(columnDefinition = "TEXT")
    private String diagnostico;

    @Column(columnDefinition = "TEXT")
    private String tratamento;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @OneToMany(mappedBy = "prontuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Prescricao> prescricoes = new ArrayList<>();

    @OneToMany(mappedBy = "prontuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AnexoProntuario> anexos = new ArrayList<>();

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "assinado_em")
    private LocalDateTime assinadoEm;

    @Column(name = "assinado_por")
    private Long assinadoPorId; // ID do médico que assinou

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public boolean isAssinado() {
        return assinadoEm != null;
    }

    public void assinar(Long medicoId) {
        this.assinadoEm = LocalDateTime.now();
        this.assinadoPorId = medicoId;
    }
}
```

### 2.5 Controller - PacienteController

```java
package com.vidaplus.sghss.controller;

import com.vidaplus.sghss.model.Paciente;
import com.vidaplus.sghss.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/pacientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PacienteController {

    private final PacienteService pacienteService;

    /**
     * GET /api/v1/pacientes
     * Listar todos os pacientes com paginação
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO','PACIENTE')")
    public ResponseEntity<Page<Paciente>> listarTodos(Pageable pageable) {
        Page<Paciente> pacientes = pacienteService.listarAtivos(pageable);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * POST /api/v1/pacientes
     * Cadastrar novo paciente
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PACIENTE')")
    public ResponseEntity<Paciente> criar(@Valid @RequestBody Paciente paciente) {
        Paciente novoPaciente = pacienteService.criar(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPaciente);
    }

    /**
     * GET /api/v1/pacientes/{id}
     * Obter detalhes de um paciente
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO','PACIENTE')")
    public ResponseEntity<Paciente> obterPorId(@PathVariable Long id) {
        Paciente paciente = pacienteService.obterPorId(id);
        return ResponseEntity.ok(paciente);
    }

    /**
     * GET /api/v1/pacientes/cpf/{cpf}
     * Buscar paciente por CPF
     */
    @GetMapping("/cpf/{cpf}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    public ResponseEntity<Paciente> obterPorCpf(@PathVariable String cpf) {
        Paciente paciente = pacienteService.obterPorCpf(cpf);
        return ResponseEntity.ok(paciente);
    }

    /**
     * PUT /api/v1/pacientes/{id}
     * Atualizar paciente
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PACIENTE')")
    public ResponseEntity<Paciente> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Paciente paciente) {
        Paciente atualizado = pacienteService.atualizar(id, paciente);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/v1/pacientes/{id}
     * Deletar paciente (soft delete LGPD)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pacienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
```

### 2.6 Service - PacienteService

```java
package com.vidaplus.sghss.service;

import com.vidaplus.sghss.exception.ResourceNotFoundException;
import com.vidaplus.sghss.model.Paciente;
import com.vidaplus.sghss.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final AuditoriaService auditoriaService;

    public Page<Paciente> listarAtivos(Pageable pageable) {
        return pacienteRepository.findByDeletadoEmIsNull(pageable);
    }

    public Paciente criar(Paciente paciente) {
        // Validar se CPF já existe
        pacienteRepository.findByCpf(paciente.getCpf())
                .ifPresent(p -> {
                    throw new IllegalArgumentException("CPF já cadastrado");
                });

        Paciente novo = pacienteRepository.save(paciente);
        auditoriaService.registrar("CREATE", "PACIENTE", novo.getId(), 
                                   "Paciente " + novo.getNome() + " cadastrado");
        return novo;
    }

    public Paciente obterPorId(Long id) {
        return pacienteRepository.findByIdAndDeletadoEmIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));
    }

    public Paciente obterPorCpf(String cpf) {
        return pacienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));
    }

    public Paciente atualizar(Long id, Paciente pacientAtualizado) {
        Paciente paciente = obterPorId(id);
        
        paciente.setNome(pacientAtualizado.getNome());
        paciente.setTelefone(pacientAtualizado.getTelefone());
        paciente.setEmail(pacientAtualizado.getEmail());
        paciente.setEndereco(pacientAtualizado.getEndereco());

        Paciente atualizado = pacienteRepository.save(paciente);
        auditoriaService.registrar("UPDATE", "PACIENTE", id, 
                                   "Dados do paciente atualizados");
        return atualizado;
    }

    public void deletar(Long id) {
        Paciente paciente = obterPorId(id);
        paciente.deletar(); // Soft delete
        pacienteRepository.save(paciente);
        auditoriaService.registrar("DELETE", "PACIENTE", id, 
                                   "Paciente deletado (soft delete LGPD)");
    }
}
```

### 2.7 Repository - PacienteRepository

```java
package com.vidaplus.sghss.repository;

import com.vidaplus.sghss.model.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    // Buscar paciente ativo por ID
    Optional<Paciente> findByIdAndDeletadoEmIsNull(Long id);

    // Buscar paciente por CPF
    Optional<Paciente> findByCpf(String cpf);

    // Listar todos os ativos com paginação
    Page<Paciente> findByDeletadoEmIsNull(Pageable pageable);

    // Buscar por nome (case-insensitive)
    Page<Paciente> findByNomeIgnoreCaseContainingAndDeletadoEmIsNull(String nome, Pageable pageable);
}
```

### 2.8 Controller - AuthController

```java
package com.vidaplus.sghss.controller;

import com.vidaplus.sghss.dto.LoginDTO;
import com.vidaplus.sghss.dto.JwtResponseDTO;
import com.vidaplus.sghss.security.JwtTokenProvider;
import com.vidaplus.sghss.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    /**
     * POST /api/v1/auth/login
     * Autenticar usuário e retornar JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            var usuario = usuarioService.obterPorEmail(loginDTO.getEmail());
            
            if (!passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
                throw new BadCredentialsException("Credenciais inválidas");
            }

            String token = jwtTokenProvider.gerarToken(usuario.getId(), usuario.getEmail(), usuario.getRole());

            return ResponseEntity.ok(JwtResponseDTO.builder()
                    .token(token)
                    .type("Bearer")
                    .expiresIn(jwtTokenProvider.getExpirationMs() / 1000)
                    .usuarioId(usuario.getId())
                    .email(usuario.getEmail())
                    .role(usuario.getRole())
                    .build());
        } catch (Exception e) {
            throw new BadCredentialsException("Email ou senha inválidos");
        }
    }

    /**
     * POST /api/v1/auth/logout
     * Fazer logout (invalida token no frontend)
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout realizado com sucesso");
    }
}
```

### 2.9 Security - JwtTokenProvider

```java
package com.vidaplus.sghss.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${app.jwt.secret:your-secret-key-change-in-production-at-least-32-chars}")
    private String secretKey;

    @Getter
    @Value("${app.jwt.expiration:3600000}")
    private long expirationMs;

    /**
     * Gerar JWT token
     */
    public String gerarToken(Long usuarioId, String email, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .setSubject(email)
                .claim("usuarioId", usuarioId)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validar e extrair dados do token
     */
    public Claims extrairClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expirado");
        } catch (JwtException e) {
            throw new RuntimeException("Token inválido");
        }
    }

    public String obterEmailDoToken(String token) {
        return extrairClaims(token).getSubject();
    }

    public Long obterUsuarioIdDoToken(String token) {
        return extrairClaims(token).get("usuarioId", Long.class);
    }

    public String obterRoleDoToken(String token) {
        return extrairClaims(token).get("role", String.class);
    }

    public boolean validarToken(String token) {
        try {
            extrairClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
```

### 2.10 Exception Handler

```java
package com.vidaplus.sghss.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDTO.builder()
                        .status(404)
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDTO.builder()
                        .status(400)
                        .message("Erro de validação")
                        .errors(errors)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseDTO.builder()
                        .status(500)
                        .message("Erro interno do servidor")
                        .build());
    }
}
```

---

## 3. IMPLEMENTAÇÃO DO FRONT-END BÁSICO

### 3.1 HTML - index.html

```html
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SGHSS - Sistema de Gestão Hospitalar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">SGHSS - VidaPlus</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item"><a class="nav-link" href="#" onclick="showLogin()">Login</a></li>
                    <li class="nav-item"><a class="nav-link" href="#" onclick="showDashboard()">Dashboard</a></li>
                    <li class="nav-item"><a class="nav-link" href="#" onclick="logout()">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <!-- Login Form -->
        <div id="loginForm" class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h5>Login - SGHSS</h5>
                    </div>
                    <div class="card-body">
                        <form onsubmit="fazerLogin(event)">
                            <div class="mb-3">
                                <label class="form-label">Email</label>
                                <input type="email" id="email" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Senha</label>
                                <input type="password" id="senha" class="form-control" required>
                            </div>
                            <button type="submit" class="btn btn-primary w-100">Entrar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Dashboard -->
        <div id="dashboard" style="display: none;">
            <h2>Dashboard - SGHSS</h2>
            <div class="row mt-4">
                <div class="col-md-3">
                    <div class="card text-center">
                        <div class="card-body">
                            <h5 class="card-title">Pacientes</h5>
                            <p id="pacienteCount" class="card-text">-</p>
                            <a href="#" onclick="listarPacientes()" class="btn btn-sm btn-primary">Ver</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center">
                        <div class="card-body">
                            <h5 class="card-title">Consultas</h5>
                            <p id="consultaCount" class="card-text">-</p>
                            <a href="#" onclick="listarConsultas()" class="btn btn-sm btn-primary">Ver</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center">
                        <div class="card-body">
                            <h5 class="card-title">Médicos</h5>
                            <p id="medicoCount" class="card-text">-</p>
                            <a href="#" onclick="listarMedicos()" class="btn btn-sm btn-primary">Ver</a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Lista de Pacientes -->
            <div id="pacientesSection" style="display: none;" class="mt-5">
                <h3>Pacientes Cadastrados</h3>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>CPF</th>
                            <th>Email</th>
                            <th>Telefone</th>
                        </tr>
                    </thead>
                    <tbody id="pacientesTable">
                    </tbody>
                </table>
            </div>

            <!-- Lista de Consultas -->
            <div id="consultasSection" style="display: none;" class="mt-5">
                <h3>Consultas Agendadas</h3>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Paciente</th>
                            <th>Médico</th>
                            <th>Data</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody id="consultasTable">
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/js/app.js"></script>
</body>
</html>
```

### 3.2 JavaScript - app.js

```javascript
const API_BASE_URL = 'http://localhost:8080/sghss/api/v1';
let jwtToken = null;

/**
 * Fazer login
 */
function fazerLogin(event) {
    event.preventDefault();
    
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;

    fetch(`${API_BASE_URL}/auth/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, senha })
    })
    .then(response => {
        if (!response.ok) throw new Error('Login falhou');
        return response.json();
    })
    .then(data => {
        jwtToken = data.token;
        localStorage.setItem('jwtToken', jwtToken);
        alert('Login realizado com sucesso!');
        showDashboard();
    })
    .catch(error => {
        alert('Erro ao fazer login: ' + error.message);
    });
}

/**
 * Mostrar tela de login
 */
function showLogin() {
    document.getElementById('loginForm').style.display = 'block';
    document.getElementById('dashboard').style.display = 'none';
}

/**
 * Mostrar dashboard
 */
function showDashboard() {
    if (!jwtToken) {
        jwtToken = localStorage.getItem('jwtToken');
    }
    
    if (!jwtToken) {
        alert('Por favor, faça login primeiro');
        showLogin();
        return;
    }

    document.getElementById('loginForm').style.display = 'none';
    document.getElementById('dashboard').style.display = 'block';
    carregarDados();
}

/**
 * Fazer logout
 */
function logout() {
    jwtToken = null;
    localStorage.removeItem('jwtToken');
    showLogin();
    alert('Logout realizado');
}

/**
 * Carregar dados do dashboard
 */
function carregarDados() {
    carregarPacientes();
    carregarConsultas();
    carregarMedicos();
}

/**
 * Listar pacientes
 */
function listarPacientes() {
    fetch(`${API_BASE_URL}/pacientes`, {
        headers: {
            'Authorization': `Bearer ${jwtToken}`
        }
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('pacientesSection').style.display = 'block';
        document.getElementById('consultasSection').style.display = 'none';
        
        const tbody = document.getElementById('pacientesTable');
        tbody.innerHTML = '';
        
        data.content.forEach(paciente => {
            tbody.innerHTML += `
                <tr>
                    <td>${paciente.id}</td>
                    <td>${paciente.nome}</td>
                    <td>${paciente.cpf}</td>
                    <td>${paciente.email}</td>
                    <td>${paciente.telefone}</td>
                </tr>
            `;
        });
        
        document.getElementById('pacienteCount').textContent = data.totalElements;
    })
    .catch(error => console.error('Erro:', error));
}

/**
 * Carregar contagem de pacientes
 */
function carregarPacientes() {
    fetch(`${API_BASE_URL}/pacientes?page=0&size=1`, {
        headers: {
            'Authorization': `Bearer ${jwtToken}`
        }
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('pacienteCount').textContent = data.totalElements;
    })
    .catch(error => console.error('Erro ao carregar pacientes:', error));
}

/**
 * Listar consultas
 */
function listarConsultas() {
    fetch(`${API_BASE_URL}/consultas`, {
        headers: {
            'Authorization': `Bearer ${jwtToken}`
        }
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('consultasSection').style.display = 'block';
        document.getElementById('pacientesSection').style.display = 'none';
        
        const tbody = document.getElementById('consultasTable');
        tbody.innerHTML = '';
        
        data.content.forEach(consulta => {
            tbody.innerHTML += `
                <tr>
                    <td>${consulta.id}</td>
                    <td>${consulta.pacienteNome}</td>
                    <td>${consulta.medicoNome}</td>
                    <td>${new Date(consulta.dataConsulta).toLocaleDateString('pt-BR')}</td>
                    <td><span class="badge bg-info">${consulta.status}</span></td>
                </tr>
            `;
        });
        
        document.getElementById('consultaCount').textContent = data.totalElements;
    })
    .catch(error => console.error('Erro:', error));
}

/**
 * Carregar contagem de consultas
 */
function carregarConsultas() {
    fetch(`${API_BASE_URL}/consultas?page=0&size=1`, {
        headers: {
            'Authorization': `Bearer ${jwtToken}`
        }
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('consultaCount').textContent = data.totalElements;
    })
    .catch(error => console.error('Erro ao carregar consultas:', error));
}

/**
 * Listar médicos
 */
function listarMedicos() {
    fetch(`${API_BASE_URL}/medicos`, {
        headers: {
            'Authorization': `Bearer ${jwtToken}`
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log('Médicos:', data);
    })
    .catch(error => console.error('Erro:', error));
}

/**
 * Carregar contagem de médicos
 */
function carregarMedicos() {
    fetch(`${API_BASE_URL}/medicos?page=0&size=1`, {
        headers: {
            'Authorization': `Bearer ${jwtToken}`
        }
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('medicoCount').textContent = data.totalElements;
    })
    .catch(error => console.error('Erro ao carregar médicos:', error));
}

// Ao carregar a página
window.addEventListener('load', () => {
    const token = localStorage.getItem('jwtToken');
    if (token) {
        jwtToken = token;
        showDashboard();
    } else {
        showLogin();
    }
});
```

### 3.3 CSS - style.css

```css
:root {
    --primary-color: #007bff;
    --secondary-color: #6c757d;
    --success-color: #28a745;
    --danger-color: #dc3545;
    --warning-color: #ffc107;
    --info-color: #17a2b8;
}

* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #f5f5f5;
    color: #333;
}

.navbar {
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.container {
    max-width: 1200px;
    margin: 0 auto;
}

.card {
    border: none;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    border-radius: 8px;
    transition: transform 0.2s, box-shadow 0.2s;
}

.card:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0,0,0,0.15);
}

.btn {
    border-radius: 4px;
    font-weight: 500;
    transition: all 0.2s;
}

.btn:hover {
    transform: translateY(-1px);
    box-shadow: 0 2px 8px rgba(0,0,0,0.2);
}

.table {
    background-color: white;
    border-radius: 8px;
    overflow: hidden;
}

.table-striped > tbody > tr:nth-of-type(odd) {
    background-color: #f9f9f9;
}

.badge {
    padding: 0.5rem 0.75rem;
    border-radius: 4px;
}

h2, h3, h5 {
    color: #007bff;
    font-weight: 600;
}

/* Responsivo */
@media (max-width: 768px) {
    .container {
        padding: 0 15px;
    }
    
    .card {
        margin-bottom: 15px;
    }
    
    .table {
        font-size: 0.875rem;
    }
}
```

---

## 4. ENDPOINTS PRINCIPAIS DOCUMENTADOS

### 4.1 Documentação Swagger/OpenAPI

A API está documentada automaticamente pelo Swagger OpenAPI 3.0:

**URL:** `http://localhost:8080/sghss/swagger-ui.html`

Principais endpoints:

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/auth/login` | Autenticar | ❌ |
| GET | `/pacientes` | Listar pacientes | ✅ |
| POST | `/pacientes` | Cadastrar paciente | ✅ |
| GET | `/pacientes/{id}` | Obter paciente | ✅ |
| PUT | `/pacientes/{id}` | Atualizar paciente | ✅ |
| DELETE | `/pacientes/{id}` | Deletar paciente | ✅ |
| GET | `/consultas` | Listar consultas | ✅ |
| POST | `/consultas` | Agendar consulta | ✅ |
| GET | `/medicos` | Listar médicos | ✅ |

---

## 5. FLUXO DE EXECUÇÃO

### 5.1 Como Executar o Projeto

```bash
# 1. Clonar repositório
git clone https://github.com/Marcio606/sghss-vidaplus.git
cd sghss-vidaplus

# 2. Configurar banco de dados
# Editar src/main/resources/application.yml com credenciais MySQL

# 3. Compilar com Maven
mvn clean install

# 4. Executar aplicação
mvn spring-boot:run

# 5. Acessar
# Frontend: http://localhost:8080/sghss/
# Swagger: http://localhost:8080/sghss/swagger-ui.html
```

### 5.2 Fluxo de Requisição - Exemplo

```
CLIENTE (Navegador)
  │
  ├─ POST /sghss/api/v1/auth/login
  │  Email: medico@vidaplus.com
  │  Senha: Senha123!
  │
  └─ Spring Security → AuthController → UsuarioService
     └─ Valida credenciais contra banco de dados
     └─ JwtTokenProvider gera token JWT
     └─ Retorna: { token: "eyJh...", expiresIn: 3600 }
  │
  ├─ localStorage.setItem('jwtToken', token)
  │
  ├─ GET /sghss/api/v1/pacientes
  │  Headers: Authorization: Bearer eyJh...
  │
  └─ SecurityFilter → JwtTokenProvider.validarToken()
     └─ Token válido? SIM
     └─ PacienteController → PacienteService
     └─ PacienteRepository.findByDeletadoEmIsNull()
     └─ MySQL: SELECT * FROM paciente WHERE deletado_em IS NULL
     └─ Retorna: Page<Paciente> com 10 registros
  │
  ├─ Renderiza tabela no frontend com dados
```

---

## 6. CONCLUSÃO DA FASE 3

### Entregáveis
✅ Back-end Spring Boot 2.7.14 completo  
✅ Entidades JPA com relacionamentos  
✅ Controllers REST com validações  
✅ Services com lógica de negócio  
✅ Repositories Spring Data  
✅ Autenticação JWT  
✅ Exception handling  
✅ Frontend básico com HTML/CSS/JS  
✅ Integração com MySQL  
✅ Documentação Swagger/OpenAPI  

### Tecnologias em Produção
- Spring Boot, Spring Security, Spring Data JPA
- MySQL 8.0, Hibernate
- JWT, BCrypt
- Bootstrap 5, Fetch API
- Docker, Maven

### Próximos Passos (Fase 4)
- Criar casos de teste (JUnit, Mockito)
- Testes de integração
- Testes de performance (JMeter)
- Testes de segurança (OWASP ZAP)
- Testes de usabilidade

---

**Documento: FASE 3 - Implementação**  
**Data:** Dezembro 2025  
**Autor:** Marcio Machado Moreira (RU: 4543545)  
**Status:** ✅ Concluído
