# FASE 2: MODELAGEM E ARQUITETURA

## 1. ANÁLISE DE REQUISITOS DETALHADA

### 1.1 Requisitos Funcionais Completos

#### **MÓDULO 1: GESTÃO DE PACIENTES (RF001-RF010)**

| ID | Descrição | Tipo | Prioridade | Critério de Aceitação |
|----|-----------|------|-----------|----------------------|
| RF001 | Cadastrar novo paciente | Funcional | **ALTA** | Registrar nome, CPF, data nascimento, contato; validar CPF único |
| RF002 | Atualizar dados do paciente | Funcional | **ALTA** | Permitir edição de telefone, endereço, contato emergência |
| RF003 | Deletar paciente (soft delete) | Funcional | **MÉDIA** | Marcar como deletado (LGPD), manter histórico |
| RF004 | Buscar paciente por CPF | Funcional | **ALTA** | Retornar dados completos em < 100ms |
| RF005 | Buscar paciente por nome | Funcional | **ALTA** | Busca case-insensitive, suportar wildcards |
| RF006 | Listar todos os pacientes ativos | Funcional | **MÉDIA** | Com paginação (10, 25, 50 por página) |
| RF007 | Visualizar histórico clínico completo | Funcional | **ALTA** | Cronologia de consultas, prontuários, medicações |
| RF008 | Exportar histórico paciente (PDF) | Funcional | **BAIXA** | Gerar documento com dados clínicos |
| RF009 | Visualizar agendamentos do paciente | Funcional | **ALTA** | Próximas 30 dias + histórico passado |
| RF010 | Cancelar agendamento (paciente) | Funcional | **MÉDIA** | Com notificação ao médico |

#### **MÓDULO 2: GESTÃO DE CONSULTAS (RF011-RF020)**

| ID | Descrição | Tipo | Prioridade | Critério de Aceitação |
|----|-----------|------|-----------|----------------------|
| RF011 | Agendar consulta (paciente) | Funcional | **ALTA** | Escolher médico, data, horário disponível |
| RF012 | Gerenciar agenda (médico) | Funcional | **ALTA** | Visualizar consultas do dia, próximos 30 dias |
| RF013 | Definir horários disponíveis (médico) | Funcional | **MÉDIA** | Slot de 30min, início às 08h, fim às 18h |
| RF014 | Confirmar/rejeitar consulta (médico) | Funcional | **MÉDIA** | Com notificação ao paciente |
| RF015 | Listar consultas por período | Funcional | **MÉDIA** | Relatório médico/paciente/data |
| RF016 | Registrar comparecimento (médico) | Funcional | **ALTA** | Check-in da consulta, liberar prontuário |
| RF017 | Redigir diagnóstico na consulta | Funcional | **ALTA** | Campo texto, salvar em prontuário |
| RF018 | Registrar prescrição na consulta | Funcional | **ALTA** | Medicamentos, dosagem, duração |
| RF019 | Notificar paciente (SMS/Email) | Funcional | **BAIXA** | Lembrança 24h antes da consulta |
| RF020 | Teleconsulta (videochamada) | Funcional | **BAIXA** | Link WebRTC, gravar consulta |

#### **MÓDULO 3: PRONTUÁRIOS ELETRÔNICOS (RF021-RF030)**

| ID | Descrição | Tipo | Prioridade | Critério de Aceitação |
|----|-----------|------|-----------|----------------------|
| RF021 | Criar prontuário para consulta | Funcional | **ALTA** | Iniciar após agendamento/confirmação |
| RF022 | Registrar anamnese (história) | Funcional | **MÉDIA** | Campo texto, histórico medicamentoso |
| RF023 | Registrar diagnóstico ICD-10 | Funcional | **ALTA** | Validar código ICD-10 |
| RF024 | Registrar tratamento/conduta | Funcional | **ALTA** | Repouso, medicamentos, encaminhamentos |
| RF025 | Anexar documento (exame, imagem) | Funcional | **MÉDIA** | Upload PDF, JPG, PNG < 5MB |
| RF026 | Visualizar prontuário histórico | Funcional | **ALTA** | Acesso completo para médico/paciente |
| RF027 | Assinar prontuário (médico) | Funcional | **ALTA** | Validar assinatura digital |
| RF028 | Auditoria de acessos ao prontuário | Funcional | **ALTA** | Registrar quem acessou, quando, de onde |
| RF029 | Exportar prontuário (PDF/XML) | Funcional | **MÉDIA** | Formato padronizado HL7 |
| RF030 | Pesquisar em prontuários (full-text) | Funcional | **BAIXA** | Buscar por sintomas, diagnósticos |

