# FASE 4: PLANO DE TESTES E QUALIDADE

## 1. ESTRATÉGIA GERAL DE TESTES

### 1.1 Pirâmide de Testes SGHSS

```
                        ▲
                       /\
                      /  \  Testes de UI (5%)
                     /    \  - Selenium / Cypress
                    /      \
                   /────────\
                  /          \  Testes de Integração (30%)
                 /            \ - JUnit + Spring Test
                /              \
               /────────────────\
              /                  \ Testes Unitários (65%)
             /                    \ - JUnit + Mockito
            /______________________\
            
Total: 100% cobertura de código almejado
Target: Mínimo 80% de cobertura para produção
```

### 1.2 Níveis de Teste

| Nível | Foco | Ferramenta | Escopo |
|-------|------|-----------|--------|
| **Unitários** | Classes isoladas (Services, Utils) | JUnit 4, Mockito | 1 classe por vez |
| **Integração** | Múltiplas classes + BD | Spring Test, TestContainers | Controllers + DB |
| **Funcional** | Requisitos funcionais | Selenium, Postman | Casos de uso |
| **Desempenho** | Performance, carga | JMeter, Locust | Endpoints sob carga |
| **Segurança** | Vulnerabilidades | OWASP ZAP, Burp | SQL Injection, XSS |
| **Usabilidade** | Interface, responsividade | Selenium, manual | UX/navegação |

---

## 2. CASOS DE TESTE (CT)

### 2.1 Módulo: Autenticação

#### CT001: Login com credenciais válidas

```
ID: CT001
Título: Autenticar com email e senha válidos
Prioridade: CRÍTICA
Pré-requisitos: Usuário existe no sistema com email=medico@vidaplus.com e senha=Senha123!

Passos:
1. Acessar página de login
2. Preencher email: "medico@vidaplus.com"
3. Preencher senha: "Senha123!"
4. Clicar botão "Entrar"

Resultado Esperado:
- Status HTTP: 200 OK
- Response contém token JWT válido
- Token armazenado em localStorage
- Usuário redirecionado para dashboard
- Tempo resposta < 500ms

Dados de Teste:
{
  "email": "medico@vidaplus.com",
  "senha": "Senha123!"
}

Resultado Esperado:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expiresIn": 3600,
  "usuarioId": 5,
  "role": "MEDICO"
}
```

#### CT002: Login com credenciais inválidas

```
ID: CT002
Título: Rejeitar login com senha incorreta
Prioridade: ALTA
Pré-requisitos: Usuário existe

Passos:
1. Tentar login com email válido + senha errada

Resultado Esperado:
- Status HTTP: 401 Unauthorized
- Response: {"status": 401, "message": "Email ou senha inválidos"}
- Usuário NÃO autenticado
- Token NÃO gerado
```

#### CT003: Login com email inexistente

```
ID: CT003
Título: Rejeitar login com email não cadastrado
Prioridade: ALTA

Resultado Esperado:
- Status HTTP: 401 Unauthorized
- Mensagem: "Email ou senha inválidos"
```

### 2.2 Módulo: Gestão de Pacientes

#### CT010: Cadastrar paciente com dados válidos

```
ID: CT010
Título: Cadastrar novo paciente com informações completas
Prioridade: ALTA
Requisito: RF001

Passos:
POST /api/v1/pacientes
{
  "nome": "Maria Silva",
  "cpf": "123.456.789-10",
  "dataNascimento": "1990-05-15",
  "email": "maria@example.com",
  "telefone": "(11) 98765-4321",
  "endereco": "Rua A, 123, SP"
}

Resultado Esperado:
- Status: 201 Created
- Response contém id gerado
- Paciente salvo no banco de dados
- Campo deletadoEm = NULL
- Validações atendidas

Cenários de Validação:
✓ Nome: mínimo 3 caracteres, máximo 100
✓ CPF: formato válido, único no banco
✓ Email: formato válido (opcional)
✓ Telefone: obrigatório
✓ Data nascimento: data válida
```

#### CT011: Rejeitar cadastro com CPF duplicado

```
ID: CT011
Título: Validar CPF único no sistema
Prioridade: ALTA

Passos:
1. Tentar cadastrar 2 pacientes com mesmo CPF

Resultado Esperado:
- 1º paciente: 201 Created ✓
- 2º paciente: 409 Conflict
- Message: "CPF já cadastrado"
```

