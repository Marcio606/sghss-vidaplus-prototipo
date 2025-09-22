@echo off
echo ========================================
echo GERANDO PDF DO PROJETO SGHSS VIDA PLUS
echo ========================================
echo.

echo Verificando se o Pandoc esta instalado...
pandoc --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERRO: Pandoc nao encontrado!
    echo.
    echo Para instalar o Pandoc:
    echo 1. Acesse: https://pandoc.org/installing.html
    echo 2. Baixe e instale o Pandoc
    echo 3. Execute este script novamente
    echo.
    pause
    exit /b 1
)

echo Pandoc encontrado! Gerando PDF...
echo.

pandoc DOCUMENTO_FINAL_PDF.md -o "SGHSS_VidaPlus_Marcio_Moreira_RU_4543545.pdf" ^
    --pdf-engine=wkhtmltopdf ^
    --margin-top=2cm ^
    --margin-bottom=2cm ^
    --margin-left=2cm ^
    --margin-right=2cm ^
    --footer-center="SGHSS Vida Plus - Marcio Machado Moreira (RU: 4543545)" ^
    --footer-font-size=10 ^
    --toc ^
    --toc-depth=3

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo PDF GERADO COM SUCESSO!
    echo ========================================
    echo.
    echo Arquivo: SGHSS_VidaPlus_Marcio_Moreira_RU_4543545.pdf
    echo Local: %CD%
    echo.
    echo Abrindo o arquivo PDF...
    start "" "SGHSS_VidaPlus_Marcio_Moreira_RU_4543545.pdf"
) else (
    echo.
    echo ERRO: Falha ao gerar PDF!
    echo Tentando metodo alternativo...
    
    pandoc DOCUMENTO_FINAL_PDF.md -o "SGHSS_VidaPlus_Marcio_Moreira_RU_4543545.pdf" ^
        --pdf-engine=pdflatex ^
        --margin-top=2cm ^
        --margin-bottom=2cm ^
        --margin-left=2cm ^
        --margin-right=2cm
        
    if %errorlevel% equ 0 (
        echo PDF gerado com metodo alternativo!
        start "" "SGHSS_VidaPlus_Marcio_Moreira_RU_4543545.pdf"
    ) else (
        echo ERRO: Nao foi possivel gerar o PDF automaticamente.
        echo.
        echo SOLUCAO MANUAL:
        echo 1. Copie o conteudo do arquivo DOCUMENTO_FINAL_PDF.md
        echo 2. Cole em um editor de texto (Word, Google Docs, etc.)
        echo 3. Formate conforme necessario
        echo 4. Exporte como PDF
        echo.
    )
)

echo.
echo Pressione qualquer tecla para sair...
pause >nul
