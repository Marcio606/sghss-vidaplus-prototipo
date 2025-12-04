# FASE 1: PLANEJAMENTO E COMPREENSÃO

## 1. CONTEXTO DO PROJETO - INSTITUIÇÃO VIDAPLUS

### 1.1 Apresentação da Instituição
A instituição **VidaPlus** é uma organização de saúde que administra:
- **Hospitais** de grande porte
- **Clínicas de bairro** (atenção primária)
- **Laboratórios** de análises clínicas
- **Equipes de Home Care** (atendimento domiciliar)

### 1.2 Problemas Identificados
Antes da implementação do SGHSS, a VidaPlus enfrentava:

| Problema | Impacto | Solução no SGHSS |
|----------|--------|-----------------|
| Gestão manual de pacientes | Erros, lentidão | Cadastro centralizado em BD |
| Agendamentos desorganizados | Conflitos, fila de espera | Sistema de agendamento online |
| Prontuários em papel | Perda, acesso lento | Prontuários eletrônicos digitais |
| Sem telemedicina | Acesso limitado a médicos | Videochamadas integradas |
| Segurança inadequada | Vazamento de dados | Criptografia + LGPD compliance |
| Sem relatórios gerenciais | Decisões sem dados | Dashboards analíticos |

---

## 2. OBJETIVOS DO PROJETO SGHSS

### 2.1 Objetivo Geral
Desenvolver um **Sistema de Gestão Hospitalar e de Serviços de Saúde (SGHSS)** que centralize e otimize o gerenciamento de:
- ✅ Pacientes
- ✅ Profissionais de saúde
- ✅ Consultas e agendamentos
- ✅ Prontuários eletrônicos
- ✅ Telemedicina
- ✅ Administração hospitalar
- ✅ Segurança e conformidade regulatória

### 2.2 Objetivos Específicos
1. **Eficiência Operacional**: Reduzir tempo de atendimento em 30%
2. **Segurança de Dados**: Implementar criptografia e auditoria LGPD
3. **Escalabilidade**: Suportar múltiplas unidades hospitalares
4. **Acessibilidade**: Interface responsiva em múltiplos dispositivos
5. **Integração**: Conectar telemedicina, prontuários e prescrições
6. **Disponibilidade**: Manter 99,5% de uptime com backups robustos

---

## 3. PRINCIPAIS ATORES (STAKEHOLDERS)

### 3.1 Pacientes
**Necessidades:**
- Agendar/cancelar consultas online
- Visualizar histórico clínico pessoal
- Acessar teleconsultas
- Receber notificações de agendamentos
- Consultar prescrições e resultados

**Benefícios:**
- Acesso 24/7 ao sistema
- Redução de deslocamentos (telemedicina)
- Transparência total do cuidado

### 3.2 Profissionais de Saúde (Médicos, Enfermeiros, Técnicos)
**Necessidades:**
- Gerenciar agenda de consultas
- Atualizar prontuários eletrônicos em tempo real
- Emitir prescrições digitais
- Acompanhar histórico completo dos pacientes
- Realizar videoconsultas seguras

**Benefícios:**
- Workflows otimizados
- Redução de burocracia
- Tomadas de decisão baseadas em dados completos

### 3.3 Administradores/Gerentes
**Necessidades:**
- Gerenciar cadastros de pacientes e profissionais
- Controlar fluxo de internações
- Alocar leitos hospitalares
- Gerar relatórios financeiros
- Monitorar performance do sistema

**Benefícios:**
- Visão holística da operação
- Relatórios em tempo real
- Controle financeiro e de estoque

---

## 4. PONTOS CRÍTICOS DO PROJETO

### 4.1 Segurança de Dados (LGPD - Lei Geral de Proteção de Dados)

**Requisitos Obrigatórios:**
- ✅ Criptografia de dados sensíveis (CPF, histórico médico)
- ✅ Controle de acesso por perfil/função
- ✅ Auditoria completa de acessos
- ✅ Direito ao esquecimento (data deletion)
- ✅ Consentimento explícito para tratamento de dados

**Implementação no SGHSS:**
- Spring Security com JWT tokens
- Banco de dados MySQL com criptografia de senhas (BCrypt)
- Logs de auditoria em tabela separada
- Soft deletes para GDPR compliance