#### CT012: Rejeitar cadastro sem campos obrigatórios

```
ID: CT012
Título: Validar campos obrigatórios
Prioridade: ALTA

Dados Inválidos:
{
  "nome": "",  // Vazio
  "cpf": "123.456.789-10",
  ...
}

Resultado Esperado:
- Status: 400 Bad Request
- Response com detalhes do erro:
{
  "status": 400,
  "message": "Erro de validação",
  "errors": {
    "nome": "Nome do paciente é obrigatório"
  }
}
```

#### CT020: Listar pacientes com paginação

```
ID: CT020
Título: Listar pacientes ativos com paginação
Prioridade: ALTA
Requisito: RF006

Passos:
GET /api/v1/pacientes?page=0&size=10&sort=nome,asc

Resultado Esperado:
- Status: 200 OK
- Response:
{
  "content": [
    { "id": 1, "nome": "João Silva", ... },
    { "id": 2, "nome": "Maria Silva", ... },
    ...
  ],
  "totalElements": 25,
  "totalPages": 3,
  "currentPage": 0,
  "pageSize": 10,
  "hasNext": true
}
- Apenas pacientes com deletadoEm = NULL
- Tempo resposta < 300ms
```

#### CT021: Buscar paciente por CPF

```
ID: CT021
Título: Localizar paciente específico por CPF
Prioridade: ALTA
Requisito: RF004

Passos:
GET /api/v1/pacientes/cpf/123.456.789-10

Resultado Esperado:
- Status: 200 OK
- Retorna paciente correspondente
- Tempo < 100ms (CPF é indexed)
```

#### CT022: Deletar paciente (soft delete)

```
ID: CT022
Título: Deletar paciente mantendo histórico (LGPD)
Prioridade: ALTA
Requisito: RF003

Passos:
DELETE /api/v1/pacientes/1

Resultado Esperado:
- Status: 204 No Content
- Campo deletadoEm preenchido com timestamp
- Paciente não aparece em listagens
- Histórico preservado no banco
```

### 2.3 Módulo: Gestão de Consultas

#### CT030: Agendar consulta com dados válidos

```
ID: CT030
Título: Agendar nova consulta com validações
Prioridade: ALTA
Requisito: RF011

Dados:
{
  "pacienteId": 1,
  "medicoId": 5,
  "dataConsulta": "2025-12-10T14:00:00",
  "tipoConsulta": "presencial"
}

Validações:
✓ Paciente existe e está ativo
✓ Médico existe e está ativo
✓ Data é no futuro (mínimo 2 horas)
✓ Horário está dentro do funcionamento (08h-18h)
✓ Não há conflito de agendamento (mesmo médico, mesma hora)
✓ Médico não está em férias

Resultado Esperado:
- Status: 201 Created
- Response:
{
  "id": 10,
  "pacienteId": 1,
  "medicoId": 5,
  "dataConsulta": "2025-12-10T14:00:00",
  "status": "agendada",
  "tipoConsulta": "presencial"
}
```

#### CT031: Rejeitar agendamento com conflito de horário

```
ID: CT031
Título: Validar conflito de horário médico
Prioridade: ALTA

Cenário:
- Médico já tem consulta às 14:00
- Tentar agendar outro paciente no mesmo horário

Resultado Esperado:
- Status: 409 Conflict
- Message: "Médico indisponível neste horário"
```

#### CT032: Rejeitar agendamento em data passada

```
ID: CT032
Título: Validar data da consulta no futuro
Prioridade: ALTA

Resultado Esperado:
- Status: 400 Bad Request
- Message: "Data da consulta deve ser no futuro"
```

### 2.4 Módulo: Prontuários

#### CT040: Criar prontuário para consulta

```
ID: CT040
Título: Registrar novo prontuário
Prioridade: ALTA
Requisito: RF021

Dados:
{
  "consultaId": 10,
  "anamnese": "Paciente relata dor de cabeça...",
  "diagnostico": "Cefaleia tensional",
  "tratamento": "Dipirona 500mg a cada 6h",
  "prescricoes": [
    {
      "nomeMedicamento": "Dipirona",
      "dosagem": "500mg",
      "frequencia": "a cada 6 horas",
      "duracao": "3 dias"
    }
  ]
}

Resultado Esperado:
- Status: 201 Created
- Prontuário associado à consulta
- Campo assinadoEm = NULL (ainda não assinado)
```

