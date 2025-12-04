@echo off
REM Script para converter HTML para PDF usando Microsoft Print to PDF

setlocal enabledelayedexpansion

cd /d "C:\Users\Marcio Moreira\Documents\sghss-vidaplus\docs"

echo ============================================================
echo Convertendo HTML para PDF...
echo ============================================================

REM Verificar se o arquivo HTML existe
if not exist "00_DOCUMENTO_FINAL_SGHSS.html" (
    echo ERRO: Arquivo HTML nao encontrado!
    exit /b 1
)

REM Usar PowerShell para converter via impressora
powershell -Command ^
"$html = Get-Content '00_DOCUMENTO_FINAL_SGHSS.html' -Raw; ^
$ie = New-Object -ComObject 'InternetExplorer.Application'; ^
$ie.Navigate('about:blank'); ^
while ($ie.Busy) { Start-Sleep -Milliseconds 100 }; ^
$doc = $ie.Document; ^
$doc.write([System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String([System.Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes($html))))); ^
$doc.Close(); ^
$ie.ExecWB(6, 2); ^
Start-Sleep -Seconds 5; ^
$ie.Quit()" 2>nul

echo.
echo Alternativa: Use um navegador para converter manualmente
echo 1. Abra 00_DOCUMENTO_FINAL_SGHSS.html no navegador
echo 2. Pressione Ctrl+P
echo 3. Selecione "Salvar como PDF"
echo 4. Salve como "00_DOCUMENTO_FINAL_SGHSS.pdf"
echo.

pause
