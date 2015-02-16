#!/bin/sh
# setting java 8 version manually
export JAVA_HOME=/opt/jdk8

trainData=$1
sysOutput=$2
modelFile=$3

if [ -z "$1" ]; then
	trainData="examples/train2.vectors.txt"
fi
if [ -z "$2" ]; then
	sysOutput="q4/model_count"
fi

mvn clean -q
mvn compile -q
mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ4" -Dexec.args="${trainData} ${modelFile}" -q > ${sysOutput}