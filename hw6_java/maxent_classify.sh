#!/bin/sh

rm -rf q2
mkdir q2

testData=$1
modelFile=$2
sysOutput=$3
accOutput=$4

if [ -z "$1" ]; then
	testData="examples/test2.vectors.txt"
fi
if [ -z "$2" ]; then
	modelFile="q1/m1.txt"
fi
if [ -z "$3" ]; then
	sysOutput="q2/res"
fi
if [ -z "$4" ]; then
    accOutput="q2/acc"
fi

mvn clean -q
mvn compile -q
mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ2" -Dexec.args="${testData} ${modelFile} ${sysOutput}" -q > $accOutput