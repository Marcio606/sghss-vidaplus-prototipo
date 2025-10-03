# 🎯 APRESENTAÇÃO DO PROJETO SGHSS VIDA PLUS

## 📋 **SLIDES DE APRESENTAÇÃO**

### **SLIDE 1: TÍTULO**
```
🏥 SGHSS VIDA PLUS
Sistema de Gestão Hospitalar e de Serviços de Saúde

Desenvolvido por: Marcio Machado Moreira
RU: 4543545

Universidade [Uninter]
Curso: [analise e desenvolvimento de software]
Disciplina: Projetos Multidisciplinares
```

---

### **SLIDE 2: PROBLEMA**
```
❌ PROBLEMAS IDENTIFICADOS NA VIDA PLUS:

• Gestão manual de pacientes
• Prontuários em papel
• Agendamentos desorganizados
• Controle de estoque ineficiente
• Falta de conformidade com LGPD
• Processos lentos e propensos a erros
```

---

### **SLIDE 3: SOLUÇÃO**
```
✅ SOLUÇÃO IMPLEMENTADA:

🏥 Sistema SGHSS Vida Plus
• Gestão completa de pacientes
• Prontuários eletrônicos
• Agendamento inteligente
• Controle de medicamentos
• Conformidade com LGPD
• Processos automatizados
```

---

### **SLIDE 4: TECNOLOGIAS**
```
🛠️ TECNOLOGIAS UTILIZADAS:

Backend:
• Java 11 + Spring Boot 2.7.14
• MySQL 8.0 + JPA/Hibernate
• REST API + Swagger/OpenAPI
• JWT + Spring Security
• Docker + Docker Compose

Ferramentas:
• Maven (Gerenciamento de dependências)
• Git (Controle de versão)
• Swagger UI (Documentação)
• Postman (Testes de API)
```

---

### **SLIDE 5: ARQUITETURA**
```
🏗️ ARQUITETURA DO SISTEMA:

┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │   Mobile App    │
│   (React/Vue)   │    │ (React Native)  │
└─────────────────┘    └─────────────────┘
         │                       │
         └───────────┬───────────┘
                     │
         ┌─────────────────┐
         │   API REST      │
         │  (Swagger UI)   │
         └─────────────────┘
                     │
         ┌─────────────────┐
         │  Spring Boot    │
         │  Controllers    │
         └─────────────────┘
                     │
         ┌─────────────────┐
         │   Services      │
         │ (Lógica Negócio)│
         └─────────────────┘
                     │
         ┌─────────────────┐
         │  Repositories   │
         │  (Acesso Dados) │
         └─────────────────┘
                     │
         ┌─────────────────┐
         │   MySQL 8.0     │
         │  (Banco Dados)  │
         └─────────────────┘
```

---

### **SLIDE 6: FUNCIONALIDADES**
```
🎯 PRINCIPAIS FUNCIONALIDADES:

👥 GESTÃO DE PACIENTES
• Cadastro completo com validações
• Busca avançada por múltiplos critérios
• Histórico médico digital
• Controle de aniversariantes

👨‍⚕️ GESTÃO DE MÉDICOS
• Cadastro profissional
• Validação de CRM
• Controle de especialidades
• Gestão de agenda

📅 SISTEMA DE CONSULTAS
• Agendamento inteligente
• Controle de disponibilidade
• Prescrições médicas
• Histórico de consultas

💊 CONTROLE DE MEDICAMENTOS
• Gestão de estoque
• Alertas de validade
• Movimentações
• Relatórios de consumo
```

---

### **SLIDE 7: ENTIDADES**
```
📊 MODELO DE DADOS:

┌─────────────┐    ┌─────────────┐
│   Paciente  │    │   Médico    │
│ - id        │    │ - id        │
│ - nome      │    │ - nome      │
│ - cpf       │    │ - crm       │
│ - email     │    │ - especialidade│
│ - telefone  │    │ - telefone  │
│ - endereco  │    │ - email     │
└─────────────┘    └─────────────┘
       │                  │
       │ (1:N)            │ (1:N)
       │                  │
       ▼                  ▼
┌─────────────┐    ┌─────────────┐
│  Consulta   │    │AgendaMedico │
│ - id        │    │ - id        │
│ - paciente  │    │ - medico    │
│ - medico    │    │ - data      │
│ - dataHora  │    │ - horaInicio│
│ - status    │    │ - disponivel│
└─────────────┘    └─────────────┘
       │
       │ (1:N)
       ▼
┌─────────────┐
│ Prescricao  │
│ - id        │
│ - consulta  │
│ - medicamento│
│ - dosagem   │
└─────────────┘
```

---