#### **MÓDULO 4: GESTÃO DE PROFISSIONAIS (RF031-RF040)**

| ID | Descrição | Tipo | Prioridade | Critério de Aceitação |
|----|-----------|------|-----------|----------------------|
| RF031 | Cadastrar profissional (médico/enfermeiro) | Funcional | **ALTA** | Nome, CRM/COREN, especialidade, contato |
| RF032 | Validar CRM junto a Conselho | Funcional | **MÉDIA** | Integração com API de validação |
| RF033 | Listar médicos por especialidade | Funcional | **ALTA** | Filtro por especialidade |
| RF034 | Visualizar grade de horários (médico) | Funcional | **ALTA** | View semanal/mensal |
| RF035 | Definir férias/afastamentos (médico) | Funcional | **MÉDIA** | Bloquear agenda |
| RF036 | Gerar relatório de produtividade (médico) | Funcional | **MÉDIA** | Consultas/mês, pacientes atendidos |
| RF037 | Avaliar desempenho profissional | Funcional | **BAIXA** | Notas, comentários de pacientes |
| RF038 | Gerenciar escala de plantão | Funcional | **BAIXA** | Para médicos de urgência/emergência |
| RF039 | Histórico de treinamentos | Funcional | **BAIXA** | Registrar certificações |
| RF040 | Bloquear acesso de profissional | Funcional | **MÉDIA** | Desativar usuário mantendo histórico |

#### **MÓDULO 5: ADMINISTRAÇÃO & RELATÓRIOS (RF041-RF050)**

| ID | Descrição | Tipo | Prioridade | Critério de Aceitação |
|----|-----------|------|-----------|----------------------|
| RF041 | Dashboard administrativo | Funcional | **ALTA** | KPIs: pacientes ativos, consultas/dia, taxa ocupação |
| RF042 | Relatório de pacientes ativos | Funcional | **MÉDIA** | Filtrar por período, status, diagnóstico |
| RF043 | Relatório de consultas realizadas | Funcional | **MÉDIA** | Dados por médico, especialidade, período |
| RF044 | Relatório financeiro (faturamento) | Funcional | **BAIXA** | Consultas faturadas, procedimentos, totais |
| RF045 | Controle de acesso por perfil | Funcional | **ALTA** | Roles: ADMIN, MEDICO, PACIENTE, ENFERMEIRO |
| RF046 | Gestão de usuários (criar/editar) | Funcional | **ALTA** | CRUD de usuários, reset de senha |
| RF047 | Auditoria de sistema | Funcional | **ALTA** | Logs de todas ações, quem fez, quando |
| RF048 | Backup automático do banco | Funcional | **ALTA** | Diário, retenção 30 dias |
| RF049 | Recuperação de dados deletados | Funcional | **MÉDIA** | Restore do backup (LGPD) |
| RF050 | Notificações de sistema | Funcional | **MÉDIA** | Email de alertas críticos |

---

### 1.2 Requisitos Não-Funcionais

#### **PERFORMANCE (RNF001-RNF005)**

| ID | Descrição | Métrica | Prioridade |
|----|-----------|---------|-----------|
| RNF001 | Tempo resposta API | < 200ms (p95) | **CRÍTICA** |
| RNF002 | Tempo carregamento dashboard | < 2s | **ALTA** |
| RNF003 | Suportar 1000+ usuários simultâneos | Throughput 100 req/s | **ALTA** |
| RNF004 | Otimizar queries banco de dados | < 1s para 1M registros | **MÉDIA** |
| RNF005 | Cache de dados frequentes | Redis com TTL 1h | **MÉDIA** |

#### **SEGURANÇA (RNF006-RNF015)**

