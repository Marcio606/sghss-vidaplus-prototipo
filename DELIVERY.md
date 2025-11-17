Capa:
Curso: [INFORME O CURSO]
Disciplina: [INFORME A DISCIPLINA]
Aluno: Marcio Machado Moreira
RU: 4543545
Polo: [INFORME O POLO]
Semestre: [INFORME O SEMESTRE]
Professor: [INFORME O PROFESSOR]

Projeto: SGHSS - Sistema de Gestão Hospitalar e de Serviços de Saúde (VidaPlus)
Repositório: https://github.com/Marcio606/sghss-vidaplus-prototipo

Sumário
1. Introdução
2. Mapeamento Requisitos → Implementação
3. Modelagem e Arquitetura (resumo)
4. Implementação (protótipo/back-end)
5. Plano de Testes (resumo)
6. Problemas corrigidos e observações
7. Como executar (passo a passo)
8. Anexos (diagrama, prints, scripts)
9. Conclusão
10. Referências

1. Introdução
Este documento reúne o trabalho final do projeto SGHSS - VidaPlus. Objetivo: entregar uma versão mínima funcional do backend, com modelagem, endpoints básicos, documentação de execução e artefatos solicitados pelo roteiro de projeto.

2. Mapeamento Requisitos → Implementação
- Cadastro e Atendimento de Pacientes: Endpoints básicos documentados. Implementação parcial; entidade Paciente e endpoints de CRUD presentes (ver src/main/java/...).
- Gestão de Profissionais: Endpoints de Médicos documentados; agenda e prescrições parcialmente implementadas.
- Administração Hospitalar: Não implementado (leitos, suprimentos, relatórios).
- Telemedicina: Não implementado (proposta de integração via WebRTC).
- Segurança e LGPD: Infraestrutura básica (actuator); falta implementação de autenticação/autorizações, mascaramento e logs de auditoria.
- Não-funcionais: Docker/Docker Compose e CI configurados; testes unitários básicos presentes; faltam testes de carga e segurança automatizados.

3. Modelagem e Arquitetura
- Classe chave entregue: Consulta (src/main/java/.../Consulta.java)
- Recomenda-se anexar DER/diagramas UML (.drawio ou imagens) no anexo.

4. Implementação (protótipo)
- Back-end Spring Boot 2.7.18, Java 11
- Build: Maven (mvnw)
- Banco: MySQL (docker-compose.dev.yml) / H2 para testes
- Endpoints principais: /sghss/api/medicos, /sghss/api/pacientes, /sghss/api/consultas
- Ajustes realizados: correção de entidade Consulta, melhorias de configuração (pom, wrapper), documentação de execução.

5. Plano de Testes (resumo)
- CT001: Cadastrar paciente com dados válidos → Esperado: HTTP 201 / mensagem de sucesso.
- CT002: Agendar consulta com médico e paciente válidos → Esperado: HTTP 201 / consulta com status AGENDADA.
- Testes não funcionais: executar JMeter para carga; OWASP ZAP para varredura de segurança (recomendado).

6. Problemas corrigidos e observações
- Consulta.java: arquivo corrompido/duplicado reconstituído.
- Atualização de scripts do Maven Wrapper e instruções para build.
- Observação: pendências de lógica (repositórios com métodos faltantes, issues de Lombok, ajustes em lambdas) documentadas.

7. Como executar (resumo)
- Local com mvnw:
  chmod +x mvnw
  ./mvnw clean install
  ./mvnw spring-boot:run
- Docker (recomendado):
  docker-compose -f docker-compose.dev.yml up --build
- Testes:
  ./mvnw test
  ./mvnw test -Dspring.profiles.active=test

8. Anexos
- src/main/java/com/vidaplus/sghss/model/Consulta.java (corrigido)
- Arquivos de configuração e scripts (pom.xml, Dockerfile, docker-compose.dev.yml, mvnw)
- Instruções para gerar PDF final (abaixo)

9. Conclusão
Esta entrega apresenta a infraestrutura e correções críticas para compor o trabalho. Pendências técnicas e funcionalidades avançadas estão listadas para evolução futura.

10. Referências
- Repositório: https://github.com/Marcio606/sghss-vidaplus-prototipo
- Roteiro do projeto (projeto clinica.txt)
