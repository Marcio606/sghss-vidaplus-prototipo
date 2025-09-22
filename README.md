# 🏥 SGHSS Vida Plus - Sistema de Gestão Hospitalar

## 📋 Sobre o Projeto

O **SGHSS Vida Plus** é um sistema completo de gestão hospitalar desenvolvido em **Java Spring Boot** para gerenciar pacientes, médicos, consultas, estoque de medicamentos e relatórios médicos.

## 🚀 Tecnologias Utilizadas

### Backend
- **Java 11**
- **Spring Boot 2.7.14**
- **Spring Data JPA**
- **Spring Security**
- **MySQL 8.0**
- **Maven**
- **Lombok**
- **MapStruct**
- **Swagger/OpenAPI**

### Dependências Principais
```xml
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Validation
- MySQL Connector
- JWT (JSON Web Token)
- Swagger UI
- Lombok
- MapStruct
```

## 🏗️ Arquitetura do Sistema

```
sghss-vidaplus/
├── src/main/java/com/vidaplus/sghss/
│   ├── controller/          # Controladores REST
│   ├── service/            # Lógica de negócio
│   ├── repository/         # Acesso a dados
│   ├── model/             # Entidades do domínio
│   ├── config/            # Configurações
│   └── SghssApplication.java
├── src/main/resources/
│   ├── application.yml     # Configurações
│   └── static/            # Recursos estáticos
├── pom.xml                # Dependências Maven
└── README.md
```

## 📊 Entidades do Sistema

### 🧑‍⚕️ **Paciente**
- Dados pessoais completos
- Histórico médico
- Prontuário digital
- Controle de consultas

### 👨‍⚕️ **Médico**
- Cadastro profissional
- Especialidades
- Agenda de atendimentos
- CRM e qualificações

### 📅 **Consulta**
- Agendamento inteligente
- Status de consulta
- Prescrições médicas
- Faturamento

### 💊 **Medicamento**
- Controle de estoque
- Alertas de validade
- Categorização
- Movimentações

### 📋 **Prontuário**
- Histórico médico digital
- Exames e resultados
- Prescrições
- Anexos

## 🛠️ Instalação e Configuração

### Pré-requisitos
- Java 11+
- MySQL 8.0+
- Maven 3.6+

### 1. Clone o Repositório
```bash
git clone https://github.com/Marcio606/sghss-vidaplus.git
cd sghss-vidaplus
```

### 2. Configure o Banco de Dados
```sql
CREATE DATABASE sghss_vidaplus;
CREATE USER 'sghss_user'@'localhost' IDENTIFIED BY 'sghss_password';
GRANT ALL PRIVILEGES ON sghss_vidaplus.* TO 'sghss_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configure as Variáveis
Edite o arquivo `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/sghss_vidaplus
    username: sghss_user
    password: sghss_password
```

### 4. Execute o Projeto
```bash
mvn clean install
mvn spring-boot:run
```

## 🌐 Endpoints da API

### Pacientes
```
GET    /api/pacientes              # Listar pacientes
GET    /api/pacientes/{id}         # Buscar por ID
GET    /api/pacientes/cpf/{cpf}    # Buscar por CPF
POST   /api/pacientes              # Cadastrar paciente
PUT    /api/pacientes/{id}         # Atualizar paciente
DELETE /api/pacientes/{id}         # Deletar paciente
```

### Médicos
```
GET    /api/medicos                # Listar médicos
GET    /api/medicos/{id}           # Buscar por ID
GET    /api/medicos/crm/{crm}      # Buscar por CRM
POST   /api/medicos                # Cadastrar médico
PUT    /api/medicos/{id}           # Atualizar médico
DELETE /api/medicos/{id}           # Deletar médico
```

### Consultas
```
GET    /api/consultas              # Listar consultas
GET    /api/consultas/{id}         # Buscar por ID
POST   /api/consultas              # Agendar consulta
PUT    /api/consultas/{id}         # Atualizar consulta
DELETE /api/consultas/{id}         # Cancelar consulta
```

### Medicamentos
```
GET    /api/medicamentos           # Listar medicamentos
GET    /api/medicamentos/{id}      # Buscar por ID
POST   /api/medicamentos           # Cadastrar medicamento
PUT    /api/medicamentos/{id}      # Atualizar medicamento
DELETE /api/medicamentos/{id}      # Deletar medicamento
```

## 📚 Documentação da API

Acesse a documentação interativa do Swagger:
```
http://localhost:8080/sghss/swagger-ui.html
```

## 🔐 Autenticação

O sistema utiliza JWT (JSON Web Token) para autenticação:

```bash
# Login
POST /api/auth/login
{
  "email": "admin@sghss.com",
  "password": "admin123"
}

