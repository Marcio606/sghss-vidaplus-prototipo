# 📄 COMO GERAR O PDF DO PROJETO

## 🎯 **OPÇÕES PARA GERAR O PDF**

### **OPÇÃO 1: Script Automático (Recomendado)**

```bash
# Execute o script no Windows
gerar_pdf.bat

# Ou no Linux/Mac
chmod +x gerar_pdf.sh
./gerar_pdf.sh
```

### **OPÇÃO 2: Pandoc Manual**

```bash
# Instalar Pandoc (se não tiver)
# Windows: choco install pandoc
# Linux: sudo apt install pandoc
# Mac: brew install pandoc

# Gerar PDF
pandoc DOCUMENTO_FINAL_PDF.md -o "SGHSS_VidaPlus_Marcio_Moreira_RU_4543545.pdf" \
    --pdf-engine=wkhtmltopdf \
    --margin-top=2cm \
    --margin-bottom=2cm \
    --margin-left=2cm \
    --margin-right=2cm \
    --footer-center="SGHSS Vida Plus - Marcio Machado Moreira (RU: 4543545)" \
    --footer-font-size=10 \
    --toc \
    --toc-depth=3
```

### **OPÇÃO 3: Conversão Online**

1. **Acesse:** https://pandoc.org/try/
2. **Cole o conteúdo** do arquivo `DOCUMENTO_FINAL_PDF.md`
3. **Selecione:** Markdown → PDF
4. **Baixe** o arquivo gerado

### **OPÇÃO 4: Microsoft Word**

1. **Abra** o arquivo `DOCUMENTO_FINAL_PDF.md`
2. **Copie** todo o conteúdo
3. **Abra** o Microsoft Word
4. **Cole** o conteúdo
5. **Formate** conforme necessário:
   - Títulos em negrito
   - Tabelas organizadas
   - Quebras de página
6. **Exporte** como PDF

### **OPÇÃO 5: Google Docs**

1. **Acesse:** https://docs.google.com
2. **Crie** um novo documento
3. **Cole** o conteúdo do `DOCUMENTO_FINAL_PDF.md`
4. **Formate** o documento
5. **Baixe** como PDF

### **OPÇÃO 6: LibreOffice**

1. **Abra** o LibreOffice Writer
2. **Cole** o conteúdo do `DOCUMENTO_FINAL_PDF.md`
3. **Formate** conforme necessário
4. **Exporte** como PDF

---

## 📋 **ESTRUTURA DO PDF FINAL**

### **Página de Rosto:**
- Nome da universidade
- Curso e disciplina
- Nome do projeto
- Nome do aluno e RU
- Data de entrega

### **Sumário:**
- Introdução
- Requisitos
- Modelagem e Arquitetura
- Implementação
- Plano de Testes
- Conclusão
- Referências
- Anexos

### **Conteúdo Principal:**
- **Introdução** - Contexto e objetivos
- **Requisitos** - Funcionais e não funcionais
- **Modelagem** - Diagramas e arquitetura
- **Implementação** - Código e funcionalidades
- **Testes** - Casos e resultados
- **Conclusão** - Resultados e lições aprendidas

### **Anexos:**
- Link do repositório GitHub
- Instruções de execução
- Endpoints da API
- Tecnologias utilizadas

---

## 🎯 **INFORMAÇÕES IMPORTANTES**

### **Nome do Arquivo PDF:**
`SGHSS_VidaPlus_Marcio_Moreira_RU_4543545.pdf`

### **Link do Repositório:**
https://github.com/Marcio606/sghss-vidaplus

### **Dados do Aluno:**
- **Nome:** Marcio Machado Moreira
- **RU:** 4543545
- **Email:** marcio606@email.com

### **Estrutura do Repositório:**
```
sghss-vidaplus/
├── DOCUMENTO_FINAL_PDF.md      # Conteúdo do PDF
├── gerar_pdf.bat               # Script para gerar PDF
├── COMO_GERAR_PDF.md           # Este arquivo
├── README.md                   # Documentação principal
├── src/                        # Código fonte
└── ...outros arquivos
```

---

## ✅ **CHECKLIST PARA ENTREGA**

### **PDF:**
- [ ] Arquivo PDF gerado com sucesso
- [ ] Nome do arquivo correto
- [ ] Todas as seções incluídas
- [ ] Link do GitHub presente
- [ ] Dados do aluno corretos
- [ ] Formatação adequada

### **GitHub:**
- [ ] Repositório público criado
- [ ] Código fonte completo
- [ ] Documentação atualizada
- [ ] README.md com instruções
- [ ] Link funcionando

### **Conteúdo:**
- [ ] Todas as fases documentadas
- [ ] Implementação completa
- [ ] Testes validados
- [ ] Conclusão elaborada
- [ ] Referências incluídas

---

## 🚀 **COMANDOS RÁPIDOS**

```bash
# Gerar PDF automaticamente
gerar_pdf.bat

# Verificar repositório
git status

# Fazer commit final
git add .
git commit -m "Documento final para entrega"
git push origin main

# Abrir repositório no navegador
start https://github.com/Marcio606/sghss-vidaplus
```

---

**📄 PDF pronto para entrega acadêmica!**
