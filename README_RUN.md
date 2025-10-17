# 🚀 Guia de Execução - SGHSS Vida Plus

Este guia descreve as diferentes formas de executar o projeto localmente para desenvolvimento e testes.

## 📋 Pré-requisitos

Escolha uma das opções abaixo:

### Opção 1: Desenvolvimento Local
- **Java JDK 11+** instalado
- **Maven 3.6+** (ou use o wrapper incluído)
- **MySQL 8.0+** (ou use Docker Compose)

### Opção 2: Docker
- **Docker** e **Docker Compose** instalados

---

## 🛠️ Métodos de Execução

### 1️⃣ Usando Maven Wrapper (Recomendado)

O projeto inclui um Maven Wrapper que baixa automaticamente a versão correta do Maven.

**Linux/Mac:**
```bash
./mvnw clean install -DskipTests
./mvnw spring-boot:run
```

**Windows (PowerShell):**
```powershell
.\mvnw.cmd clean install -DskipTests
.\mvnw.cmd spring-boot:run
```

### 2️⃣ Usando Maven Instalado

Se você já tem o Maven instalado globalmente:

```bash
# Verificar versão do Maven
mvn -v

# Build do projeto
mvn clean install -DskipTests

# Executar a aplicação
mvn spring-boot:run
```

### 3️⃣ Usando Docker Compose (Recomendado para Testes)

O Docker Compose sobe o banco MySQL e a aplicação automaticamente:

```bash
# Build e iniciar todos os serviços
docker-compose up --build

# Ou para desenvolvimento (com rebuild automático)
docker-compose -f docker-compose.dev.yml up --build

# Para parar os serviços
docker-compose down

# Para parar e limpar volumes
docker-compose down -v
```

**Após iniciar, acesse:**
- API: http://localhost:8080/sghss/api/medicos
- Swagger: http://localhost:8080/sghss/swagger-ui.html
- Health Check: http://localhost:8080/sghss/actuator/health

### 4️⃣ Usando JAR Compilado

Depois de compilar o projeto, você pode executar o JAR diretamente:

```bash
# Compilar
mvn clean package -DskipTests

# Executar
java -jar target/sghss-vidaplus-1.0.0.jar
```

---

## 🧪 Executando Testes

### Testes Unitários e de Integração

O projeto usa H2 in-memory para testes:

```bash
# Executar todos os testes
mvn test

# Executar testes com profile de teste
mvn test -Dspring.profiles.active=test

# Executar testes com cobertura
mvn test jacoco:report
```

### Verificar Relatório de Cobertura

Após executar os testes com cobertura, abra:
```
target/site/jacoco/index.html
```

---

## 🔧 Configuração do Banco de Dados

### MySQL Local

1. **Criar o banco de dados:**
```sql
CREATE DATABASE sghss_vidaplus;
CREATE USER 'sghss_user'@'localhost' IDENTIFIED BY 'sghss_password';
GRANT ALL PRIVILEGES ON sghss_vidaplus.* TO 'sghss_user'@'localhost';
FLUSH PRIVILEGES;
```

2. **Configurar credenciais** em `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/sghss_vidaplus
    username: sghss_user
    password: sghss_password
```

### H2 para Testes (já configurado)

Os testes usam H2 in-memory automaticamente. Veja `src/test/resources/application-test.yml`.

---

## 📊 GitHub Actions

O projeto possui CI/CD configurado no GitHub Actions:

1. **Build automático** em push/PR para `main`
2. **Testes automáticos** com H2
3. **Cache do Maven** para builds mais rápidos
4. **Upload de artefatos** (JAR compilado)

Para ver os logs: https://github.com/Marcio606/sghss-vidaplus-prototipo/actions

---

## 🐛 Problemas Comuns

### Porta 8080 Ocupada
```bash
# Mudar porta no application.yml ou via linha de comando:
mvn spring-boot:run -Dserver.port=8081
```

### Erro de Compilação com Lombok
- Certifique-se de que seu IDE tem o **plugin Lombok** instalado
- Habilite **Annotation Processing** nas configurações do IDE

### Maven Wrapper não Funciona
```bash
# Re-download do wrapper
mvn wrapper:wrapper

# Dar permissão de execução (Linux/Mac)
chmod +x mvnw
```

### Erro de Conexão com MySQL
- Verifique se o MySQL está rodando: `sudo systemctl status mysql`
- Teste a conexão: `mysql -u sghss_user -p`

---

## 📝 Variáveis de Ambiente

Você pode usar variáveis de ambiente para sobrescrever configurações:

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/sghss_vidaplus
export SPRING_DATASOURCE_USERNAME=sghss_user
export SPRING_DATASOURCE_PASSWORD=sghss_password

mvn spring-boot:run
```

---

## 📞 Suporte

- **Desenvolvedor:** Marcio Machado Moreira
- **RU:** 4543545
- **GitHub:** https://github.com/Marcio606
- **Issues:** https://github.com/Marcio606/sghss-vidaplus-prototipo/issues

---

## ✅ Verificação Rápida

Após iniciar a aplicação, verifique se está funcionando:

```bash
# Health check
curl http://localhost:8080/sghss/actuator/health

# Listar médicos (deve retornar [] inicialmente)
curl http://localhost:8080/sghss/api/medicos
```

**Resposta esperada:**
```json
{
  "status": "UP"
}
```