### 4.2 Escalabilidade Multi-Unidade

**Desafio:**
VidaPlus opera múltiplos hospitais/clínicas que precisam:
- Dados centralizados mas independentes por unidade
- Roteamento de pacientes entre unidades
- Escalabilidade horizontal

**Solução Arquitetural:**
- Banco de dados único com campo `unidade_id` em todas entidades
- API stateless (escalável com load balancer)
- Cache distribuído (Redis para sessões)
- Microserviços futuros para telemedicina

### 4.3 Disponibilidade e Confiabilidade

**Requisitos:**
- 99.5% de uptime (máx. 3.6 horas/mês indisponível)
- RPO (Recovery Point Objective): < 1 hora
- RTO (Recovery Time Objective): < 30 minutos

**Estratégia:**
- Backups automáticos diários + backups em tempo real
- Redundância em múltiplas AZs (nuvem)
- Health checks automáticos
- Circuit breakers para APIs externas

### 4.4 Telemedicina - Requisitos Críticos

**Desafios:**
- Latência < 200ms para videochamadas
- Criptografia end-to-end
- Conformidade médico-legal
- Registro automático de consultas

**Tecnologias Propostas:**
- WebRTC para videochamadas
- TURN servers para NAT traversal
- Gravação automática com consentimento
- Integração com prontuários em tempo real

---

## 5. ESCOPO DO PROJETO - ÊNFASE

### 5.1 Ênfase Escolhida: **Back-end com Front-end Simples Básico**

**Por quê?**
- A lógica de negócio complexa está no back-end (SGHSS é um sistema crítico)
- Front-end será simples mas funcional para demonstrar integração
- Foco em API REST robusta, segura e escalável

### 5.2 O que será desenvolvido

#### Back-end (Completo) ✅
- **Arquitetura em Camadas**: Controllers → Services → Repositories
- **Models/Entities**: Paciente, Médico, Consulta, Prontuário, Prescrição, etc.
- **API REST**: Endpoints CRUD com validações
- **Banco de Dados**: MySQL 8.0 com JPA/Hibernate
- **Segurança**: Spring Security + JWT
- **Tratamento de Erros**: Exception handlers personalizados

#### Front-end (Básico + Funcional) ✅
- **HTML/CSS Responsivo**: Dashboard simples
- **JavaScript Vanilla**: Consumir API REST
- **Telas Principais**:
  - Login com JWT
  - Dashboard com lista de pacientes
  - Formulário cadastro de pacientes
  - Agendamento de consultas (básico)
  - Listagem de consultas

#### Integração BD ✅
- **MySQL 8.0**: Criar schema automático com JPA
- **Seed Data**: Dados iniciais para testes
- **Migrações**: Versionamento de schema

---

## 6. TECNOLOGIAS SELECIONADAS

### 6.1 Back-end Stack

| Camada | Tecnologia | Versão | Justificativa |
|-------|-----------|--------|--------------|
| Framework | Spring Boot | 2.7.14 | Maduro, robusto, grande comunidade |
| Banco de Dados | MySQL | 8.0 | Relacional, ACID, confiável |
| ORM | JPA/Hibernate | 5.6.x | Padrão Java para ORM |
| Build | Maven | 3.9.6 | Reprodutibilidade, dependencies |
| Segurança | Spring Security | 5.7.x | Autenticação + Autorização |
| API Tokens | JWT | 0.11.x | Stateless, escalável |
| Documentação API | Swagger/OpenAPI | 3.0 | Documentação automática |
| Validation | Hibernate Validator | 7.x | Bean validation |
| Logging | Log4j2 | 2.17.x | Logging estruturado |

### 6.2 Front-end Stack (Básico)

| Camada | Tecnologia | Justificativa |
|-------|-----------|--------------|
| HTML | HTML5 | Semântico, acessível |
| CSS | Bootstrap 5 | Responsivo, simples |
| JavaScript | Vanilla JS | Sem dependências, educacional |
| HTTP | Fetch API | Nativa, moderna |
| Storage | LocalStorage | Armazenar JWT tokens |

### 6.3 Infraestrutura & DevOps

| Ferramenta | Versão | Uso |
|-----------|--------|-----|
| Docker | 20.10+ | Containerização |
| Docker Compose | 2.0+ | Orquestração local |
| Git | 2.36+ | Versionamento |
| GitHub | - | Repositório central |
| Maven Wrapper | Incluído | Build sem Maven instalado |