| ID | Descrição | Implementação | Prioridade |
|----|-----------|---------------|-----------|
| RNF006 | Autenticação JWT | Token com expiração 1h | **CRÍTICA** |
| RNF007 | Criptografia de senhas | PBKDF2 + salt 16 bytes | **CRÍTICA** |
| RNF008 | HTTPS/TLS | TLS 1.2+ obrigatório | **CRÍTICA** |
| RNF009 | Validação de entrada | Sanitizar todas entradas | **CRÍTICA** |
| RNF010 | SQL Injection prevention | Prepared statements | **CRÍTICA** |
| RNF011 | XSS prevention | Sanitizar outputs | **ALTA** |
| RNF012 | LGPD compliance | Auditoria, direito esquecimento | **CRÍTICA** |
| RNF013 | Backup criptografado | AES-256 | **ALTA** |
| RNF014 | Rate limiting | Max 100 req/min por IP | **ALTA** |
| RNF015 | Senhas fortes | Min 8 char, maiúscula, número, especial | **MÉDIA** |

#### **DISPONIBILIDADE & CONFIABILIDADE (RNF016-RNF020)**

| ID | Descrição | Métrica | Prioridade |
|----|-----------|---------|-----------|
| RNF016 | Uptime do sistema | 99.5% (máx 3.6h/mês indisponível) | **CRÍTICA** |
| RNF017 | MTBF (Mean Time Between Failures) | > 720 horas | **ALTA** |
| RNF018 | RTO (Recovery Time Objective) | < 30 minutos | **ALTA** |
| RNF019 | RPO (Recovery Point Objective) | < 1 hora | **ALTA** |
| RNF020 | Redundância de BD | Replicação master-slave | **ALTA** |

#### **USABILIDADE & ACESSIBILIDADE (RNF021-RNF025)**

| ID | Descrição | Padrão | Prioridade |
|----|-----------|--------|-----------|
| RNF021 | Interface responsiva | Mobile-first, Bootstrap 5 | **ALTA** |
| RNF022 | Acessibilidade WCAG 2.1 | AA compliance | **MÉDIA** |
| RNF023 | Contraste de cores | Ratio 4.5:1 mínimo | **MÉDIA** |
| RNF024 | Suporte teclado | Tab navigation | **MÉDIA** |
| RNF025 | Documentação API | Swagger/OpenAPI 3.0 | **ALTA** |

#### **CONFORMIDADE REGULATÓRIA (RNF026-RNF030)**

| ID | Descrição | Requisito | Prioridade |
|----|-----------|-----------|-----------|
| RNF026 | LGPD - Lei Geral Proteção Dados | Consentimento, direito esquecimento | **CRÍTICA** |
| RNF027 | HIPAA (se aplicável) | Criptografia dados saúde | **ALTA** |
| RNF028 | Registro de auditoria | Todos acessos/mudanças | **CRÍTICA** |
| RNF029 | Conformidade médico-legal | Assinaturas digitais, timestamps | **ALTA** |
| RNF030 | Política de privacidade | Transparente, atualizada | **MÉDIA** |

---

## 2. DIAGRAMAS UML

### 2.1 Diagrama de Casos de Uso

```
┌─────────────────────────────────────────────────────────────────┐
│                      SGHSS - SISTEMA PRINCIPAL                   │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  ┌─────────────┐                                                │
│  │  PACIENTE   │                                                │
│  └──────┬──────┘                                                │
│         │                                                        │
│         │                                                        │
│    ┌────┴──────────────────────────────────────┐                │
│    │    Autenticar/Login                      │                │
│    │    Agendar Consulta                      │                │
│    │    Visualizar Prontuário                 │                │
│    │    Consultar Resultados Exames           │                │
│    │    Acessar Teleconsulta                  │                │
│    │    Gerenciar Perfil                      │                │
│    └────┬──────────────────────────────────────┘                │
│         │                                                        │
│  ┌──────┴──────┐                                                │
│  │   MEDICO    │                                                │
│  └──────┬──────┘                                                │
│         │                                                        │
│    ┌────┴──────────────────────────────────────┐                │
│    │    Gerenciar Agenda                      │                │
│    │    Registrar Diagnóstico                 │                │
│    │    Prescrever Medicamento                │                │
│    │    Consultar Prontuário Paciente         │                │
│    │    Realizar Teleconsulta                 │                │
│    │    Assinar Prontuário                    │                │
│    └────┬──────────────────────────────────────┘                │
│         │                                                        │
│  ┌──────┴──────────┐                                            │
│  │   ADMINISTRADOR │                                            │
│  └──────┬──────────┘                                            │
│         │                                                        │
│    ┌────┴──────────────────────────────────────┐                │
│    │    Gerenciar Usuários                    │                │
│    │    Gerar Relatórios                      │                │
│    │    Controlar Acesso                      │                │
│    │    Auditar Sistema                       │                │
│    │    Configurar Sistema                    │                │
│    │    Gerenciar Backup                      │                │
│    └────────────────────────────────────────┘                  │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
```

