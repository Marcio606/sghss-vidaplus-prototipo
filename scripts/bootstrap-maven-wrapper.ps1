param(
    [string]$WrapperUrl = 'https://repo1.maven.org/maven2/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar',
    [int]$Retries = 3
)

$targetDir = Join-Path -Path $PSScriptRoot -ChildPath '..\\.mvn\\wrapper'
if (-not (Test-Path $targetDir)) { New-Item -ItemType Directory -Path $targetDir -Force | Out-Null }

$targetFile = Join-Path -Path $targetDir -ChildPath 'maven-wrapper.jar'
if (Test-Path $targetFile) {
    Write-Host "maven-wrapper.jar já existe em: $targetFile"
    return
}

$i = 0
while ($i -lt $Retries) {
    try {
        Write-Host "Tentativa $($i + 1) de baixar maven-wrapper.jar de $WrapperUrl..."
        Invoke-WebRequest -Uri $WrapperUrl -OutFile $targetFile -UseBasicParsing -ErrorAction Stop
        Write-Host "Salvo em: $targetFile"
        break
    } catch {
        Write-Warning "Falha ao baixar: $($_.Exception.Message)"
        Remove-Item -Force -ErrorAction SilentlyContinue $targetFile
        Start-Sleep -Seconds 2
    }
    $i++
}

if (-not (Test-Path $targetFile)) { throw "Não foi possível baixar maven-wrapper.jar após $Retries tentativas." }

