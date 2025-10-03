# Plano de Testes - SGHSS-Vida Plus Prototipo

## Estratégia de Testes
Os testes do sistema SGHSS-Vida Plus foram realizados utilizando o Postman para validar os principais endpoints da API REST. O objetivo foi garantir que as funcionalidades essenciais (cadastro de pacientes, agendamento de consultas, etc.) estejam funcionando conforme os requisitos.

## Ferramenta Utilizada
- **Postman**: utilizada para enviar requisições HTTP (GET, POST, PUT, DELETE) e validar as respostas da API.

## Exemplos de Casos de Teste
| Caso | Descrição | Entrada | Resultado Esperado |
|------|-----------|---------|--------------------|
| CT01 | Cadastrar paciente | POST /api/pacientes com dados válidos | 201 Created + dados do paciente |
| CT02 | Buscar paciente | GET /api/pacientes/{id} | 200 OK + dados do paciente |
| CT03 | Agendar consulta | POST /api/consultas com dados válidos | 201 Created + dados da consulta |
| CT04 | Atualizar paciente | PUT /api/pacientes/{id} com novos dados | 200 OK + dados atualizados |
| CT05 | Deletar paciente | DELETE /api/pacientes/{id} | 204 No Content |

## Exemplo de Requisição no Postman
- **POST** `http://localhost:8080/api/pacientes`
- **Body (JSON):**
```
{
  "nome": "João da Silva",
  "cpf": "12345678900",
  "dataNascimento": "1990-01-01",
  "historicoClinico": "Sem alergias."
}
```
- **Resposta Esperada:**
```
{
  "id": 1,
  "nome": "João da Silva",
  "cpf": "12345678900",
  "dataNascimento": "1990-01-01",
  "historicoClinico": "Sem alergias."
}
```

## Observações
- Todos os testes foram realizados manualmente, simulando o uso real do sistema por usuários humanos.
- Os resultados foram analisados para garantir que o sistema responde corretamente a entradas válidas e inválidas.
- Sugere-se anexar prints das telas do Postman no documento final para comprovação dos testes.

---
Marcio Machado Moreira - R.U: 4543545