### **SLIDE 8: API ENDPOINTS**
```
🌐 PRINCIPAIS ENDPOINTS:

PACIENTES:
GET    /api/pacientes              # Listar todos
GET    /api/pacientes/{id}         # Buscar por ID
GET    /api/pacientes/cpf/{cpf}    # Buscar por CPF
POST   /api/pacientes              # Cadastrar
PUT    /api/pacientes/{id}         # Atualizar
DELETE /api/pacientes/{id}         # Deletar

MÉDICOS:
GET    /api/medicos                # Listar todos
GET    /api/medicos/{id}           # Buscar por ID
GET    /api/medicos/crm/{crm}      # Buscar por CRM
POST   /api/medicos                # Cadastrar
PUT    /api/medicos/{id}           # Atualizar

CONSULTAS:
GET    /api/consultas              # Listar todas
POST   /api/consultas              # Agendar
PUT    /api/consultas/{id}         # Atualizar status
DELETE /api/consultas/{id}         # Cancelar

MEDICAMENTOS:
GET    /api/medicamentos           # Listar todos
POST   /api/medicamentos           # Cadastrar
PUT    /api/medicamentos/{id}      # Atualizar estoque
```

---

### **SLIDE 9: CRONOGRAMA**
```
📅 CRONOGRAMA DE DESENVOLVIMENTO:

FASE 1 (Semana 1): ✅ CONCLUÍDA
• Planejamento e compreensão
• Definição do escopo
• Pesquisa de tecnologias

FASE 2 (Semanas 2-3): ✅ CONCLUÍDA
• Análise de requisitos
• Modelagem UML
• Definição da arquitetura

FASE 3 (Semanas 4-6): ✅ CONCLUÍDA
• Implementação do código
• Desenvolvimento das APIs
• Testes funcionais

FASE 4 (Semana 7): ✅ CONCLUÍDA
• Plano de testes
• Testes de qualidade
• Validação de performance

FASE 5 (Semana 8): ✅ CONCLUÍDA
• Documentação final
• Revisão do projeto
• Entrega
```

---

### **SLIDE 10: RESULTADOS DOS TESTES**
```
🧪 RESULTADOS DOS TESTES:

TESTES FUNCIONAIS:
• Total de casos: 20
• Aprovados: 19 (95%)
• Reprovados: 1 (5%)

TESTES DE PERFORMANCE:
• Tempo de resposta: 250ms (< 500ms) ✅
• Throughput: 150 req/s (> 100 req/s) ✅
• Uso de memória: 256MB (< 512MB) ✅
• Disponibilidade: 99.9% (> 99.5%) ✅

TESTES DE SEGURANÇA:
• Autenticação JWT: ✅
• Validação de entrada: ✅
• Proteção SQL Injection: ✅
• CORS configurado: ✅
```

---

### **SLIDE 11: DEMONSTRAÇÃO**
```
🎬 DEMONSTRAÇÃO AO VIVO:

1. 🚀 EXECUÇÃO DO SISTEMA
   docker-compose up -d

2. 📚 DOCUMENTAÇÃO SWAGGER
   http://localhost:8080/sghss/swagger-ui.html

3. 🧪 TESTES DE API
   • Cadastrar paciente
   • Buscar por CPF
   • Agendar consulta
   • Listar medicamentos

4. 📊 RELATÓRIOS
   • Estatísticas de pacientes
   • Aniversariantes do mês
   • Medicamentos em estoque baixo
```

---

### **SLIDE 12: BENEFÍCIOS**
```
💡 BENEFÍCIOS ALCANÇADOS:

PARA A INSTITUIÇÃO:
• Processos automatizados
• Redução de erros
• Conformidade com LGPD
• Melhor gestão de recursos
• Relatórios em tempo real

PARA OS PROFISSIONAIS:
• Interface intuitiva
• Acesso rápido aos dados
• Prescrições digitais
• Agenda organizada
• Histórico completo

PARA OS PACIENTES:
• Atendimento mais rápido
• Prontuário digital seguro
• Agendamento online
• Histórico médico completo
• Maior privacidade
```

---

### **SLIDE 13: MELHORIAS FUTURAS**
```
🔮 ROADMAP FUTURO:

VERSÃO 1.1:
• Autenticação OAuth2
• Notificações push
• Integração com SUS
• Telemedicina

VERSÃO 1.2:
• IA para diagnósticos
• Blockchain para prontuários
• IoT para monitoramento
• Analytics avançado

VERSÃO 2.0:
• Microserviços
• Mobile app completo
• Integração com laboratórios
• Sistema de faturamento
```

---

