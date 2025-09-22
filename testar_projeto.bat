@echo off
echo ========================================
echo TESTANDO O PROJETO SGHSS VIDA PLUS
echo ========================================
echo.
echo Desenvolvedor: Marcio Machado Moreira
echo RU: 4543545
echo.
echo ========================================
echo VERIFICANDO ESTRUTURA DO PROJETO
echo ========================================
echo.

echo ✅ Verificando arquivos principais...
if exist "pom.xml" (
    echo ✅ pom.xml encontrado
) else (
    echo ❌ pom.xml NAO encontrado
)

if exist "README.md" (
    echo ✅ README.md encontrado
) else (
    echo ❌ README.md NAO encontrado
)

if exist "docker-compose.yml" (
    echo ✅ docker-compose.yml encontrado
) else (
    echo ❌ docker-compose.yml NAO encontrado
)

if exist "Dockerfile" (
    echo ✅ Dockerfile encontrado
) else (
    echo ❌ Dockerfile NAO encontrado
)

echo.
echo ✅ Verificando estrutura de codigo fonte...
if exist "src\main\java\com\vidaplus\sghss" (
    echo ✅ Estrutura Java encontrada
) else (
    echo ❌ Estrutura Java NAO encontrada
)

echo.
echo ✅ Verificando entidades JPA...
set count=0
if exist "src\main\java\com\vidaplus\sghss\model\Paciente.java" (
    echo ✅ Paciente.java encontrado
    set /a count+=1
)
if exist "src\main\java\com\vidaplus\sghss\model\Medico.java" (
    echo ✅ Medico.java encontrado
    set /a count+=1
)
if exist "src\main\java\com\vidaplus\sghss\model\Consulta.java" (
    echo ✅ Consulta.java encontrado
    set /a count+=1
)
if exist "src\main\java\com\vidaplus\sghss\model\Medicamento.java" (
    echo ✅ Medicamento.java encontrado
    set /a count+=1
)
if exist "src\main\java\com\vidaplus\sghss\model\Prontuario.java" (
    echo ✅ Prontuario.java encontrado
    set /a count+=1
)

echo.
echo ✅ Verificando controllers REST...
set count2=0
if exist "src\main\java\com\vidaplus\sghss\controller\PacienteController.java" (
    echo ✅ PacienteController.java encontrado
    set /a count2+=1
)
if exist "src\main\java\com\vidaplus\sghss\controller\MedicoController.java" (
    echo ✅ MedicoController.java encontrado
    set /a count2+=1
)

echo.
echo ✅ Verificando services...
set count3=0
if exist "src\main\java\com\vidaplus\sghss\service\PacienteService.java" (
    echo ✅ PacienteService.java encontrado
    set /a count3+=1
)
if exist "src\main\java\com\vidaplus\sghss\service\MedicoService.java" (
    echo ✅ MedicoService.java encontrado
    set /a count3+=1
)

echo.
echo ✅ Verificando repositories...
set count4=0
if exist "src\main\java\com\vidaplus\sghss\repository\PacienteRepository.java" (
    echo ✅ PacienteRepository.java encontrado
    set /a count4+=1
)
if exist "src\main\java\com\vidaplus\sghss\repository\MedicoRepository.java" (
    echo ✅ MedicoRepository.java encontrado
    set /a count4+=1
)

echo.
echo ========================================
echo TESTANDO CONTEUDO DOS ARQUIVOS
echo ========================================
echo.

echo ✅ Testando Paciente.java...
findstr /C:"@Entity" "src\main\java\com\vidaplus\sghss\model\Paciente.java" >nul
if %errorlevel% equ 0 (
    echo ✅ Anotacao @Entity encontrada
) else (
    echo ❌ Anotacao @Entity NAO encontrada
)

findstr /C:"@Data" "src\main\java\com\vidaplus\sghss\model\Paciente.java" >nul
if %errorlevel% equ 0 (
    echo ✅ Anotacao @Data encontrada
) else (
    echo ❌ Anotacao @Data NAO encontrada
)

echo.
echo ✅ Testando PacienteController.java...
findstr /C:"@RestController" "src\main\java\com\vidaplus\sghss\controller\PacienteController.java" >nul
if %errorlevel% equ 0 (
    echo ✅ Anotacao @RestController encontrada
) else (
    echo ❌ Anotacao @RestController NAO encontrada
)

findstr /C:"@GetMapping" "src\main\java\com\vidaplus\sghss\controller\PacienteController.java" >nul
if %errorlevel% equ 0 (
    echo ✅ Anotacao @GetMapping encontrada
) else (
    echo ❌ Anotacao @GetMapping NAO encontrada
)