---

## 7. CRONOGRAMA DE DESENVOLVIMENTO

### 7.1 Fases do Projeto (8 semanas)

| Semana | Fase | Atividades | Entregáveis |
|--------|------|-----------|-------------|
| 1 | Planejamento | Análise de requisitos, prototipagem | Documento requisitos |
| 2-3 | Modelagem | Diagramas UML, design DB, arquitetura | Diagrama classes, DER |
| 4-6 | Implementação | Código back-end, front-end, testes | APIs funcionando, UI básica |
| 7 | Testes | Casos de teste, automação | Plano de testes |
| 8 | Documentação | PDF final, anexos | Documento PDF único |

---

## 8. RISCOS E MITIGAÇÃO

| Risco | Probabilidade | Impacto | Mitigação |
|------|--------------|--------|-----------|
| Java 24 incompatível com Lombok | Alta | Alto | Usar Java 11 LTS ou Spring Boot 3.x |
| Dados sensíveis não criptografados | Média | Crítico | Implementar criptografia desde início |
| Banco de dados lento com muitos registros | Média | Médio | Índices no MySQL, cache Redis |
| API não escalável | Baixa | Alto | Arquitetura stateless desde início |
| Telemedicina com latência alta | Média | Alto | Use load balancing, CDN |
| LGPD non-compliance | Média | Crítico | Auditoria legal, logs completos |

---

## 9. REQUISITOS FUNCIONAIS PRINCIPAIS (Resumo)

### RF001-RF010: Gestão de Pacientes
- ✅ Cadastrar paciente (nome, CPF, data nasc, contato)
- ✅ Atualizar dados de paciente
- ✅ Deletar paciente (soft delete LGPD)
- ✅ Buscar paciente por CPF/Nome
- ✅ Visualizar histórico clínico

### RF011-RF020: Gestão de Consultas
- ✅ Agendar consulta (data, hora, médico, paciente)
- ✅ Cancelar consulta
- ✅ Listar consultas do paciente
- ✅ Listar consultas do médico por data

### RF021-RF030: Prontuários
- ✅ Criar prontuário para consulta
- ✅ Atualizar diagnóstico/tratamento
- ✅ Anexar documentos (exames, imagens)
- ✅ Visualizar histórico de prontuários

### RF031-RF040: Profissionais
- ✅ Cadastrar médico (especialidade, CRM)
- ✅ Gerenciar agenda médica
- ✅ Listar médicos por especialidade

### RF041-RF050: Administração
- ✅ Gerar relatórios de pacientes ativos
- ✅ Relatório de consultas por período
- ✅ Controle de acesso por perfil

---

## 10. REQUISITOS NÃO-FUNCIONAIS PRINCIPAIS (Resumo)

| Categoria | Requisito | Métrica |
|-----------|-----------|---------|
| Performance | Resposta de API | < 200ms (p95) |
| Escalabilidade | Suportar usuários simultâneos | 1000+ usuários |
| Disponibilidade | Uptime | 99.5% |
| Segurança | Criptografia de senhas | PBKDF2 mínimo |
| Usabilidade | Interface responsiva | Mobile-first |
| Conformidade | LGPD compliance | Auditoria completa |
| Confiabilidade | MTBF | > 720 horas |
| Portabilidade | Rodar em Docker | Windows/Linux/Mac |

---

## 11. CONCLUSÃO DA FASE 1

### Resumo
Este projeto SGHSS é uma iniciativa crítica para VidaPlus, centralizando:
- **Dados** dos pacientes de forma segura
- **Operações** hospitalares de forma eficiente
- **Acesso** através de múltiplos canais (web, telemedicina)
- **Conformidade** legal com LGPD e regulamentações médicas

### Próximas Etapas
Na **Fase 2**, procederemos com:
1. Análise detalhada de requisitos em tabelas
2. Diagramas UML (Casos de Uso, Classes, DER)
3. Design de arquitetura em camadas
4. Definição de endpoints da API REST

---

**Documento: FASE 1 - Planejamento e Compreensão**  
**Data:** Dezembro 2025  
**Autor:** Marcio Machado Moreira (RU: 4543545)  
**Status:** ✅ Concluído
