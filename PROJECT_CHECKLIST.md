# Checklist e Roadmap - SGHSS VidaPlus

## Pré-requisitos
- [ ] Clonar repositório
- [ ] Ter JDK (versão X) e Docker instalados

## Prioridade Alta
- [ ] Build: corrigir pom.xml / maven wrapper e obter build verde
- [ ] Documentar Requisitos (docs/requirements.md)
- [ ] Criar PROJECT_REPORT.md (estrutura do relatório)
- [ ] Gerar branch `report` e trabalhar a versão PDF

## Prioridade Média
- [ ] Adicionar diagrama de classes (docs/diagrams/)
- [ ] Documentar API endpoints (docs/API.md ou openapi.yaml)
- [ ] Plano de testes (docs/plan_tests.md)

## Prioridade Baixa
- [ ] Otimizar Dockerfile / docker-compose
- [ ] Pipeline CI para build/test
- [ ] Verificação de LGPD e registro de auditoria

## Como gerar PDF final
- Convertendo o PROJECT_REPORT.md para PDF (ex. pandoc):
  - pandoc PROJECT_REPORT.md -o Projeto_SGHSS_VidaPlus.pdf --pdf-engine=xelatex
