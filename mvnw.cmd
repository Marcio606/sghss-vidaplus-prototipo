@echo off
REM Maven Wrapper for Windows
setlocal
set PRG=%~dp0
java -jar "%PRG%.mvn\wrapper\maven-wrapper.jar" %*
