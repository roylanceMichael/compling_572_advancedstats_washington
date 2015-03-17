#!/usr/bin/env bash
export JAVA_HOME=/opt/jdk8

rm -rf q1
mkdir q1

mvn clean -q
mvn compile -q
mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ1" -Dexec.args="examples/test2.txt q1/m1.txt q2/sys 258" -q > q2/acc