### 2.2 Diagrama de Classes (Simplificado)

```
┌───────────────────────────────────┐
│          Paciente                 │
├───────────────────────────────────┤
│ - id: Long (PK)                   │
│ - cpf: String (UNIQUE)            │
│ - nome: String                    │
│ - dataNascimento: LocalDate       │
│ - telefone: String                │
│ - email: String                   │
│ - endereco: String                │
│ - dataRegistro: LocalDateTime     │
│ - deletadoEm: LocalDateTime       │
├───────────────────────────────────┤
│ + cadastrar(): void               │
│ + atualizar(): void               │
│ + obterHistorico(): List<Consulta>│
│ + deletar(): void (soft delete)   │
└───────────────────────────────────┘
          ▲
          │ 1:N (um paciente, muitas consultas)
          │
┌─────────────────────────────────────────┐
│            Consulta                     │
├─────────────────────────────────────────┤
│ - id: Long (PK)                         │
│ - pacienteId: Long (FK)                 │
│ - medicoId: Long (FK)                   │
│ - dataConsulta: LocalDateTime           │
│ - tipoConsulta: String (presencial/...) │
│ - status: String (agendada/realizada)   │
│ - criadoEm: LocalDateTime               │
├─────────────────────────────────────────┤
│ + agendar(): void                       │
│ + confirmar(): void                     │
│ + registrarDiagnóstico(): void          │
│ + obterProntuario(): Prontuario         │
└─────────────────────────────────────────┘
          ▲
          │ 1:1 (uma consulta, um prontuário)
          │
┌──────────────────────────────┐
│       Prontuario             │
├──────────────────────────────┤
│ - id: Long (PK)              │
│ - consultaId: Long (FK)      │
│ - diagnostico: String        │
│ - tratamento: String         │
│ - medicacoes: List<Medicacao>│
│ - assinadoEm: LocalDateTime  │
├──────────────────────────────┤
│ + registrar(): void          │
│ + assinar(): void            │
│ + obterHistorico(): List     │
└──────────────────────────────┘

┌─────────────────────────────────┐
│          Medico                 │
├─────────────────────────────────┤
│ - id: Long (PK)                 │
│ - crm: String (UNIQUE)          │
│ - nome: String                  │
│ - especialidade: String         │
│ - telefone: String              │
│ - horarioDisponivel: List       │
│ - ativo: Boolean                │
├─────────────────────────────────┤
│ + cadastrar(): void             │
│ + definirHorarios(): void       │
│ + obterAgenda(): List<Consulta> │
│ + obterProdutividade(): Report  │
└─────────────────────────────────┘

┌──────────────────────────────┐
│       Usuario                │
├──────────────────────────────┤
│ - id: Long (PK)              │
│ - email: String (UNIQUE)     │
│ - senha: String (criptografada)
│ - role: String (enum)        │
│ - ativo: Boolean             │
│ - ultimoLogin: LocalDateTime │
├──────────────────────────────┤
│ + autenticar(): JWT Token    │
│ + verificarPermissao(): bool  │
│ + alterarSenha(): void       │
└──────────────────────────────┘
```

### 2.3 Diagrama Entidade-Relacionamento (DER)

