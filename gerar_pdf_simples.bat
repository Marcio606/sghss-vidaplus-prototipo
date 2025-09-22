@echo off
echo ========================================
echo GERANDO PDF DO PROJETO SGHSS VIDA PLUS
echo ========================================
echo.

echo Abrindo o arquivo DOCUMENTO_FINAL_PDF.md...
echo.
echo INSTRUCOES PARA GERAR O PDF:
echo.
echo OPCAO 1 - MICROSOFT WORD:
echo 1. O arquivo sera aberto automaticamente
echo 2. Copie todo o conteudo (Ctrl+A, Ctrl+C)
echo 3. Abra o Microsoft Word
echo 4. Cole o conteudo (Ctrl+V)
echo 5. Formate conforme necessario
echo 6. Salve como PDF (Arquivo ^> Salvar Como ^> PDF)
echo.
echo OPCAO 2 - GOOGLE DOCS:
echo 1. Acesse: https://docs.google.com
echo 2. Crie um novo documento
echo 3. Cole o conteudo copiado
echo 4. Formate o documento
echo 5. Baixe como PDF
echo.
echo OPCAO 3 - CONVERSAO ONLINE:
echo 1. Acesse: https://pandoc.org/try/
echo 2. Cole o conteudo do arquivo
echo 3. Selecione: Markdown -^> PDF
echo 4. Baixe o arquivo gerado
echo.
echo OPCAO 4 - LIBREOFFICE:
echo 1. Abra o LibreOffice Writer
echo 2. Cole o conteudo
echo 3. Formate conforme necessario
echo 4. Exporte como PDF
echo.

start notepad DOCUMENTO_FINAL_PDF.md

echo.
echo ========================================
echo ARQUIVO ABERTO!
echo ========================================
echo.
echo O arquivo DOCUMENTO_FINAL_PDF.md foi aberto no Notepad.
echo Siga as instrucoes acima para gerar o PDF.
echo.
echo NOME DO ARQUIVO PDF SUGERIDO:
echo SGHSS_VidaPlus_Marcio_Moreira_RU_4543545.pdf
echo.
echo Pressione qualquer tecla para sair...
pause >nul
