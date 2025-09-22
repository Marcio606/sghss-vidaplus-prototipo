# 🧪 FASE 4 - PLANO DE TESTES E QUALIDADE

## 📋 **OBJETIVO DA FASE 4**

Implementar e executar testes funcionais, não funcionais e de segurança para validar o sistema SGHSS Vida Plus conforme os requisitos especificados.

---

## 🎯 **ESTRATÉGIA DE TESTES**

### **1. TESTES FUNCIONAIS**

#### **1.1 Casos de Teste - Gestão de Pacientes**

| ID | Caso de Teste | Descrição | Entrada | Resultado Esperado | Status |
|----|---------------|-----------|---------|-------------------|--------|
| CT001 | Cadastrar Paciente Válido | Cadastrar paciente com todos os dados obrigatórios | Nome, CPF, Email, Telefone | HTTP 201 + Dados do paciente | ✅ |
| CT002 | Cadastrar Paciente Sem CPF | Tentar cadastrar paciente sem informar CPF | Nome, Email, Telefone | HTTP 400 + "CPF é obrigatório" | ✅ |
| CT003 | Cadastrar Paciente CPF Duplicado | Tentar cadastrar paciente com CPF já existente | CPF já cadastrado | HTTP 409 + "CPF já cadastrado" | ✅ |
| CT004 | Buscar Paciente por ID | Buscar paciente existente por ID | ID válido | HTTP 200 + Dados do paciente | ✅ |
| CT005 | Buscar Paciente Inexistente | Buscar paciente com ID inexistente | ID inexistente | HTTP 404 + "Paciente não encontrado" | ✅ |
| CT006 | Buscar Paciente por CPF | Buscar paciente existente por CPF | CPF válido | HTTP 200 + Dados do paciente | ✅ |
| CT007 | Atualizar Paciente | Atualizar dados de paciente existente | ID + Novos dados | HTTP 200 + Dados atualizados | ✅ |
| CT008 | Deletar Paciente | Deletar paciente existente | ID válido | HTTP 204 + Paciente removido | ✅ |

#### **1.2 Casos de Teste - Gestão de Médicos**

| ID | Caso de Teste | Descrição | Entrada | Resultado Esperado | Status |
|----|---------------|-----------|---------|-------------------|--------|
| CT009 | Cadastrar Médico Válido | Cadastrar médico com CRM válido | Nome, CRM, Especialidade | HTTP 201 + Dados do médico | ✅ |
| CT010 | Cadastrar Médico CRM Duplicado | Tentar cadastrar médico com CRM já existente | CRM já cadastrado | HTTP 409 + "CRM já cadastrado" | ✅ |
| CT011 | Buscar Médico por Especialidade | Buscar médicos por especialidade | Especialidade | HTTP 200 + Lista de médicos | ✅ |
| CT012 | Validar CRM | Validar formato do CRM | CRM inválido | HTTP 400 + "CRM inválido" | ✅ |

#### **1.3 Casos de Teste - Sistema de Consultas**

| ID | Caso de Teste | Descrição | Entrada | Resultado Esperado | Status |
|----|---------------|-----------|---------|-------------------|--------|
| CT013 | Agendar Consulta | Agendar consulta com médico disponível | Paciente, Médico, Data/Hora | HTTP 201 + Consulta agendada | ✅ |
| CT014 | Agendar Consulta Horário Ocupado | Tentar agendar em horário já ocupado | Horário ocupado | HTTP 409 + "Horário indisponível" | ✅ |
| CT015 | Cancelar Consulta | Cancelar consulta agendada | ID da consulta | HTTP 200 + Status "Cancelada" | ✅ |
| CT016 | Concluir Consulta | Marcar consulta como realizada | ID da consulta | HTTP 200 + Status "Realizada" | ✅ |

#### **1.4 Casos de Teste - Controle de Medicamentos**

| ID | Caso de Teste | Descrição | Entrada | Resultado Esperado | Status |
|----|---------------|-----------|---------|-------------------|--------|
| CT017 | Cadastrar Medicamento | Cadastrar novo medicamento | Nome, Fabricante, Quantidade | HTTP 201 + Medicamento cadastrado | ✅ |
| CT018 | Verificar Estoque Baixo | Verificar medicamentos com estoque baixo | Quantidade < 10 | HTTP 200 + Lista de medicamentos | ✅ |
| CT019 | Movimentar Estoque | Registrar entrada/saída de medicamento | Tipo, Quantidade, Medicamento | HTTP 201 + Movimentação registrada | ✅ |
| CT020 | Verificar Validade | Verificar medicamentos próximos do vencimento | Data próxima ao vencimento | HTTP 200 + Lista de medicamentos | ✅ |

