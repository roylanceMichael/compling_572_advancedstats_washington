#!/bin/bash
# Mike Roylance (roylance@uw.edu) & Olga Whelan (olgaw@uw.edu)

testFile=$1
modelFile=$2
sysOutput=$3

if [ -z "$1" ]; then
	testFile="examples/test2.vectors.txt"
fi
if [ -z "$2" ]; then
	modelFile="q1/m1.txt"
fi
if [ -z "$3" ]; then
	sysOutput="sys_default"
fi

python2.7 source/main.py $testFile $modelFile $sysOutput