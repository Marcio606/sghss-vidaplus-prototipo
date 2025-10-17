# Multi-stage build para otimizar o tamanho da imagem
FROM maven:3.9.6-eclipse-temurin-11-alpine AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos de configuração do Maven primeiro (melhor cache)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Baixar dependências (cache layer)
RUN ./mvnw dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Build da aplicação
RUN ./mvnw clean package -DskipTests -B

# Imagem de produção
FROM eclipse-temurin:11-jre-alpine

# Metadados da imagem
LABEL maintainer="Marcio Machado Moreira <marcio606@email.com>"
LABEL description="SGHSS Vida Plus - Sistema de Gestão Hospitalar"
LABEL version="1.0.0"

# Instalar curl para health checks
RUN apk add --no-cache curl

# Criar usuário não-root para segurança
RUN addgroup -g 1001 sghss && \
    adduser -u 1001 -G sghss -s /bin/sh -D sghss

# Definir diretório de trabalho
WORKDIR /app

# Copiar JAR da aplicação
COPY --from=build /app/target/sghss-vidaplus-*.jar app.jar

# Criar diretórios necessários
RUN mkdir -p /app/uploads /app/backups /app/logs && \
    chown -R sghss:sghss /app

# Mudar para usuário não-root
USER sghss

# Expor porta
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/sghss/actuator/health || exit 1

# Comando para executar a aplicação com otimizações
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
