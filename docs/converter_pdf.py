#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
Script para converter Markdown para PDF
"""

import os
import sys

try:
    from markdown import markdown
    import pdfkit
    print("✓ Bibliotecas importadas com sucesso")
except ImportError:
    print("❌ Instalando dependências...")
    os.system("pip install markdown pdfkit -q")
    from markdown import markdown
    import pdfkit

def converter_md_para_pdf(arquivo_md, arquivo_pdf):
    """Converter Markdown para PDF"""
    try:
        # Ler o arquivo markdown
        with open(arquivo_md, 'r', encoding='utf-8') as f:
            md_content = f.read()
        
        # Converter para HTML
        html_content = markdown(md_content)
        
        # Adicionar CSS para melhor formatação
        html_com_css = f"""
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8">
            <style>
                body {{
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    line-height: 1.6;
                    color: #333;
                    margin: 20px;
                }}
                h1 {{
                    color: #0066cc;
                    border-bottom: 3px solid #0066cc;
                    padding-bottom: 10px;
                }}
                h2 {{
                    color: #0066cc;
                    margin-top: 20px;
                }}
                h3 {{
                    color: #0099ff;
                }}
                table {{
                    border-collapse: collapse;
                    width: 100%;
                    margin: 15px 0;
                }}
                table, th, td {{
                    border: 1px solid #ddd;
                    padding: 12px;
                    text-align: left;
                }}
                th {{
                    background-color: #0066cc;
                    color: white;
                }}
                code {{
                    background-color: #f4f4f4;
                    padding: 2px 5px;
                    border-radius: 3px;
                    font-family: 'Courier New', monospace;
                }}
                pre {{
                    background-color: #f4f4f4;
                    padding: 10px;
                    border-left: 4px solid #0066cc;
                    overflow-x: auto;
                }}
                .success {{
                    color: #27ae60;
                }}
                .error {{
                    color: #e74c3c;
                }}
                .warning {{
                    color: #f39c12;
                }}
            </style>
        </head>
        <body>
            {html_content}
        </body>
        </html>
        """
        
        # Converter HTML para PDF usando pdfkit
        try:
            pdfkit.from_string(html_com_css, arquivo_pdf, options={
                'page-size': 'A4',
                'margin-top': '0.75in',
                'margin-right': '0.75in',
                'margin-bottom': '0.75in',
                'margin-left': '0.75in',
                'encoding': 'UTF-8',
                'enable-local-file-access': None
            })
            print(f"✅ PDF criado com sucesso: {arquivo_pdf}")
            return True
        except:
            # Se pdfkit falhar, tentar método alternativo
            print("⚠️  pdfkit não disponível, usando método alternativo...")
            
            # Salvar como HTML temporário
            html_file = arquivo_pdf.replace('.pdf', '.html')
            with open(html_file, 'w', encoding='utf-8') as f:
                f.write(html_com_css)
            
            print(f"✅ HTML criado como alternativa: {html_file}")
            print("   Você pode converter para PDF usando:")
            print(f"   - Navegador: Abrir {html_file} e imprimir como PDF")
            print(f"   - Linha de comando: wkhtmltopdf {html_file} {arquivo_pdf}")
            return True
            
    except Exception as e:
        print(f"❌ Erro ao converter: {str(e)}")
        return False

if __name__ == "__main__":
    arquivo_md = "00_DOCUMENTO_FINAL_SGHSS.md"
    arquivo_pdf = "00_DOCUMENTO_FINAL_SGHSS.pdf"
    
    print("=" * 60)
    print("Convertendo Markdown para PDF...")
    print("=" * 60)
    print(f"📄 Origem: {arquivo_md}")
    print(f"📕 Destino: {arquivo_pdf}")
    print("-" * 60)
    
    if os.path.exists(arquivo_md):
        if converter_md_para_pdf(arquivo_md, arquivo_pdf):
            print("-" * 60)
            print("✅ Conversão concluída!")
            if os.path.exists(arquivo_pdf):
                tamanho = os.path.getsize(arquivo_pdf) / (1024 * 1024)
                print(f"📊 Tamanho do PDF: {tamanho:.2f} MB")
        else:
            print("❌ Falha na conversão")
    else:
        print(f"❌ Arquivo não encontrado: {arquivo_md}")
