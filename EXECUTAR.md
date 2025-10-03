# 🚀 COMO EXECUTAR O SGHSS VIDA PLUS

## 📋 **OPÇÕES DE EXECUÇÃO**

### **1️⃣ EXECUÇÃO LOCAL (Recomendado para desenvolvimento)**

#### **Pré-requisitos:**
- Java 11+
- MySQL 8.0+
- Maven 3.6+

#### **Passos:**
```bash
# 1. Clone o repositório
git clone https://github.com/Marcio606/sghss-vidaplus.git
cd sghss-vidaplus

# 2. Configure o MySQL
mysql -u root -p
CREATE DATABASE sghss_vidaplus;
CREATE USER 'sghss_user'@'localhost' IDENTIFIED BY 'sghss_password';
GRANT ALL PRIVILEGES ON sghss_vidaplus.* TO 'sghss_user'@'localhost';
FLUSH PRIVILEGES;
EXIT;

# 3. Execute a aplicação
mvn clean install
mvn spring-boot:run
```

#### **Acesso:**
- **API:** http://localhost:8080/sghss
- **Swagger:** http://localhost:8080/sghss/swagger-ui.html
- **Health:** http://localhost:8080/sghss/actuator/health

---

### **2️⃣ EXECUÇÃO COM DOCKER (Recomendado para produção)**

#### **Pré-requisitos:**
- Docker
- Docker Compose

#### **Passos:**
```bash
# 1. Clone o repositório
git clone https://github.com/Marcio606/sghss-vidaplus.git
cd sghss-vidaplus

# 2. Execute com Docker Compose
docker-compose up -d

# 3. Verifique os logs
docker-compose logs -f app
```

#### **Acesso:**
- **API:** http://localhost:8080/sghss
- **Swagger:** http://localhost:8080/sghss/swagger-ui.html
- **MySQL:** localhost:3306

---

### **3️⃣ EXECUÇÃO COM DOCKER (Build manual)**

```bash
# 1. Build da imagem
docker build -t sghss-vidaplus .

# 2. Execute o MySQL
docker run -d --name sghss-mysql \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=sghss_vidaplus \
  -e MYSQL_USER=sghss_user \
  -e MYSQL_PASSWORD=sghss_password \
  -p 3306:3306 mysql:8.0

# 3. Execute a aplicação
docker run -d --name sghss-app \
  --link sghss-mysql:mysql \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/sghss_vidaplus \
  -e SPRING_DATASOURCE_USERNAME=sghss_user \
  -e SPRING_DATASOURCE_PASSWORD=sghss_password \
  -p 8080:8080 sghss-vidaplus
```

---

## 🔧 **CONFIGURAÇÕES**

### **Variáveis de Ambiente:**
```bash
# Banco de Dados
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/sghss_vidaplus
SPRING_DATASOURCE_USERNAME=sghss_user
SPRING_DATASOURCE_PASSWORD=sghss_password

# JPA
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false

# JWT
SGHSS_SECURITY_JWT_SECRET=sghss-vidaplus-secret-key-2025
SGHSS_SECURITY_JWT_EXPIRATION=86400000
```

### **Portas:**
- **Aplicação:** 8080
- **MySQL:** 3306
- **Swagger:** 8080/sghss/swagger-ui.html

---

## 📊 **TESTANDO A API**

### **1. Health Check:**
```bash
curl http://localhost:8080/sghss/actuator/health
```

### **2. Listar Pacientes:**
```bash
curl http://localhost:8080/sghss/api/pacientes
```

### **3. Cadastrar Paciente:**
```bash
curl -X POST http://localhost:8080/sghss/api/pacientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "cpf": "12345678901",
    "email": "joao@email.com",
    "telefone": "11999999999",
    "dataNascimento": "1990-01-01",
    "sexo": "MASCULINO"
  }'
```

---

## 🐛 **SOLUÇÃO DE PROBLEMAS**

### **Erro de Conexão com MySQL:**
```bash
# Verificar se o MySQL está rodando
sudo systemctl status mysql

# Verificar porta
netstat -tlnp | grep 3306
```

### **Erro de Porta em Uso:**
```bash
# Verificar porta 8080
netstat -tlnp | grep 8080

# Matar processo
sudo kill -9 <PID>
```

### **Erro de Permissão Docker:**
```bash
# Adicionar usuário ao grupo docker
sudo usermod -aG docker $USER
newgrp docker
```

### **Reset do Banco:**
```bash
# Local
mysql -u root -p
DROP DATABASE sghss_vidaplus;
CREATE DATABASE sghss_vidaplus;

# Docker
docker-compose down -v
docker-compose up -d
```

---

## 📱 **ACESSANDO O SISTEMA**

### **URLs Importantes:**
- **API Base:** http://localhost:8080/sghss
- **Swagger UI:** http://localhost:8080/sghss/swagger-ui.html
- **Health Check:** http://localhost:8080/sghss/actuator/health
- **Metrics:** http://localhost:8080/sghss/actuator/metrics

### **Endpoints Principais:**
```
GET    /api/pacientes              # Listar pacientes
GET    /api/medicos                # Listar médicos
GET    /api/consultas              # Listar consultas
GET    /api/medicamentos           # Listar medicamentos
GET    /api/prontuarios            # Listar prontuários
```

---

## 🎯 **PRÓXIMOS PASSOS**

1. **Configure o banco de dados**
2. **Execute a aplicação**
3. **Acesse o Swagger UI**
4. **Teste os endpoints**
5. **Configure autenticação**
6. **Integre com frontend**

---

## 📞 **SUPORTE**

Se encontrar problemas:

1. **Verifique os logs:**
   ```bash
   # Local
   tail -f logs/application.log
   
   # Docker
   docker-compose logs -f app
   ```

2. **Verifique a documentação:**
   - README.md
   - Swagger UI

3. **Contato:**
   - **Email:** marcio606@email.com
   - **GitHub:** https://github.com/Marcio606

---

**🎉 Sistema pronto para uso!**