```
USUARIO
├── id (PK)
├── email (UNIQUE)
├── senha
├── role
├── ativo
└── ultimoLogin

PACIENTE
├── id (PK)
├── usuarioId (FK → USUARIO)
├── cpf (UNIQUE)
├── nome
├── dataNascimento
├── telefone
├── endereco
├── dataRegistro
└── deletadoEm

MEDICO
├── id (PK)
├── usuarioId (FK → USUARIO)
├── crm (UNIQUE)
├── nome
├── especialidade
├── telefone
├── ativo
└── deletadoEm

CONSULTA
├── id (PK)
├── pacienteId (FK → PACIENTE)
├── medicoId (FK → MEDICO)
├── dataConsulta
├── tipoConsulta
├── status
└── criadoEm

PRONTUARIO
├── id (PK)
├── consultaId (FK → CONSULTA)
├── diagnostico
├── tratamento
├── assinadoEm
└── criadoEm

MEDICACAO
├── id (PK)
├── prontuarioId (FK → PRONTUARIO)
├── nomeMedicamento
├── dosagem
├── frequencia
└── duracao

AUDITORIA
├── id (PK)
├── usuarioId (FK → USUARIO)
├── acao (CREATE, READ, UPDATE, DELETE)
├── entidade (Paciente, Consulta, etc)
├── descricao
├── dataHora
└── ipOrigem

RELACOES:
- PACIENTE 1:N CONSULTA
- MEDICO 1:N CONSULTA
- CONSULTA 1:1 PRONTUARIO
- PRONTUARIO 1:N MEDICACAO
- USUARIO 1:1 PACIENTE ou MEDICO
```

---

## 3. DEFINIÇÃO DE ARQUITETURA

### 3.1 Arquitetura em Camadas (Layered Architecture)

```
┌──────────────────────────────────────────────────┐
│           PRESENTATION LAYER                     │
│  (Frontend: HTML/CSS/JS + Swagger UI)            │
├──────────────────────────────────────────────────┤
│           API LAYER (REST Controllers)           │
│  GET/POST/PUT/DELETE /api/pacientes               │
│  GET/POST/PUT/DELETE /api/consultas               │
│  GET/POST /api/prontuarios                        │
│  GET /api/medicos                                 │
├──────────────────────────────────────────────────┤
│           SERVICE LAYER (Business Logic)         │
│  PacienteService (validações, regras)            │
│  ConsultaService (agendamento, conflitos)        │
│  ProntuarioService (assinatura, auditoria)       │
│  MedicoService (agenda, disponibilidade)         │
├──────────────────────────────────────────────────┤
│           REPOSITORY LAYER (Data Access)         │
│  PacienteRepository (CRUD)                       │
│  ConsultaRepository (CRUD + queries)             │
│  ProntuarioRepository (CRUD)                     │
│  UsuarioRepository (auth, perfis)                │
├──────────────────────────────────────────────────┤
│           DATABASE LAYER                        │
│  MySQL 8.0 (ACID transactions, constraints)     │
├──────────────────────────────────────────────────┤
```

### 3.2 Camada de Segurança (Transversal)

```
┌────────────────────────────────────────┐
│     SECURITY LAYER (Cross-cutting)     │
├────────────────────────────────────────┤
│ - Authentication (JWT)                 │
│ - Authorization (Role-based access)    │
│ - Encryption (passwords, sensitive)    │
│ - Audit logging (quem, quando, o quê)  │
│ - Exception handling (error codes)     │
│ - Validation (input sanitization)      │
└────────────────────────────────────────┘
```

### 3.3 Padrões de Design Adotados

| Padrão | Aplicação | Exemplo |
|--------|-----------|---------|
| **MVC** | Separação concerns | Controller → Service → Model |
| **DAO/Repository** | Abstração de dados | PacienteRepository |
| **Service Locator** | Dependências | Autowiring Spring |
| **Builder** | Criação entidades | Lombok @Builder |
| **Singleton** | Services | @Service (bean único) |
| **Factory** | Objetos complexos | EntityFactory |
| **Decorator** | Auditoria/logs | AOP aspects |
| **Strategy** | Múltiplos algoritmos | TipoConsultaStrategy |

---

## 4. DESIGN DE ENDPOINTS DA API REST

### 4.1 Padrão de URLs (RESTful)

