# FASE 4: TESTES (Unitários, Integração, Performance, Segurança)

**Aluno:** Marcio Machado Moreira  
**R.U:** 4543545  

---

## 1. Estratégia de Testes
- Unitários: JUnit + Mockito
- Integração: Spring Boot Test (TestRestTemplate)
- Performance: JMeter (cenários de carga)
- Segurança: OWASP Top 10 checklist, testes de pen test básicos
- Cobertura: JaCoCo

## 2. Como executar
- Unit tests:
```powershell
mvnw.cmd test
```
- Gerar relatório JaCoCo:
```powershell
mvnw.cmd jacoco:report
```
- Executar collection Postman (exportada em `docs/postman/`)

## 3. Casos de Teste Principais (resumo)
- CT-001: Criar paciente válido (unit + integration)
- CT-002: Criar paciente com CPF duplicado (erro)
- CT-007: Agendar consulta (disponibilidade horário)
- CT-016: Acesso sem token -> 401
- CT-025: Soft delete e direito ao esquecimento

## 4. Testes de Performance (resumo de resultados)
- GET /api/pacientes — 1000 req, média 163ms
- POST /api/pacientes — 500 req, média 248ms

## 5. Checklist OWASP (resumo)
- Input validation: ✅
- Proteção CSRF: Spring Security (APIs stateless) ✅
- XSS sanitization: ✅
- Sensitive data encryption: AES-256 para backups ✅

## 6. Anexos de Testes
- `docs/testes/casos_teste_sghss.md` — descrição completa dos 40+ casos
- `docs/testes/relatorio_jmeter.md` — resultados brutos
- `docs/testes/relatorio_jacoco.html` — cobertura

---

**Fim da FASE 4**
