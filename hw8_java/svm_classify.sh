#!/usr/bin/env bash
mvn clean -q
mvn compile -q

mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ2" -Dexec.args="$1 $2 $3" -q > $4