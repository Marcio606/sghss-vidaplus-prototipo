README - Como executar o projeto localmente
=========================================

Este arquivo descreve formas rápidas de rodar a API localmente e executar os testes. Use o método que for mais conveniente no seu ambiente (Windows PowerShell).

1) Usando o Maven instalado localmente

Pré-requisitos:
- JDK 11 instalado e variável JAVA_HOME apontando para o JDK
- Maven instalado (opcional se preferir usar o wrapper)

Comandos (PowerShell):

```powershell
mvn -v
mvn clean package -DskipTests
# Rodar a aplicação
mvn spring-boot:run
```

2) Usando o Maven Wrapper (mvnw)

Se preferir não instalar o Maven globalmente, há scripts `mvnw.cmd` e `mvnw` no repositório. Em Windows PowerShell:

```powershell
# No root do projeto
.\mvnw.cmd -v
.\mvnw.cmd clean package -DskipTests
.\mvnw.cmd spring-boot:run
```

Observação: Caso o wrapper não funcione (problemas com o JAR), use a seção Docker abaixo ou instale o Maven.

3) Usando Docker (sem instalar Maven/Java localmente)

Pré-requisito: Docker Desktop instalado e em execução.

```powershell
docker run --rm -v ${PWD}:/workspace -w /workspace maven:3.8.8-openjdk-11 mvn -B clean package -DskipTests
```

4) Rodando os testes (profile `test` com H2)

O projeto já inclui um profile de teste que usa H2 em memória. Para executar os testes:

```powershell
mvn test -Dspring.profiles.active=test
# ou com wrapper
.\mvnw.cmd test -Dspring.profiles.active=test
```

5) Logs do CI (GitHub Actions)

Eu adicionei um workflow que roda o build no GitHub Actions. Se um build falhar, abra a aba "Actions" do repositório e cole os logs relevantes aqui ou compartilhe o link da execução.

6) Problemas comuns
- Se a porta 8080 estiver ocupada, altere `server.port` em `application.yml` ou use `-Dserver.port=0` para porta aleatória.
- Se houver erros de compilação relacionados ao Lombok, certifique-se de que o seu IDE tem o plugin Lombok instalado e o annotation processing habilitado.

Se quiser, posso também criar um `docker-compose` de desenvolvimento que levanta o banco MySQL local para testes mais realistas.

7) Docker Compose (modo recomendado para o professor testar)

Se o avaliador/ professor quiser testar o projeto rapidamente sem instalar Java/Maven localmente, pode usar o `docker-compose.dev.yml` incluído. Ele sobe um MySQL e constrói/roda a aplicação via Docker.

```powershell
# A partir do root do repositório
docker-compose -f docker-compose.dev.yml up --build

# Depois acesse:
# http://localhost:8080/sghss/api/medicos
```

Notas para o avaliador:
- O GitHub Actions foi configurado para rodar o build automaticamente. Abra `https://github.com/Marcio606/sghss-vidaplus-prototipo/actions` para ver o status do build e logs.
- Se o endpoint `/sghss/api/medicos` não retornar dados inicialmente, os testes usam H2 e o banco MySQL será populado conforme as operações CRUD executadas.