```
BASE_URL: http://localhost:8080/sghss/api/v1

PACIENTES:
  GET     /pacientes              → Listar todos (com paginação)
  POST    /pacientes              → Criar novo
  GET     /pacientes/{id}         → Obter detalhes
  PUT     /pacientes/{id}         → Atualizar
  DELETE  /pacientes/{id}         → Deletar (soft delete)
  GET     /pacientes/{id}/consultas → Consultas do paciente
  GET     /pacientes/cpf/{cpf}    → Buscar por CPF

CONSULTAS:
  GET     /consultas              → Listar todas (filtros: data, status)
  POST    /consultas              → Agendar nova
  GET     /consultas/{id}         → Detalhes consulta
  PUT     /consultas/{id}         → Atualizar status
  DELETE  /consultas/{id}         → Cancelar
  GET     /consultas/{id}/prontuario → Prontuário da consulta
  POST    /consultas/{id}/confirmar  → Médico confirma

PRONTUARIOS:
  GET     /prontuarios/{id}       → Obter prontuário
  POST    /prontuarios            → Criar novo
  PUT     /prontuarios/{id}       → Atualizar
  POST    /prontuarios/{id}/assinar   → Médico assina
  GET     /prontuarios/{id}/auditoria → Histórico de acessos

MEDICOS:
  GET     /medicos                → Listar médicos
  POST    /medicos                → Cadastrar novo
  GET     /medicos/{id}           → Detalhes
  GET     /medicos/{id}/agenda    → Agenda do mês
  POST    /medicos/{id}/horarios  → Definir horários
  GET     /medicos/especialidade/{espec} → Por especialidade

USUARIOS:
  POST    /auth/login             → Autenticar (retorna JWT)
  POST    /auth/refresh           → Renovar token
  POST    /auth/logout            → Logout
  POST    /usuarios               → Criar usuário (admin)
  PUT     /usuarios/{id}          → Atualizar perfil
  POST    /usuarios/{id}/senha    → Alterar senha

ADMINISTRACAO:
  GET     /admin/dashboard        → KPIs
  GET     /admin/relatorios/pacientes → Relatório pacientes
  GET     /admin/relatorios/consultas → Relatório consultas
  GET     /admin/auditoria        → Logs de auditoria
  GET     /admin/backup/status    → Status backup
  POST    /admin/backup/executar  → Executar backup imediato
```

### 4.2 Formato de Requisição/Resposta

#### **Exemplo: POST /sghss/api/v1/pacientes**

**Request:**
```json
{
  "nome": "João Silva",
  "cpf": "123.456.789-10",
  "dataNascimento": "1990-05-15",
  "telefone": "(11) 98765-4321",
  "email": "joao.silva@email.com",
  "endereco": "Rua A, 123, São Paulo, SP"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "cpf": "123.456.789-10",
  "dataNascimento": "1990-05-15",
  "telefone": "(11) 98765-4321",
  "email": "joao.silva@email.com",
  "endereco": "Rua A, 123, São Paulo, SP",
  "dataRegistro": "2025-12-03T10:30:00",
  "ativo": true
}
```

#### **Exemplo: POST /sghss/api/v1/auth/login**

**Request:**
```json
{
  "email": "medico@vidaplus.com",
  "senha": "SenhaForte123!"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expiresIn": 3600,
  "usuario": {
    "id": 5,
    "email": "medico@vidaplus.com",
    "role": "MEDICO"
  }
}
```

#### **Exemplo: GET /sghss/api/v1/consultas?pacienteId=1&status=agendada**

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 10,
      "pacienteId": 1,
      "pacienteNome": "João Silva",
      "medicoId": 5,
      "medicoNome": "Dr. Pedro",
      "medicoEspecialidade": "Cardiologia",
      "dataConsulta": "2025-12-10T14:00:00",
      "tipoConsulta": "presencial",
      "status": "agendada",
      "criadoEm": "2025-12-03T10:00:00"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "currentPage": 0,
  "pageSize": 10
}
```

### 4.3 Códigos de Status HTTP

| Status | Significado | Exemplo |
|--------|------------|---------|
| **200** | OK | GET paciente sucesso |
| **201** | Created | POST paciente criado |
| **204** | No Content | DELETE sem retorno |
| **400** | Bad Request | Validação falhou |
| **401** | Unauthorized | Token inválido/expirado |
| **403** | Forbidden | Sem permissão |
| **404** | Not Found | Recurso não existe |
| **409** | Conflict | CPF duplicado |
| **500** | Server Error | Erro interno |

---

## 5. SEGURANÇA NA ARQUITETURA

### 5.1 Camada de Autenticação (Spring Security)

```
1. Requisição HTTP
   ↓