### **SLIDE 14: TECNOLOGIAS DOMINADAS**
```
🎓 COMPETÊNCIAS DESENVOLVIDAS:

DESENVOLVIMENTO:
• Java 11 + Spring Boot
• REST API + Swagger
• MySQL + JPA/Hibernate
• Docker + Docker Compose
• JWT + Spring Security

ANÁLISE E MODELAGEM:
• UML (Classes, Casos de Uso)
• Arquitetura em camadas
• Design patterns
• Relacionamentos de dados

TESTES E QUALIDADE:
• Testes funcionais
• Testes de performance
• Testes de segurança
• Documentação técnica

GESTÃO DE PROJETOS:
• Cronograma de 8 semanas
• Versionamento Git
• Documentação contínua
• Entrega pontual
```

---

### **SLIDE 15: CONCLUSÃO**
```
🎯 CONCLUSÃO:

✅ OBJETIVOS ALCANÇADOS:
• Sistema funcional implementado
• Todas as funcionalidades principais
• Documentação completa
• Testes validados
• Cronograma cumprido

📈 RESULTADOS:
• 9 entidades implementadas
• 5 controllers REST
• 95% dos testes aprovados
• API documentada
• Sistema containerizado

🎓 APRENDIZADOS:
• Desenvolvimento Spring Boot
• Arquitetura de software
• Testes de qualidade
• Gestão de projetos
• Documentação técnica

🚀 PRÓXIMOS PASSOS:
• Deploy em produção
• Treinamento dos usuários
• Monitoramento contínuo
• Evolução do sistema
```

---

### **SLIDE 16: OBRIGADO!**
```
🙏 OBRIGADO PELA ATENÇÃO!

PROJETO SGHSS VIDA PLUS
Sistema de Gestão Hospitalar e de Serviços de Saúde

Desenvolvido por: Marcio Machado Moreira
RU: 4543545

📧 Email: marcio606@email.com
🔗 GitHub: https://github.com/Marcio606/sghss-vidaplus-prototipo
🌐 Sistema: http://localhost:8080/sghss/swagger-ui.html

PERGUNTAS?
```

---

## 🎬 **ROTEIRO DE DEMONSTRAÇÃO**

### **1. PREPARAÇÃO (2 min)**
```bash
# Iniciar o sistema
cd sghss-vidaplus
docker-compose up -d

# Verificar status
curl http://localhost:8080/sghss/actuator/health
```

### **2. DOCUMENTAÇÃO SWAGGER (3 min)**
- Acessar: http://localhost:8080/sghss/swagger-ui.html
- Mostrar estrutura da API
- Explicar endpoints disponíveis
- Demonstrar autenticação

### **3. TESTES PRÁTICOS (5 min)**
- Cadastrar um paciente
- Buscar paciente por CPF
- Agendar uma consulta
- Listar medicamentos
- Mostrar relatórios

### **4. CÓDIGO FONTE (3 min)**
- Mostrar estrutura do projeto
- Explicar entidades JPA
- Demonstrar controllers
- Mostrar configurações

### **5. DOCKER (2 min)**
- Mostrar docker-compose.yml
- Explicar containerização
- Demonstrar logs
- Mostrar banco de dados

---

## 📋 **CHECKLIST PARA APRESENTAÇÃO**

### **✅ ANTES DA APRESENTAÇÃO:**
- [ ] Sistema rodando localmente
- [ ] Docker containers ativos
- [ ] Swagger UI acessível
- [ ] Banco de dados populado
- [ ] Slides preparados
- [ ] Código fonte organizado
- [ ] Documentação atualizada

### **✅ DURANTE A APRESENTAÇÃO:**
- [ ] Explicar o problema
- [ ] Mostrar a solução
- [ ] Demonstrar tecnologias
- [ ] Exibir arquitetura
- [ ] Testar funcionalidades
- [ ] Mostrar resultados
- [ ] Explicar melhorias futuras

### **✅ APÓS A APRESENTAÇÃO:**
- [ ] Responder perguntas
- [ ] Mostrar código fonte
- [ ] Explicar implementação
- [ ] Discutir desafios
- [ ] Propor melhorias

---

## 🎯 **PONTOS-CHAVE PARA DESTACAR**

1. **Problema Real:** Gestão hospitalar manual e ineficiente
2. **Solução Técnica:** Sistema moderno com Spring Boot
3. **Arquitetura Robusta:** Camadas bem definidas
4. **API Completa:** REST com documentação Swagger
5. **Testes Validados:** 95% de aprovação
6. **Containerizado:** Fácil deploy com Docker
7. **Documentado:** Código e API documentados
8. **Escalável:** Preparado para crescimento

---

**🎉 Projeto pronto para apresentação acadêmica!**
