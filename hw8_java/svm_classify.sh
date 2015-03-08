#!/usr/bin/env bash
export JAVA_HOME=/opt/jdk8

mvn clean -q
mvn compile -q

mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ2" -Dexec.classpathScope=compile -Dexec.args="$1 $2 $3" -q > $4