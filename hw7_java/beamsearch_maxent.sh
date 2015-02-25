#!/bin/sh
export JAVA_HOME=/opt/jdk8

testData=$1
boundaryFile=$2
modelFile=$3
sysOutput=$4
beamSize=$5
topN=$6
topK=$7

if [ -z "$1" ]; then
	testData="examples/ex/test.txt"
fi
if [ -z "$2" ]; then
	boundaryFile="examples/ex/boundary.txt"
fi
if [ -z "$3" ]; then
	modelFile="examples/m1.txt"
fi
if [ -z "$4" ]; then
	sysOutput="q1/sys"
fi
if [ -z "$5" ]; then
	beamSize="0"
fi
if [ -z "$6" ]; then
	topN="1"
fi
if [ -z "$7" ]; then
	topK="1"
fi

mvn clean -q
mvn compile -q
mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.Main" -Dexec.args="$testData $boundaryFile $modelFile $sysOutput $beamSize $topN $topK" -q