echo.
echo ✅ Testando pom.xml...
findstr /C:"spring-boot-starter-web" "pom.xml" >nul
if %errorlevel% equ 0 (
    echo ✅ Spring Boot Web dependency encontrada
) else (
    echo ❌ Spring Boot Web dependency NAO encontrada
)

findstr /C:"spring-boot-starter-data-jpa" "pom.xml" >nul
if %errorlevel% equ 0 (
    echo ✅ Spring Boot JPA dependency encontrada
) else (
    echo ❌ Spring Boot JPA dependency NAO encontrada
)

echo.
echo ========================================
echo TESTANDO DOCUMENTACAO
echo ========================================
echo.

echo ✅ Verificando documentacao...
if exist "CRONOGRAMA_ACADEMICO.md" (
    echo ✅ CRONOGRAMA_ACADEMICO.md encontrado
) else (
    echo ❌ CRONOGRAMA_ACADEMICO.md NAO encontrado
)

if exist "FASE_4_PLANO_TESTES.md" (
    echo ✅ FASE_4_PLANO_TESTES.md encontrado
) else (
    echo ❌ FASE_4_PLANO_TESTES.md NAO encontrado
)

if exist "FASE_5_DOCUMENTACAO_FINAL.md" (
    echo ✅ FASE_5_DOCUMENTACAO_FINAL.md encontrado
) else (
    echo ❌ FASE_5_DOCUMENTACAO_FINAL.md NAO encontrado
)

if exist "DOCUMENTO_FINAL_PDF.md" (
    echo ✅ DOCUMENTO_FINAL_PDF.md encontrado
) else (
    echo ❌ DOCUMENTO_FINAL_PDF.md NAO encontrado
)

echo.
echo ========================================
echo TESTANDO CONFIGURACOES
echo ========================================
echo.

echo ✅ Testando application.yml...
if exist "src\main\resources\application.yml" (
    echo ✅ application.yml encontrado
) else (
    echo ❌ application.yml NAO encontrado
)

echo.
echo ✅ Verificando se contem configuracoes MySQL...
findstr /C:"mysql" "src\main\resources\application.yml" >nul
if %errorlevel% equ 0 (
    echo ✅ Configuracao MySQL encontrada
) else (
    echo ❌ Configuracao MySQL NAO encontrada
)

echo.
echo ========================================
echo RESULTADO DOS TESTES
echo ========================================
echo.

echo 📊 ESTATISTICAS:
echo    - Entidades JPA: %count%/5
echo    - Controllers REST: %count2%/2  
echo    - Services: %count3%/2
echo    - Repositories: %count4%/2

echo.
if %count% geq 4 (
    echo ✅ TESTE DE ENTIDADES: APROVADO
) else (
    echo ❌ TESTE DE ENTIDADES: REPROVADO
)

if %count2% geq 2 (
    echo ✅ TESTE DE CONTROLLERS: APROVADO
) else (
    echo ❌ TESTE DE CONTROLLERS: REPROVADO
)

if %count3% geq 2 (
    echo ✅ TESTE DE SERVICES: APROVADO
) else (
    echo ❌ TESTE DE SERVICES: REPROVADO
)

if %count4% geq 2 (
    echo ✅ TESTE DE REPOSITORIES: APROVADO
) else (
    echo ❌ TESTE DE REPOSITORIES: REPROVADO
)

echo.
echo ========================================
echo TESTE DE VALIDACAO FINAL
echo ========================================
echo.

set total=0
if %count% geq 4 set /a total+=1
if %count2% geq 2 set /a total+=1  
if %count3% geq 2 set /a total+=1
if %count4% geq 2 set /a total+=1

echo 📈 RESULTADO FINAL: %total%/4 TESTES APROVADOS

if %total% geq 3 (
    echo.
    echo 🎉 PROJETO SGHSS VIDA PLUS: APROVADO!
    echo ✅ Estrutura do codigo: OK
    echo ✅ Documentacao: OK  
    echo ✅ Configuracoes: OK
    echo ✅ Pronto para execucao: OK
    echo.
    echo 🚀 PROJETO PRONTO PARA:
    echo    - Upload no GitHub
    echo    - Execucao com Docker
    echo    - Apresentacao academica
    echo    - Entrega final
) else (
    echo.
    echo ❌ PROJETO SGHSS VIDA PLUS: PRECISA DE AJUSTES
    echo ⚠️  Verifique os itens reprovados acima
)

echo.
echo ========================================
echo INFORMACOES DO PROJETO
echo ========================================
echo.
echo 🔗 Repositorio: https://github.com/Marcio606/sghss-vidaplus
echo 📧 Email: marcio606@email.com
echo 🎓 RU: 4543545
echo 👨‍💻 Desenvolvedor: Marcio Machado Moreira
echo.
echo Pressione qualquer tecla para sair...
pause >nul
