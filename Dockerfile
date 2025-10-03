# Multi-stage build para otimizar o tamanho da imagem
FROM maven:3.8.6-openjdk-11-slim AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos de dependências
COPY pom.xml .

# Baixar dependências (cache layer)
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Build da aplicação
RUN mvn clean package -DskipTests

# Imagem de produção
FROM openjdk:11-jre-slim

# Instalar curl para health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Criar usuário não-root para segurança
RUN groupadd -r sghss && useradd -r -g sghss sghss

# Definir diretório de trabalho
WORKDIR /app

# Copiar JAR da aplicação
COPY --from=build /app/target/sghss-vidaplus-*.jar app.jar

# Criar diretórios necessários
RUN mkdir -p /app/uploads /app/backups && \
    chown -R sghss:sghss /app

# Mudar para usuário não-root
USER sghss

# Expor porta
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/sghss/actuator/health || exit 1

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