2. SecurityFilter (valida JWT)
   ↓
3. JwtTokenProvider (decodifica token)
   ↓
4. UsuarioRepository (busca dados)
   ↓
5. Autorização (verifica Role/Permissão)
   ↓
6. Controller (executa lógica)
   ↓
7. Resposta HTTP
```

### 5.2 Fluxo de Autenticação JWT

```
1. Usuário submete credenciais (email + senha)
   ↓
2. AuthController valida contra banco (senha com BCrypt)
   ↓
3. JwtTokenProvider gera token válido por 1 hora
   ↓
4. Cliente armazena token em localStorage
   ↓
5. Toda requisição inclui: Authorization: Bearer <token>
   ↓
6. SecurityFilter valida signature do token
   ↓
7. Se válido, requisição prossegue; se inválido, 401
```

### 5.3 Criptografia

| Dados | Método | Chave | Rotação |
|-------|--------|-------|---------|
| Senhas | PBKDF2 + BCrypt | Aleatória (salt) | Sempre ao resetar |
| JWT Token | HMAC-SHA256 | Secret key do app | Anual |
| Dados sensíveis (CPF) | AES-256-GCM | Master key | Anual |
| Backups | AES-256 | Chave separada | Anual |

---

## 6. FLUXO DE DADOS - EXEMPLO: AGENDAR CONSULTA

```
PACIENTE (Web Frontend)
  │
  ├─ Escolhe médico, data, hora
  │
  └─ POST /sghss/api/v1/consultas
         Authorization: Bearer <jwt_token>
         {
           "pacienteId": 1,
           "medicoId": 5,
           "dataConsulta": "2025-12-10T14:00:00"
         }
         │
         ├─ SecurityFilter (valida JWT) ✓
         │
         └─ ConsultaController
              │
              └─ ConsultaService
                   │
                   ├─ Validar paciente existe ✓
                   ├─ Validar médico existe ✓
                   ├─ Validar horário disponível ✓
                   ├─ Validar sem conflitos ✓
                   │
                   └─ ConsultaRepository.save()
                        │
                        └─ MySQL: INSERT INTO consulta (paciente_id, medico_id, data_consulta, status)
                             │
                             └─ Retorna Consulta com ID gerado
                   │
                   └─ AuditoriaService.registrar()
                        └─ Registra: CREATED, CONSULTA, usuario=1, paciente_id=1
                   │
                   └─ NotificacaoService.enviar()
                        └─ Notifica médico por email
                   │
                   └─ Response 201 Created
                        {
                          "id": 10,
                          "pacienteId": 1,
                          "medicoId": 5,
                          "dataConsulta": "2025-12-10T14:00:00",
                          "status": "agendada"
                        }

PACIENTE (recebe confirmação)
```

---

## 7. CONCLUSÃO DA FASE 2

### Entregáveis
✅ Requisitos funcionais (50 RFs) catalogados com prioridades  
✅ Requisitos não-funcionais (30 RNFs) com métricas  
✅ Diagramas UML (casos de uso, classes, DER)  
✅ Arquitetura em camadas definida  
✅ Design de API REST (endpoints, formatos, status codes)  
✅ Estratégia de segurança (autenticação, criptografia)  
✅ Fluxos de dados especificados  

### Próximos Passos (Fase 3)
- Implementar controllers Spring Boot
- Criar models JPA com relacionamentos
- Implementar repositories Spring Data
- Codificar services com lógica de negócio
- Testes unitários com JUnit/Mockito

---

**Documento: FASE 2 - Modelagem e Arquitetura**  
**Data:** Dezembro 2025  
**Autor:** Marcio Machado Moreira (RU: 4543545)  
**Status:** ✅ Concluído
