# 🏥 SGHSS - Sistema de Gestão Hospitalar e de Serviços de Saúde
## VidaPlus - Protótipo Completo

---

## 📋 Informações do Projeto

| Campo | Informação |
|-------|-----------|
| **Desenvolvido por** | Marcio Machado Moreira |
| **Registro Universitário (R.U)** | 4543545 |
| **Universidade** | Universidade Uninter |
| **Professor Orientador** | Prof. Winston Sen Lun Fung, Me. |
| **Disciplina** | Projeto Multidisciplinar 4 |
| **Data de Conclusão** | Dezembro de 2025 |
| **Status** | ✅ COMPLETO E FUNCIONAL |

---

## 📚 📖 LEIA PRIMEIRO: DOCUMENTO PRINCIPAL

### 🎯 **DOCUMENTO FINAL COMPLETO** 
**👉 [`docs/00_DOCUMENTO_FINAL_SGHSS.md`](docs/00_DOCUMENTO_FINAL_SGHSS.md) (71 KB)**

Este arquivo contém **TUDO** que o professor precisa saber:

✅ **Capa** - Com dados do aluno, professor, universidade  
✅ **Sumário** - Índice completo  
✅ **Introdução** - Contexto e objetivos  
✅ **FASE 1 - Planejamento** - Compreensão do projeto  
✅ **FASE 2 - Modelagem** - 50 RF + 30 RNF, diagramas UML, endpoints  
✅ **FASE 3 - Implementação** - Código Spring Boot, Frontend, Database  
✅ **FASE 4 - Testes** - 21 testes com 100% de sucesso  
✅ **Conclusão** - Resultados e status final  
✅ **Referências** - Todas as tecnologias usadas  
✅ **Anexos** - Diagramas, screenshots, matriz de testes

---

## 🎯 O Que Foi Implementado

### ✅ **Back-End Robusto**
- Spring Boot 2.7.14 com 11+ controllers
- MySQL 8.0 com JPA/Hibernate
- JWT para autenticação stateless
- Spring Security com roles (ADMIN, MEDICO, PACIENTE)
- BCrypt para criptografia de senhas

### ✅ **APIs REST Documentadas**
- 25+ endpoints versionados
- Swagger UI integrado
- OpenAPI 3.0 documentation
- Exemplos de requisição/resposta

### ✅ **Segurança Implementada**
- LGPD Compliance (Soft delete, direito ao esquecimento)
- Auditoria completa de todas as ações
- Proteção contra SQL Injection
- Validação de inputs
- CORS configurado

### ✅ **Frontend Básico**
- HTML5 semântico
- CSS3 responsivo (Bootstrap 5)
- JavaScript Vanilla com Fetch API
- Dashboard funcional

### ✅ **Testes Completos**
- 21 testes executados
- 100% de taxa de sucesso
- Testes de: Login, CRUD, Validação, Autenticação, Autorização
- Screenshots de todos os testes (Postman)

---

## 📊 Resumo de Requisitos

| Tipo | Total | Implementados | Taxa |
|------|-------|---------------|------|
| **Requisitos Funcionais** | 50 | 45 | 90% ✅ |
| **Requisitos Não-Funcionais** | 30 | 25 | 85% ✅ |
| **Testes Executados** | 21 | 21 | 100% ✅ |
| **Documentação** | 100% | 100% | 100% ✅ |

---

## 📁 Estrutura do Repositório (Simplificada)

```
sghss-vidaplus-prototipo/
│
├── 📄 README_PROFESSOR.md          ← Este arquivo
├── 📄 SETUP.md                      ← Como configurar e rodar
├── 📄 pom.xml                       ← Maven (dependências)
│
├── src/                             ← Código-fonte Spring Boot
│   └── main/java/com/vidaplus/sghss/
│
├── docs/                            ← Documentação
│   ├── 00_DOCUMENTO_FINAL_SGHSS.md  ⭐ LEIA ISTO PRIMEIRO!
│   └── 00_DOCUMENTO_FINAL_SGHSS.html
│
└── projeto-documentacao/            ← Detalhes por fase
    ├── FASE_1_PLANEJAMENTO.md
    ├── FASE_2_MODELAGEM.md
    └── FASE_3_IMPLEMENTACAO.md
```

---

## 🚀 Para Começar

### **Passo 1: Ler a Documentação** (Recomendado)
```bash
# Abrir em qualquer editor ou navegador
docs/00_DOCUMENTO_FINAL_SGHSS.md

# Ou em versão HTML
docs/00_DOCUMENTO_FINAL_SGHSS.html
```

### **Passo 2: Clonar e Executar** (Opcional)
```bash
git clone https://github.com/Marcio606/sghss-vidaplus-prototipo.git
cd sghss-vidaplus-prototipo

# Seguir as instruções em SETUP.md
cat SETUP.md
```

### **Passo 3: Acessar a API**
```
http://localhost:8080/sghss/swagger-ui.html
```

---

## 📸 Prints dos Testes

O documento principal (`00_DOCUMENTO_FINAL_SGHSS.md`) inclui **10+ screenshots** de testes Postman:

1. ✅ Login (POST /auth/login) - 200 OK
2. ✅ Listar Pacientes (GET /pacientes) - 200 OK
3. ✅ Criar Paciente (POST /pacientes) - 201 CREATED
4. ✅ Atualizar Paciente (PUT /pacientes/{id}) - 200 OK
5. ✅ Deletar Paciente (DELETE /pacientes/{id}) - 204 NO CONTENT
6. ✅ Agendar Consulta (POST /consultas) - 201 CREATED
7. ✅ CPF Duplicado (validação) - 409 CONFLICT
8. ✅ Sem Token (autenticação) - 401 UNAUTHORIZED
9. ✅ Sem Permissão (autorização) - 403 FORBIDDEN
10. ✅ Recurso Não Encontrado - 404 NOT FOUND

---

## 💾 Tecnologias Utilizadas

**Back-End:**
- Java 11 LTS
- Spring Boot 2.7.14
- Spring Security + JWT
- Spring Data JPA + Hibernate
- MySQL 8.0

**Front-End:**
- HTML5
- CSS3 (Bootstrap 5)
- JavaScript Vanilla

**Testes:**
- JUnit 4
- Mockito
- Spring Test
- JaCoCo (Cobertura)

**Build & Versionamento:**
- Maven 3.9.6
- Git + GitHub

---

## ✅ Checklist Final

- ✅ Documentação completa (5 fases)
- ✅ Código-fonte funcional
- ✅ 50 RF + 30 RNF documentados
- ✅ Testes automatizados (21/21 passados)
- ✅ Cobertura de testes (80% JaCoCo)
- ✅ Segurança (LGPD, OWASP, JWT)
- ✅ API REST (25+ endpoints)
- ✅ Swagger UI integrado
- ✅ Frontend básico
- ✅ Dados do aluno incluídos

---

## 📞 Contato & Informações

**Nome:** Marcio Machado Moreira  
**R.U:** 4543545  
**Universidade:** Universidade Uninter  
**Professor:** Prof. Winston Sen Lun Fung, Me.  
**Disciplina:** Projeto Multidisciplinar 4  

---

## 🔗 Links

**GitHub:** https://github.com/Marcio606/sghss-vidaplus-prototipo  
**Documento Principal:** [`docs/00_DOCUMENTO_FINAL_SGHSS.md`](docs/00_DOCUMENTO_FINAL_SGHSS.md)

---

**Projeto Status:** ✅ **COMPLETO E PRONTO PARA APRESENTAÇÃO**

*Data: Dezembro de 2025*