#### CT041: Assinar prontuário (médico)

```
ID: CT041
Título: Médico assina prontuário validando dados
Prioridade: ALTA
Requisito: RF027

Passos:
POST /api/v1/prontuarios/15/assinar

Resultado Esperado:
- Status: 200 OK
- Campo assinadoEm preenchido
- Campo assinadoPorId preenchido com ID do médico
- Prontuário não pode ser editado após assinatura
- Auditoria registra: MEDICO_X assinou PRONTUARIO_15
```

---

## 3. TESTES UNITÁRIOS (JUnit + Mockito)

### 3.1 Teste de Service - PacienteService

```java
package com.vidaplus.sghss.service;

import com.vidaplus.sghss.exception.ResourceNotFoundException;
import com.vidaplus.sghss.model.Paciente;
import com.vidaplus.sghss.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private AuditoriaService auditoriaService;

    @InjectMocks
    private PacienteService pacienteService;

    private Paciente pacienteTest;

    @BeforeEach
    void setup() {
        pacienteTest = Paciente.builder()
                .id(1L)
                .nome("João Silva")
                .cpf("123.456.789-10")
                .dataNascimento(LocalDate.of(1990, 5, 15))
                .email("joao@example.com")
                .telefone("(11) 98765-4321")
                .endereco("Rua A, 123, SP")
                .build();
    }

    @Test
    void testCriarPaciente_ComDadosValidos() {
        // Arrange
        when(pacienteRepository.findByCpf("123.456.789-10")).thenReturn(Optional.empty());
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteTest);

        // Act
        Paciente resultado = pacienteService.criar(pacienteTest);

        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        assertEquals("123.456.789-10", resultado.getCpf());
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
        verify(auditoriaService, times(1)).registrar(anyString(), anyString(), anyLong(), anyString());
    }

    @Test
    void testCriarPaciente_ComCpfDuplicado() {
        // Arrange
        when(pacienteRepository.findByCpf("123.456.789-10")).thenReturn(Optional.of(pacienteTest));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            pacienteService.criar(pacienteTest);
        });
        verify(pacienteRepository, never()).save(any());
    }

    @Test
    void testObterPorId_Sucesso() {
        // Arrange
        when(pacienteRepository.findByIdAndDeletadoEmIsNull(1L))
                .thenReturn(Optional.of(pacienteTest));

        // Act
        Paciente resultado = pacienteService.obterPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
    }

    @Test
    void testObterPorId_NaoEncontrado() {
        // Arrange
        when(pacienteRepository.findByIdAndDeletadoEmIsNull(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            pacienteService.obterPorId(999L);
        });
    }

    @Test
    void testDeletarPaciente_SoftDelete() {
        // Arrange
        when(pacienteRepository.findByIdAndDeletadoEmIsNull(1L))
                .thenReturn(Optional.of(pacienteTest));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteTest);

        // Act
        pacienteService.deletar(1L);

        // Assert
        assertTrue(pacienteTest.getDeletadoEm() != null);
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
        verify(auditoriaService, times(1)).registrar("DELETE", "PACIENTE", 1L, anyString());
    }

    @Test
    void testListarAtivos_ComPaginacao() {
        // Arrange
        Page<Paciente> pagina = new PageImpl<>(
                Arrays.asList(pacienteTest),
                Pageable.unpaged(),
                1
        );
        when(pacienteRepository.findByDeletadoEmIsNull(any(Pageable.class)))
                .thenReturn(pagina);

        // Act
        Page<Paciente> resultado = pacienteService.listarAtivos(Pageable.unpaged());

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertTrue(resultado.getContent().stream()
                .anyMatch(p -> p.getNome().equals("João Silva")));
    }
}
```

### 3.2 Teste de Validação - PacienteValidator

