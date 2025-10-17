# 🚀 Quick Start Guide - SGHSS Vida Plus

## TL;DR - Execução Rápida

### Opção 1: Docker (Mais Simples) 🐳
```bash
docker-compose up --build
```
Acesse: http://localhost:8080/sghss/swagger-ui.html

### Opção 2: Maven Wrapper 📦
```bash
./mvnw clean install -DskipTests
./mvnw spring-boot:run
```

### Opção 3: Somente Testes 🧪
```bash
./mvnw test
```

---

## 📋 Pré-requisitos

**Escolha uma opção:**

| Método | Requisitos |
|--------|-----------|
| Docker | Docker + Docker Compose |
| Maven | Java 11 + Maven (ou use o wrapper) |
| JAR | Java 11 |

---

## ⚡ Comandos Essenciais

### Build e Execução
```bash
# Build completo
./mvnw clean install

# Build sem testes (rápido)
./mvnw clean install -DskipTests

# Executar aplicação
./mvnw spring-boot:run

# Executar JAR
java -jar target/sghss-vidaplus-1.0.0.jar
```

### Testes
```bash
# Todos os testes
./mvnw test

# Com cobertura
./mvnw test jacoco:report

# Ver relatório: target/site/jacoco/index.html
```

### Docker
```bash
# Iniciar tudo
docker-compose up -d

# Ver logs
docker-compose logs -f

# Parar
docker-compose down

# Rebuild
docker-compose up --build
```

### Verificação
```bash
# Health check
curl http://localhost:8080/sghss/actuator/health

# Listar médicos
curl http://localhost:8080/sghss/api/medicos

# Swagger UI
# http://localhost:8080/sghss/swagger-ui.html
```

---

## 🗄️ Banco de Dados

### MySQL Local (Opcional)
```sql
CREATE DATABASE sghss_vidaplus;
CREATE USER 'sghss_user'@'localhost' IDENTIFIED BY 'sghss_password';
GRANT ALL PRIVILEGES ON sghss_vidaplus.* TO 'sghss_user'@'localhost';
```

### H2 (Testes)
Automático - já configurado em `application-test.yml`

---

## 🌐 Endpoints Principais

| Endpoint | Descrição |
|----------|-----------|
| `/sghss/api/medicos` | API de Médicos |
| `/sghss/api/pacientes` | API de Pacientes |
| `/sghss/api/consultas` | API de Consultas |
| `/sghss/swagger-ui.html` | Documentação API |
| `/sghss/actuator/health` | Health Check |

---

## 🐛 Troubleshooting Rápido

### Porta 8080 ocupada
```bash
./mvnw spring-boot:run -Dserver.port=8081
```

### Maven wrapper não funciona
```bash
chmod +x mvnw
# ou
mvn wrapper:wrapper
```

### Erro de Lombok
- Instale plugin Lombok no seu IDE
- Ative "Annotation Processing"

### MySQL não conecta
```bash
# Verifique se está rodando
sudo systemctl status mysql

# Teste conexão
mysql -u sghss_user -p
```

---

## 📚 Documentação Completa

- **Setup Detalhado:** [README_RUN.md](README_RUN.md)
- **Melhorias Aplicadas:** [CONFIGURATION_IMPROVEMENTS.md](CONFIGURATION_IMPROVEMENTS.md)
- **Projeto Principal:** [README.md](README.md)

---

## 🔗 Links Úteis

- **Repositório:** https://github.com/Marcio606/sghss-vidaplus-prototipo
- **Issues:** https://github.com/Marcio606/sghss-vidaplus-prototipo/issues
- **Actions:** https://github.com/Marcio606/sghss-vidaplus-prototipo/actions

---

## ✅ Checklist de Verificação

Após iniciar o projeto, verifique:

- [ ] Aplicação iniciou sem erros
- [ ] Health check retorna `{"status":"UP"}`
- [ ] Swagger UI está acessível
- [ ] Endpoints de API respondem
- [ ] Banco de dados está conectado

---

**Dúvidas?** Consulte [README_RUN.md](README_RUN.md) para guia completo!
