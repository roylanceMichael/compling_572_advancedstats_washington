#!/bin/sh

rm -rf q3
mkdir q3

testData=$1
sysOutput=$2

if [ -z "$1" ]; then
	testData="examples/test2.vectors.txt"
fi
if [ -z "$2" ]; then
	sysOutput="q3/emp_count"
fi

mvn clean -q
mvn compile -q
mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ3" -Dexec.args="${testData}" -q > ${sysOutput}