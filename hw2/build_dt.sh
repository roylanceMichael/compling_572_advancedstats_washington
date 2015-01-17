#!/bin/bash
# Mike Roylance (roylance@uw.edu) & Olga Whelon (olgaw@uw.edu)

trainFile=$1
testFile=$2
maxDepth=$3
minGain=$4
modelFile=$5
sysOutput=$6
accFile=$7

if [ -z "$1" ]; then
	trainFile="examples/train.vectors.txt"
fi
if [ -z "$2"]; then
	testFile="examples/test.vectors.txt"
fi
if [ -z "$3"]; then
	maxDepth=10
fi
if [ -z "$4"]; then
	minGain=0.1
fi
if [ -z "$5"]; then
	modelFile="examples/model_default"
fi
if [ -z "$6"]; then
	sysOutput="examples/sys_default"
fi
if [ -z "$7"]; then
	accFile="examples/acc_default"
fi

python src/main.py $trainFile $testFile $maxDepth $minGain $modelFile $sysOutput > $accFile