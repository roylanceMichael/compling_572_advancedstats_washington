#!/bin/bash
# Mike Roylance (roylance@uw.edu) & Olga Whelan (olgaw@uw.edu)

trainFile=$1
testFile=$2
kval=$3
similarity_func=$4

if [ -z "$1" ]; then
	trainFile="examples/train.vectors.txt"
fi
if [ -z "$2" ]; then
	testFile="examples/test.vectors.txt"
fi
if [ -z "$3" ]; then
	kval=5
fi
if [ -z "$4" ]; then
	similarity_func=1
fi
 
python2.7 source/main.py $trainFile $testFile $kval $similarity_func