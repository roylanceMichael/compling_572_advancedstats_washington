#!/bin/bash
# Mike Roylance (roylance@uw.edu) & Olga Whelan (olgaw@uw.edu)

trainFile=$1
testFile=$2
classPriorDelta=$3
condProbDelta=$4
modelFile=$5
sysOutput=$6
accFile=$7
binarize=$8

if [ -z "$1" ]; then
	trainFile="examples/sub.train.vectors.txt"
fi
if [ -z "$2" ]; then
	testFile="examples/sub.test.vectors.txt"
fi
if [ -z "$3" ]; then
	classPriorDelta=0
fi
if [ -z "$4" ]; then
	condProbDelta=1
fi
if [ -z "$5" ]; then
	modelFile="model_default"
fi
if [ -z "$6" ]; then
	sysOutput="sys_default"
fi
if [ -z "$7" ]; then
	accFile="acc_default"
fi
if [ -z "$8" ]; then
	binarize=0
fi

python source/q4_main.py $trainFile $testFile $classPriorDelta $condProbDelta $modelFile $sysOutput $binarize > $accFile