#!/usr/bin/env sh
# ----------------------------------------------------------------------------
# Maven Wrapper
# This script is a minimal wrapper that executes the maven-wrapper.jar located
# at .mvn/wrapper/maven-wrapper.jar. Use the bundled PowerShell script to
# download the jar if it's not present.
# ----------------------------------------------------------------------------
PRG="${0}"
PRGDIR=`dirname "${PRG}"`
BASEDIR=`cd "${PRGDIR}" && pwd`
exec java -jar "${BASEDIR}/.mvn/wrapper/maven-wrapper.jar" "$@"