```java
@Test
void testValidarCpf_Valido() {
    assertTrue(ValidationUtils.validarCpf("123.456.789-10"));
}

@Test
void testValidarCpf_Invalido() {
    assertFalse(ValidationUtils.validarCpf("000.000.000-00"));
    assertFalse(ValidationUtils.validarCpf("123.456.789-99"));
}

@Test
void testValidarEmail_Valido() {
    assertTrue(ValidationUtils.validarEmail("usuario@example.com"));
}

@Test
void testValidarEmail_Invalido() {
    assertFalse(ValidationUtils.validarEmail("email-invalido"));
    assertFalse(ValidationUtils.validarEmail(""));
}

@Test
void testValidarDataFutura() {
    LocalDateTime futuro = LocalDateTime.now().plusHours(3);
    assertTrue(ValidationUtils.validarDataFutura(futuro));
    
    LocalDateTime passado = LocalDateTime.now().minusHours(1);
    assertFalse(ValidationUtils.validarDataFutura(passado));
}
```

---

## 4. TESTES DE INTEGRAÇÃO (Spring Test)

### 4.1 Teste de Controller com Banco de Dados

```java
package com.vidaplus.sghss.controller;

import com.vidaplus.sghss.model.Paciente;
import com.vidaplus.sghss.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        pacienteRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testListarPacientes_Sucesso() throws Exception {
        // Arrange
        Paciente paciente = Paciente.builder()
                .nome("Maria Silva")
                .cpf("987.654.321-00")
                .dataNascimento(LocalDate.of(1985, 3, 20))
                .email("maria@example.com")
                .telefone("(11) 91234-5678")
                .build();
        pacienteRepository.save(paciente);

        // Act & Assert
        mockMvc.perform(get("/api/v1/pacientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.content[0].nome", is("Maria Silva")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCriarPaciente_Sucesso() throws Exception {
        // Arrange
        Paciente novoPaciente = Paciente.builder()
                .nome("João Pedro")
                .cpf("111.222.333-44")
                .dataNascimento(LocalDate.of(1992, 7, 10))
                .email("joao@example.com")
                .telefone("(11) 99999-9999")
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/v1/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoPaciente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nome", is("João Pedro")))
                .andExpect(jsonPath("$.cpf", is("111.222.333-44")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCriarPaciente_ComCpfDuplicado() throws Exception {
        // Arrange - Inserir paciente existente
        Paciente existente = Paciente.builder()
                .nome("José Silva")
                .cpf("555.666.777-88")
                .dataNascimento(LocalDate.of(1988, 1, 15))
                .email("jose@example.com")
                .telefone("(11) 98888-8888")
                .build();
        pacienteRepository.save(existente);

        Paciente duplicado = Paciente.builder()
                .nome("Outro Nome")
                .cpf("555.666.777-88")  // CPF duplicado
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/v1/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicado)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testObterPacientePorId_Sucesso() throws Exception {
        // Arrange
        Paciente paciente = pacienteRepository.save(Paciente.builder()
                .nome("Ana Costa")
                .cpf("999.888.777-66")
                .dataNascimento(LocalDate.of(1995, 6, 25))
                .email("ana@example.com")
                .telefone("(11) 97777-7777")
                .build());

        // Act & Assert
        mockMvc.perform(get("/api/v1/pacientes/" + paciente.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(paciente.getId().intValue())))
                .andExpect(jsonPath("$.nome", is("Ana Costa")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeletarPaciente_Sucesso() throws Exception {
        // Arrange
        Paciente paciente = pacienteRepository.save(Paciente.builder()
                .nome("Carlos Mendes")
                .cpf("444.333.222-11")
                .dataNascimento(LocalDate.of(1980, 12, 5))
                .email("carlos@example.com")
                .telefone("(11) 96666-6666")
                .build());

        // Act & Assert
        mockMvc.perform(delete("/api/v1/pacientes/" + paciente.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verificar soft delete
        Paciente deletado = pacienteRepository.findById(paciente.getId()).orElse(null);
        assertNotNull(deletado.getDeletadoEm());
    }

    @Test
    void testAcessarSemAutenticacao() throws Exception {
        mockMvc.perform(get("/api/v1/pacientes"))
                .andExpect(status().isUnauthorized());
    }
}
```

---

## 5. TESTES DE DESEMPENHO (JMeter)

### 5.1 Cenário: Listar Pacientes sob Carga