# Usar token nas requisições
Authorization: Bearer <token>
```

## 📊 Funcionalidades Principais

### ✅ **Gestão de Pacientes**
- Cadastro completo com validações
- Busca avançada por múltiplos critérios
- Histórico médico digital
- Controle de aniversariantes

### ✅ **Gestão de Médicos**
- Cadastro profissional completo
- Controle de especialidades
- Gestão de agenda
- Validação de CRM

### ✅ **Sistema de Consultas**
- Agendamento inteligente
- Controle de status
- Prescrições médicas
- Faturamento integrado

### ✅ **Controle de Estoque**
- Gestão de medicamentos
- Alertas de validade
- Controle de movimentações
- Relatórios de consumo

### ✅ **Prontuários Digitais**
- Histórico médico completo
- Exames e resultados
- Prescrições digitais
- Anexos de documentos

## 📈 Relatórios e Estatísticas

### 📊 **Dashboard Executivo**
- Total de pacientes ativos
- Consultas realizadas
- Faturamento mensal
- Medicamentos em estoque baixo

### 📋 **Relatórios Disponíveis**
- Pacientes por faixa etária
- Consultas por especialidade
- Medicamentos mais utilizados
- Faturamento por médico

## 🚀 Como Executar

### Desenvolvimento
```bash
mvn spring-boot:run
```

### Produção
```bash
mvn clean package
java -jar target/sghss-vidaplus-1.0.0.jar
```

## 🐳 Docker (Opcional)

```bash
# Build da imagem
docker build -t sghss-vidaplus .

# Executar container
docker run -p 8080:8080 sghss-vidaplus
```

## 🧪 Testes

```bash
# Executar todos os testes
mvn test

# Executar testes com coverage
mvn test jacoco:report
```

## 📱 Integração Frontend

O sistema está preparado para integração com:
- **React.js** / **Vue.js** / **Angular**
- **React Native** (Mobile)
- **Flutter** (Mobile)

## 🔧 Configurações Avançadas

### Logs
```yaml
logging:
  level:
    com.vidaplus.sghss: DEBUG
    org.hibernate.SQL: DEBUG
```

### Cache
```yaml
spring:
  cache:
    type: redis
  redis:
    host: localhost
    port: 6379
```

## 📞 Suporte

### Desenvolvedor
- **Nome:** Marcio Machado Moreira
- **RU:** 4543545
- **Email:** marcio606@email.com
- **GitHub:** https://github.com/Marcio606

### Documentação
- **API Docs:** http://localhost:8080/sghss/swagger-ui.html
- **Health Check:** http://localhost:8080/sghss/actuator/health

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para detalhes.

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 🎯 Roadmap

### Versão 1.1
- [ ] Autenticação OAuth2
- [ ] Notificações push
- [ ] Integração com SUS
- [ ] Telemedicina

### Versão 1.2
- [ ] IA para diagnósticos
- [ ] Blockchain para prontuários
- [ ] IoT para monitoramento
- [ ] Analytics avançado

---

## 🏆 Status do Projeto

![Status](https://img.shields.io/badge/Status-Produção-green)
![Versão](https://img.shields.io/badge/Versão-1.0.0-blue)
![Java](https://img.shields.io/badge/Java-11-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.14-brightgreen)
![License](https://img.shields.io/badge/License-MIT-yellow)

**© 2025 SGHSS Vida Plus - Sistema Hospitalar Completo**