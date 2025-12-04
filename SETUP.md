# Setup - SGHSS Vida Plus Protótipo

**Data:** Dezembro 2025  
**Status:** Sincronização com MySQL concluída ✅ | Build Java pendente ⏳

---

## 📋 Pré-requisitos Instalados

### ✅ Concluído
- **MySQL 8.0** rodando na porta 3306
  - Usuário: `root`
  - Senha: `159753`
  - Database: `sghss_vidaplus` (existente e acessível)
  - Teste: `mysql -u root -p159753 -h 127.0.0.1 -P 3306 -e "SELECT VERSION();"`
  - Resultado: `8.0.40` ✅

- **Maven 3.9.6** instalado localmente
  - Caminho: `C:\Users\Marcio Moreira\tools\apache-maven-3.9.6`
  - Teste: `mvn -v` mostra Maven 3.9.6 ✅

- **Código-fonte** restaurado e limpo
  - Sem merge conflicts ✅
  - Arquivos Java válidos (Paciente.java, Consulta.java, PacienteRepository.java) ✅

- **Configuração** do aplicativo atualizada
  - `src/main/resources/application.yml` aponta para MySQL local ✅
  - Credenciais corretas (root:159753) ✅
  - JPA com `hibernate.ddl-auto: update` (criará tabelas automaticamente) ✅

### ⏳ Pendente: Resolver Incompatibilidade Java

**Problema:** Java 24 (atualmente instalado) é incompatível com Lombok 1.18.x usado pelo projeto Spring Boot 2.7.14.

**Erro:** `java.lang.NoSuchFieldException: com.sun.tools.javac.code.TypeTag :: UNKNOWN`

---

## 🔧 Solução: Escolha UMA opção abaixo

### **OPÇÃO 1: Usar Java 11 LTS (RECOMENDADO)**

Java 11 é totalmente compatível com Spring Boot 2.7 e Lombok 1.18.x. É a solução mais simples.

#### Passo 1: Baixar Java 11 LTS
- Acesse: https://www.oracle.com/java/technologies/downloads/#java11
- Clique em: **JDK 11.0.X** → **Windows x64 Installer** (arquivo `.exe`)
- Baixe e execute (deixar instalação no caminho padrão)

#### Passo 2: Configurar JAVA_HOME no Windows
Abra **PowerShell como Administrador** e execute:

```powershell
# Defina a variável de ambiente
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Java\jdk-11.0.X", "Machine")
```

Substitua `jdk-11.0.X` pela versão exata que você instalou (ex: `jdk-11.0.21_9`).

#### Passo 3: Abra um NOVO PowerShell (importante!) e teste:

```powershell
java -version
# Deve aparecer: java version "11.0.X"
```

#### Passo 4: Build e Run (no diretório do projeto)

```powershell
cd 'C:\Users\Marcio Moreira\Documents\sghss-vidaplus'

# Build
mvn -DskipTests clean package

# Run (a aplicação estará disponível em http://localhost:8080/sghss)
mvn -DskipTests spring-boot:run
```

Quando ver a mensagem:
```
Tomcat started on port(s): 8080 (http)
Started Application in X seconds
```

A API está pronta! 🚀

---

### **OPÇÃO 2: Atualizar Spring Boot para 3.x (compatível com Java 24)**

Se preferir manter Java 24, atualize o Spring Boot para versão 3.2 ou superior que suporta Java 24.

**Comando (no diretório do projeto):**

```powershell
# Editar pom.xml manualmente ou usar:
# Abra pom.xml, localize a seção <parent> e altere:
# <version>2.7.14</version>  →  <version>3.2.0</version>

# Depois:
mvn -DskipTests clean package
mvn -DskipTests spring-boot:run
```

**Nota:** Essa opção requer ajustes adicionais (imports, dependências) que podem exigir refatoração do código.

---

### **OPÇÃO 3: Docker (sem instalar Java localmente)**

Se tiver Docker Desktop instalado:

```powershell
cd 'C:\Users\Marcio Moreira\Documents\sghss-vidaplus'

# Build da imagem
docker build -t sghss-app .

# Run (conectando ao MySQL do host Windows)
docker run -d --name sghss-app-container \
  -p 8080:8080 \
  -e "SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/sghss_vidaplus?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true" \
  -e "SPRING_DATASOURCE_USERNAME=root" \
  -e "SPRING_DATASOURCE_PASSWORD=159753" \
  sghss-app

# Ver logs
docker logs -f sghss-app-container
```

---

## 📡 Testar a API (após estar rodando)

### Health Check
```powershell
Invoke-RestMethod -Uri 'http://localhost:8080/sghss/actuator/health'
# Resposta esperada: {"status":"UP"}
```

### Swagger/OpenAPI (visualizar endpoints)
```
http://localhost:8080/sghss/swagger-ui.html
```

### Listar Pacientes (exemplo)
```powershell
Invoke-RestMethod -Uri 'http://localhost:8080/sghss/api/pacientes' -Method GET
```

---

## 🗄️ Estrutura de Pastas Importantes

```
sghss-vidaplus/
├── src/
│   ├── main/
│   │   ├── java/com/vidaplus/sghss/
│   │   │   ├── model/         (Paciente, Consulta, etc.)
│   │   │   ├── repository/    (PacienteRepository, etc.)
│   │   │   ├── controller/    (PacienteController, etc.)
│   │   │   └── Application.java
│   │   └── resources/
│   │       └── application.yml (configuração - JÁ ATUALIZADO)
│   └── test/
├── pom.xml                    (Maven config - JÁ ATUALIZADO)
├── Dockerfile                 (para Docker)
└── docker-compose.yml         (alterna — pode subir MySQL em container)
```

---

## 🔍 Verificação: Banco de Dados

### Conectar ao MySQL e verificar tabelas

```powershell
mysql -u root -p159753 -h 127.0.0.1 -P 3306 sghss_vidaplus -e "SHOW TABLES;"
```

**Nota:** Quando a API é iniciada pela primeira vez, o Hibernate (JPA) cria automaticamente as tabelas conforme o `ddl-auto: update` está configurado.

---

## 🚨 Problemas Conhecidos & Soluções

| Problema | Solução |
|----------|---------|
| `java version "24.0.2"` ao rodar Maven | Instale Java 11 (OPÇÃO 1) ou use Docker (OPÇÃO 3) |
| `TypeTag :: UNKNOWN` compile error | Mesmo problema acima — resolver Java version |
| `Connection refused: 127.0.0.1:3306` | Certifique-se MySQL está rodando: `mysql -u root -p159753 -e "SELECT 1;"` |
| `Port 8080 already in use` | Mude porta em `application.yml` → `server.port: 8081` |
| `Swagger-UI não abre` | Acesse: `http://localhost:8080/sghss/swagger-ui.html` (com `/sghss` context path) |
| `Lombok não gera getters/setters` | Atualize IDE (IntelliJ/VS Code) config para reconhecer Lombok |

---

## 📝 Próximos Passos (Após API Rodando)

1. **Testes**: Execute requisições HTTP via Postman/curl aos endpoints `/api/pacientes`, `/api/consultas`, etc.
2. **Seed de Dados**: Crie um script `DataLoader.java` para popular o banco com dados iniciais
3. **Deploy**: Configure CI/CD (GitHub Actions), deploy em cloud (AWS, Azure, etc.)
4. **Frontend**: Desenvolva interface React/Vue para consumir a API

---

## 📞 Contato & Suporte

- **Repositório:** https://github.com/Marcio606/sghss-vidaplus-prototipo
- **Documentação Técnica:** Ver `/docs` neste repositório
- **Issues:** Reporte em GitHub Issues

---

**Versão:** 1.0.0  
**Última Atualização:** Dezembro 3, 2025  
**Status de Compilação:** ⏳ Aguardando Java 11 para build final