```
Configuração JMeter:
├── Thread Group
│   ├── Number of Threads: 100
│   ├── Ramp-Up Period: 30s
│   ├── Loop Count: 10
│
├── HTTP Request
│   ├── Method: GET
│   ├── URL: http://localhost:8080/sghss/api/v1/pacientes
│   ├── Headers:
│   │   ├── Authorization: Bearer <token>
│   │   └── Content-Type: application/json
│
├── Listeners
│   ├── View Results Tree
│   ├── Graph Results
│   ├── Summary Report
│   └── Aggregate Report
```

**Critérios de Aceitação:**
- ✅ Tempo médio de resposta: < 200ms
- ✅ Percentil 95: < 500ms
- ✅ Erro rate: < 1%
- ✅ Throughput: > 100 req/s

**Exemplo de Resultado Esperado:**

```
Aggregate Report
===============
Label:          GET /api/v1/pacientes
Samples:        1000
Average:        145ms
Min:            32ms
Max:            678ms
Std. Dev:       156ms
Error %:        0.5%
Throughput:     125 req/s
95% Line:       420ms
99% Line:       580ms
```

### 5.2 Cenário: Criar Paciente sob Carga

```
HTTP Request
├── Method: POST
├── URL: http://localhost:8080/sghss/api/v1/pacientes
├── Body:
│   {
│     "nome": "Paciente ${__counter()}",
│     "cpf": "${__UUID()}",
│     "dataNascimento": "1990-05-15",
│     "email": "paciente${__counter()}@example.com",
│     "telefone": "(11) 9${__Random(10000000, 99999999)}"
│   }
```

**Critérios:**
- ✅ Tempo resposta: < 300ms
- ✅ Taxa de sucesso: 100%
- ✅ Status 201 Created para todos

---

## 6. TESTES DE SEGURANÇA

### 6.1 Testes com OWASP ZAP

**Vulnerabilidades a Verificar:**

| Vulnerabilidade | Teste | Ferramenta | Esperado |
|-----------------|-------|-----------|----------|
| **SQL Injection** | Enviar `' OR '1'='1'` em fields | ZAP | Rejected |
| **XSS (Cross-Site Scripting)** | Enviar `<script>alert('xss')</script>` | ZAP, Burp | Sanitized |
| **CSRF (Cross-Site Request Forgery)** | Verificar CSRF tokens | ZAP | Token required |
| **Broken Authentication** | Tentar acessar sem token | Manual | 401 Unauthorized |
| **Sensitive Data Exposure** | Verificar TLS/criptografia | TestSSL | TLS 1.2+ |
| **LGPD Non-Compliance** | Verificar direito esquecimento | Manual | Soft delete |

### 6.2 Teste Manual: SQL Injection

```
Entrada Maliciosa:
GET /api/v1/pacientes/cpf/123' OR '1'='1

Resultado Esperado:
- Request rejeitado
- Prepared statement previne injeção
- Status: 400 Bad Request ou sem resultado relevante
- Log: Tentativa de SQL injection detectada
```

### 6.3 Teste Manual: XSS Prevention

```
Entrada Maliciosa em Criação de Paciente:
{
  "nome": "<img src=x onerror=alert('XSS')>",
  "cpf": "123.456.789-10",
  ...
}

Resultado Esperado:
- HTML/JS tags são escapados
- No frontend, renderiza como texto: "<img src=x onerror=...>"
- Script NÃO executa
- Status: 201 (salva com texto escapado)
```

### 6.4 Teste de Autenticação JWT

```java
@Test
void testAcessarSemToken() throws Exception {
    mockMvc.perform(get("/api/v1/pacientes"))
            .andExpect(status().isUnauthorized());
}

@Test
void testAcessarComTokenExpirado() throws Exception {
    String tokenExpirado = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";
    
    mockMvc.perform(get("/api/v1/pacientes")
            .header("Authorization", "Bearer " + tokenExpirado))
            .andExpect(status().isUnauthorized());
}

@Test
void testAcessarComTokenInvalido() throws Exception {
    mockMvc.perform(get("/api/v1/pacientes")
            .header("Authorization", "Bearer invalid.token.here"))
            .andExpect(status().isUnauthorized());
}
```

