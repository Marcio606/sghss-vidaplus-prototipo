# Script para baixar o Maven Wrapper JAR manualmente
# Execute este script caso o Maven Wrapper não funcione

param(
    [string]$WrapperVersion = '3.3.4',
    [int]$Retries = 3
)

$wrapperUrl = "https://repo1.maven.org/maven2/org/apache/maven/wrapper/maven-wrapper/$WrapperVersion/maven-wrapper-$WrapperVersion.jar"
$targetDir = Join-Path -Path $PSScriptRoot -ChildPath '..\\.mvn\\wrapper'

if (-not (Test-Path $targetDir)) {
    New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
    Write-Host "Diretório criado: $targetDir"
}

$targetFile = Join-Path -Path $targetDir -ChildPath 'maven-wrapper.jar'

if (Test-Path $targetFile) {
    Write-Host "maven-wrapper.jar já existe em: $targetFile"
    Write-Host "Para forçar o re-download, delete o arquivo primeiro."
    return
}

Write-Host "Baixando Maven Wrapper $WrapperVersion..."
$i = 0
$success = $false

while ($i -lt $Retries) {
    try {
        Write-Host "Tentativa $($i + 1) de $Retries..."
        Invoke-WebRequest -Uri $wrapperUrl -OutFile $targetFile -UseBasicParsing -ErrorAction Stop
        Write-Host "✓ Maven Wrapper baixado com sucesso!"
        Write-Host "  Salvo em: $targetFile"
        $success = $true
        break
    } catch {
        Write-Warning "✗ Falha ao baixar: $($_.Exception.Message)"
        if (Test-Path $targetFile) {
            Remove-Item -Force -ErrorAction SilentlyContinue $targetFile
        }
        Start-Sleep -Seconds 2
    }
    $i++
}

if (-not $success) {
    Write-Error "Não foi possível baixar maven-wrapper.jar após $Retries tentativas."
    Write-Host "`nAlternativa: Execute 'mvn wrapper:wrapper' para regenerar o wrapper."
    exit 1
}

Write-Host "`nVerificando o Maven Wrapper..."
Set-Location (Join-Path -Path $PSScriptRoot -ChildPath '..')

if ($IsWindows -or $env:OS -like "Windows*") {
    & .\mvnw.cmd -version
} else {
    chmod +x ./mvnw
    & ./mvnw -version
}

if ($LASTEXITCODE -eq 0) {
    Write-Host "`n✓ Maven Wrapper está funcionando corretamente!"
} else {
    Write-Warning "`n✗ Maven Wrapper pode não estar funcionando. Verifique os logs acima."
}

