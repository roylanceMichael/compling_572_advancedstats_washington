#!/usr/bin/env bash
export JAVA_HOME=/opt/jdk8

testData=$1
modelFile=$2
sysOutput=$3
n=$4
accOutput=$5

if [ -z "$1" ]; then
	testData="examples/test2.txt"
fi
if [ -z "$2" ]; then
	modelFile="q1/m1.txt"
fi
if [ -z "$3" ]; then
	sysOutput="q2/sys"
fi
if [ -z "$4" ]; then
	n="258"
fi

if [ -z "$5" ]; then
	accOutput="q2/acc"
fi

mvn clean -q
mvn compile -q
mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ2" -Dexec.args="${testData} ${modelFile} ${sysOutput} ${n}" -q > ${accOutput}