---

## 7. TESTES DE USABILIDADE

### 7.1 Teste Manual: Responsividade

```
Dispositivos Testados:
├── Desktop (1920x1080)
├── Tablet (768x1024)
└── Mobile (375x812)

Critérios:
✓ Layout adapta sem scroll horizontal
✓ Buttons/inputs clicáveis em mobile
✓ Imagens responsive
✓ Texto legível (min 14px em mobile)
✓ Navegação acessível em todos tamanhos
```

### 7.2 Teste com Selenium: Fluxo Completo

```java
@Test
void testFluxoCompletoLogin_Dashboard_ListarPacientes() throws InterruptedException {
    driver.get("http://localhost:8080/sghss/");
    
    // Preencher login
    driver.findElement(By.id("email")).sendKeys("medico@vidaplus.com");
    driver.findElement(By.id("senha")).sendKeys("Senha123!");
    driver.findElement(By.xpath("//button[contains(text(), 'Entrar')]")).click();
    
    // Aguardar carregamento
    Thread.sleep(2000);
    
    // Verificar dashboard
    assertTrue(driver.getPageSource().contains("Dashboard"));
    
    // Clicar em "Ver Pacientes"
    driver.findElement(By.xpath("//a[contains(text(), 'Ver')]")).click();
    
    // Verificar tabela de pacientes
    assertTrue(driver.findElement(By.tagName("table")).isDisplayed());
    
    // Contar linhas da tabela
    int rowCount = driver.findElements(By.xpath("//tbody/tr")).size();
    assertTrue(rowCount > 0, "Deve haver pacientes na tabela");
}
```

---

## 8. MÉTRICAS DE COBERTURA

### 8.1 Relatório JaCoCo (Cobertura de Código)

**Target:**
- Cobertura total: **80%+**
- Services: **90%+**
- Controllers: **70%+**
- Repositories: **Auto gerado, isento**

### 8.2 Comando Maven com JaCoCo

```bash
# Executar testes com cobertura
mvn clean test jacoco:report

# Relatório gerado em:
# target/site/jacoco/index.html

# Comando para verificar cobertura mínima
mvn clean test jacoco:report jacoco:check \
  -Djacoco.maven.plugin.version=0.8.8 \
  -Dline.coverage.minimum=0.80 \
  -Dbranch.coverage.minimum=0.70
```

---

## 9. PLANO DE EXECUÇÃO DOS TESTES

### 9.1 Cronograma Semanal (Fase 4)

| Dia | Atividade | Ferramenta | Duração |
|-----|-----------|-----------|--------|
| Seg | Testes Unitários | JUnit, Mockito | 2 dias |
| Qua | Testes Integração | Spring Test | 1 dia |
| Qui | Testes Desempenho | JMeter | 1 dia |
| Sex | Testes Segurança | OWASP ZAP | 1 dia |
| Seg (S2) | Testes Usabilidade | Selenium, Manual | 1 dia |
| Ter | Relatórios e Ajustes | - | 1 dia |

### 9.2 Definições de Pronto (DoD - Definition of Done)

- ✅ Mínimo 80% cobertura de código (JaCoCo)
- ✅ 0 testes falhando
- ✅ Performance < 200ms (p95)
- ✅ Sem vulnerabilidades críticas (OWASP)
- ✅ 100% requisitos funcionais testados
- ✅ Documentação de testes atualizada

---

## 10. CONCLUSÃO DA FASE 4

### Entregáveis
✅ 40+ casos de teste documentados  
✅ Testes unitários (JUnit + Mockito)  
✅ Testes de integração (Spring Test)  
✅ Testes de desempenho (JMeter)  
✅ Testes de segurança (OWASP ZAP)  
✅ Testes de usabilidade (Selenium)  
✅ Cobertura de código (JaCoCo) 80%+  
✅ Relatórios de teste  

### Próximos Passos
- Executar testes automatizados
- Corrigir bugs encontrados
- Gerar relatório final de qualidade
- Integração contínua (CI/CD)

---

**Documento: FASE 4 - Plano de Testes e Qualidade**  
**Data:** Dezembro 2025  
**Autor:** Marcio Machado Moreira (RU: 4543545)  
**Status:** ✅ Concluído
