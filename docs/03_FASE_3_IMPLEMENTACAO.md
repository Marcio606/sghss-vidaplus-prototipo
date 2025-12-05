# FASE 3: IMPLEMENTAÇÃO (BACK-END + FRONT-END BÁSICO)

**Aluno:** Marcio Machado Moreira  
**R.U:** 4543545  
**Professor:** Prof. Winston Sen Lun Fung, Me.  

---

## 1. Visão Geral
Documento descreve a implementação realizada: código Spring Boot, controllers, services, repositories, DTOs, segurança JWT, configurações, e o front-end básico em `frontend/`.

## 2. Como compilar e executar (desenvolvimento)
1. Pré-requisitos:
   - Java 11
   - Maven 3.9+
   - Docker (opcional)
   - MySQL 8.0
2. Variáveis de ambiente (usar `.env.example` como base)
3. Build e run:
```powershell
cd C:\Users\Marcio Moreira\Documents\sghss-vidaplus
mvnw.cmd clean package -DskipTests
java -jar target\sghss-vida-plus-0.0.1-SNAPSHOT.jar
```

## 3. Principais pacotes and classes
- `com.sghss.config` — Configurações (SecurityConfig, CorsConfig)
- `com.sghss.security` — JWT provider, filters
- `com.sghss.controller` — Endpoints REST (PacienteController, ConsultaController, etc.)
- `com.sghss.service` — Regras de negócio
- `com.sghss.repository` — Spring Data JPA
- `com.sghss.entity` — JPA Entities

## 4. Configurações importantes
- `application.yml` (exemplo em `docs/.env.example`)
- JWT secret guardado via variável de ambiente em produção
- HikariCP para pool de conexões
- CORS restrito por origem em produção

## 5. Segurança
- Autenticação stateless via JWT
- Permissões via `@PreAuthorize` nas controllers
- Senhas armazenadas com BCrypt
- Auditoria de operações sensíveis

## 6. API (exemplos de requisição)
- Criar paciente (ADMIN)
  - POST /api/pacientes
  - Body: { "nome": "Ana", "cpf": "12345678901", "dataNascimento": "1990-01-01" }

- Autenticação
  - POST /api/auth/login -> recebe token

## 7. Front-end Básico
- Código em `frontend/` com `index.html`, `app.js` e `styles.css`.
- Consome endpoints via Fetch API com token Bearer.

## 8. Observações de produção
- Substituir `jwt.secret` por secret gerado seguro
- Habilitar HTTPS e configurar proxy reverso (Nginx)

---

**Fim da FASE 3**