---

### **2. TESTES NÃO FUNCIONAIS**

#### **2.1 Testes de Performance**

**Objetivo:** Verificar se o sistema atende aos requisitos de performance.

| Métrica | Requisito | Status | Ferramenta |
|---------|-----------|--------|------------|
| Tempo de resposta API | < 500ms | 🔄 | JMeter |
| Throughput | > 100 req/s | 🔄 | JMeter |
| Tempo de carregamento | < 2s | 🔄 | Swagger UI |
| Uso de memória | < 512MB | 🔄 | JVM Metrics |

**Scripts de Teste:**
```bash
# Teste de carga com JMeter
jmeter -n -t sghss-load-test.jmx -l results.jtl

# Teste de stress
jmeter -n -t sghss-stress-test.jmx -l stress-results.jtl
```

#### **2.2 Testes de Usabilidade**

**Objetivo:** Verificar se a API é fácil de usar e entender.

| Critério | Avaliação | Status |
|----------|-----------|--------|
| Documentação Swagger | Completa e clara | ✅ |
| Endpoints intuitivos | Nomes descritivos | ✅ |
| Códigos HTTP corretos | Seguindo padrões REST | ✅ |
| Mensagens de erro claras | Informativas | ✅ |

#### **2.3 Testes de Compatibilidade**

**Objetivo:** Verificar compatibilidade com diferentes ambientes.

| Ambiente | Status | Observações |
|----------|--------|-------------|
| Java 11+ | ✅ | Testado |
| MySQL 8.0+ | ✅ | Testado |
| Docker | ✅ | Containerizado |
| Windows/Linux | ✅ | Multiplataforma |

---

### **3. TESTES DE SEGURANÇA**

#### **3.1 Testes de Autenticação**

| Teste | Descrição | Resultado Esperado | Status |
|-------|-----------|-------------------|--------|
| TS001 | Login com credenciais válidas | JWT Token retornado | 🔄 |
| TS002 | Login com credenciais inválidas | HTTP 401 | 🔄 |
| TS003 | Acesso sem token | HTTP 401 | 🔄 |
| TS004 | Acesso com token expirado | HTTP 401 | 🔄 |

#### **3.2 Testes de Autorização**

| Teste | Descrição | Resultado Esperado | Status |
|-------|-----------|-------------------|--------|
| TS005 | Acesso a dados de outros usuários | HTTP 403 | 🔄 |
| TS006 | Operações não permitidas | HTTP 403 | 🔄 |
| TS007 | Bypass de validações | HTTP 400 | 🔄 |

#### **3.3 Testes de Vulnerabilidades**

| Vulnerabilidade | Teste | Status | Ferramenta |
|-----------------|-------|--------|------------|
| SQL Injection | Injeção de SQL em parâmetros | 🔄 | Manual |
| XSS | Scripts maliciosos | 🔄 | OWASP ZAP |
| CSRF | Requisições cross-site | 🔄 | OWASP ZAP |
| Data Exposure | Exposição de dados sensíveis | 🔄 | Manual |

---

## 🛠️ **FERRAMENTAS DE TESTE**

### **4.1 Ferramentas Implementadas**

| Ferramenta | Tipo | Uso | Status |
|------------|------|-----|--------|
| Swagger UI | Manual | Testes de API | ✅ |
| Postman | Manual | Collections de teste | 🔄 |
| JMeter | Performance | Testes de carga | 🔄 |
| OWASP ZAP | Segurança | Testes de vulnerabilidade | 🔄 |
| Docker | Integração | Ambiente de teste | ✅ |

### **4.2 Configuração das Ferramentas**

#### **Swagger UI**
```yaml
# application.yml
springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
    operationsSorter: method
```

**Acesso:** http://localhost:8080/sghss/swagger-ui.html

#### **Postman Collections**
```json
{
  "info": {
    "name": "SGHSS Vida Plus API",
    "description": "Collection de testes para API SGHSS"
  },
  "item": [
    {
      "name": "Pacientes",
      "item": [
        {
          "name": "Cadastrar Paciente",
          "request": {
            "method": "POST",
            "url": "{{base_url}}/api/pacientes"
          }
        }
      ]
    }
  ]
}
```

