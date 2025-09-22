# Script PowerShell para gerar PDF do projeto SGHSS Vida Plus
Write-Host "========================================" -ForegroundColor Green
Write-Host "GERANDO PDF DO PROJETO SGHSS VIDA PLUS" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

# Verificar se o Pandoc está instalado
Write-Host "Verificando se o Pandoc está instalado..." -ForegroundColor Yellow
try {
    $pandocVersion = pandoc --version 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Pandoc encontrado!" -ForegroundColor Green
    } else {
        throw "Pandoc não encontrado"
    }
} catch {
    Write-Host "ERRO: Pandoc não encontrado!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Para instalar o Pandoc:" -ForegroundColor Yellow
    Write-Host "1. Acesse: https://pandoc.org/installing.html" -ForegroundColor Cyan
    Write-Host "2. Baixe e instale o Pandoc" -ForegroundColor Cyan
    Write-Host "3. Execute este script novamente" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Pressione qualquer tecla para sair..."
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
    exit 1
}

Write-Host "Pandoc encontrado! Gerando PDF..." -ForegroundColor Green
Write-Host ""

# Tentar gerar PDF com wkhtmltopdf
Write-Host "Tentando gerar PDF com wkhtmltopdf..." -ForegroundColor Yellow
$command = @"
pandoc DOCUMENTO_FINAL_PDF.md -o "SGHSS_VidaPlus_Marcio_Moreira_RU_4543545.pdf" `
    --pdf-engine=wkhtmltopdf `
    --margin-top=2cm `
    --margin-bottom=2cm `
    --margin-left=2cm `
    --margin-right=2cm `
    --footer-center="SGHSS Vida Plus - Marcio Machado Moreira (RU: 4543545)" `
    --footer-font-size=10 `
    --toc `
    --toc-depth=3
"@

try {
    Invoke-Expression $command
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "========================================" -ForegroundColor Green
        Write-Host "PDF GERADO COM SUCESSO!" -ForegroundColor Green
        Write-Host "========================================" -ForegroundColor Green
        Write-Host ""
        Write-Host "Arquivo: SGHSS_VidaPlus_Marcio_Moreira_RU_4543545.pdf" -ForegroundColor Cyan
        Write-Host "Local: $PWD" -ForegroundColor Cyan
        Write-Host ""
        Write-Host "Abrindo o arquivo PDF..." -ForegroundColor Yellow
        Start-Process "SGHSS_VidaPlus_Marcio_Moreira_RU_4543545.pdf"
    } else {
        throw "Falha com wkhtmltopdf"
    }
} catch {
    Write-Host "Tentando método alternativo com pdflatex..." -ForegroundColor Yellow
    
    $commandAlt = @"
pandoc DOCUMENTO_FINAL_PDF.md -o "SGHSS_VidaPlus_Marcio_Moreira_RU_4543545.pdf" `
    --pdf-engine=pdflatex `
    --margin-top=2cm `
    --margin-bottom=2cm `
    --margin-left=2cm `
    --margin-right=2cm
"@
    
    try {
        Invoke-Expression $commandAlt
        if ($LASTEXITCODE -eq 0) {
            Write-Host "PDF gerado com método alternativo!" -ForegroundColor Green
            Start-Process "SGHSS_VidaPlus_Marcio_Moreira_RU_4543545.pdf"
        } else {
            throw "Falha com pdflatex"
        }
    } catch {
        Write-Host "ERRO: Não foi possível gerar o PDF automaticamente." -ForegroundColor Red
        Write-Host ""
        Write-Host "SOLUÇÃO MANUAL:" -ForegroundColor Yellow
        Write-Host "1. Abra o arquivo DOCUMENTO_FINAL_PDF.md" -ForegroundColor Cyan
        Write-Host "2. Copie todo o conteúdo" -ForegroundColor Cyan
        Write-Host "3. Cole em um editor (Word, Google Docs, etc.)" -ForegroundColor Cyan
        Write-Host "4. Formate conforme necessário" -ForegroundColor Cyan
        Write-Host "5. Exporte como PDF" -ForegroundColor Cyan
        Write-Host ""
        Write-Host "OU use conversão online:" -ForegroundColor Yellow
        Write-Host "https://pandoc.org/try/" -ForegroundColor Cyan
    }
}

Write-Host ""
Write-Host "Pressione qualquer tecla para sair..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
