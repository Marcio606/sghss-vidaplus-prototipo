# SGHSS-Vida Plus Prototipo

## Diagrama de Casos de Uso (Descrição)
- Cadastro de Paciente
- Agendamento de Consulta
- Gerenciamento de Profissionais
- Prontuário Eletrônico
- Telemedicina
- Administração Hospitalar
- Controle de Acesso

## Diagrama de Classes (Descrição)
- Paciente: id, nome, cpf, dataNascimento, historicoClinico
- ProfissionalSaude: id, nome, crm, especialidade, agenda
- Consulta: id, paciente, profissional, dataHora, tipo, status
- Prontuario: id, paciente, registros, prescricoes
- Usuario: id, login, senha, perfil
- Leito: id, status, paciente
- Relatorio: id, tipo, dados, dataGeracao

## Arquitetura Back-end
- Monolítica em camadas (Controller, Service, Repository)
- API RESTful
- Banco de dados: MySQL
- Framework: Spring Boot

## Exemplo de Endpoints REST
- POST /api/pacientes
- GET /api/pacientes/{id}
- PUT /api/pacientes/{id}
- DELETE /api/pacientes/{id}
- POST /api/consultas
- GET /api/consultas/{id}
- POST /api/profissionais
- POST /api/login

---
Os diagramas visuais (UML) podem ser gerados em ferramentas como draw.io, Lucidchart ou PlantUML e anexados posteriormente ao documento final.