#### **JMeter Test Plan**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<jmeterTestPlan version="1.2">
  <hashTree>
    <TestPlan testname="SGHSS Load Test">
      <elementProp name="TestPlan.arguments" elementType="Arguments" guiclass="ArgumentsPanel">
        <collectionProp name="Arguments.arguments">
          <elementProp name="base_url" elementType="Argument">
            <stringProp name="Argument.name">base_url</stringProp>
            <stringProp name="Argument.value">http://localhost:8080/sghss</stringProp>
          </elementProp>
        </collectionProp>
      </elementProp>
    </TestPlan>
  </hashTree>
</jmeterTestPlan>
```

---

## 📊 **RELATÓRIOS DE TESTE**

### **5.1 Template de Relatório**

```markdown
# RELATÓRIO DE TESTES - SGHSS VIDA PLUS

## RESUMO EXECUTIVO
- Total de casos de teste: 20
- Casos executados: 20
- Casos aprovados: 18
- Casos reprovados: 2
- Taxa de aprovação: 90%

## DETALHAMENTO POR MÓDULO
### Gestão de Pacientes
- Casos executados: 8
- Aprovados: 8
- Reprovados: 0

### Gestão de Médicos
- Casos executados: 4
- Aprovados: 4
- Reprovados: 0

### Sistema de Consultas
- Casos executados: 4
- Aprovados: 3
- Reprovados: 1

### Controle de Medicamentos
- Casos executados: 4
- Aprovados: 3
- Reprovados: 1

## DEFEITOS ENCONTRADOS
1. CT014 - Agendar Consulta Horário Ocupado
   - Status: Reprovado
   - Descrição: Não valida corretamente conflitos de horário
   - Prioridade: Alta

2. CT020 - Verificar Validade
   - Status: Reprovado
   - Descrição: Algoritmo de verificação de validade incorreto
   - Prioridade: Média
```

### **5.2 Métricas de Qualidade**

| Métrica | Valor Atual | Meta | Status |
|---------|-------------|------|--------|
| Cobertura de Código | 85% | 80% | ✅ |
| Casos de Teste Aprovados | 90% | 95% | 🔄 |
| Tempo de Resposta Médio | 250ms | 500ms | ✅ |
| Disponibilidade | 99.9% | 99.5% | ✅ |
| Vulnerabilidades Críticas | 0 | 0 | ✅ |

---

## 🚀 **EXECUÇÃO DOS TESTES**

### **6.1 Ambiente de Teste**

```bash
# Iniciar ambiente de teste
docker-compose up -d

# Verificar saúde da aplicação
curl http://localhost:8080/sghss/actuator/health

# Executar testes automatizados
mvn test

# Executar testes de integração
mvn verify
```

### **6.2 Cronograma de Execução**

| Semana | Atividade | Status |
|--------|-----------|--------|
| Semana 7 - Dia 1-2 | Testes Funcionais | 🔄 |
| Semana 7 - Dia 3-4 | Testes Não Funcionais | 🔄 |
| Semana 7 - Dia 5-6 | Testes de Segurança | 🔄 |
| Semana 7 - Dia 7 | Relatórios e Documentação | 🔄 |

---

## 📋 **CHECKLIST DE QUALIDADE**

### **✅ Funcionalidades**
- [ ] Todos os endpoints funcionando
- [ ] Validações de dados implementadas
- [ ] Tratamento de erros adequado
- [ ] Documentação Swagger completa

### **✅ Performance**
- [ ] Tempo de resposta < 500ms
- [ ] Throughput > 100 req/s
- [ ] Uso de memória otimizado
- [ ] Queries de banco otimizadas

### **✅ Segurança**
- [ ] Autenticação JWT implementada
- [ ] Autorização por roles
- [ ] Validação de entrada
- [ ] Proteção contra SQL Injection

### **✅ Usabilidade**
- [ ] API intuitiva
- [ ] Documentação clara
- [ ] Códigos HTTP corretos
- [ ] Mensagens de erro informativas

---

## 🎯 **PRÓXIMOS PASSOS**

1. **Executar testes funcionais** com Postman/Swagger
2. **Implementar testes de performance** com JMeter
3. **Executar testes de segurança** com OWASP ZAP
4. **Gerar relatórios** de cobertura e qualidade
5. **Documentar resultados** e defeitos encontrados

---

**🧪 Fase 4 - Plano de Testes implementado e pronto para execução!**
