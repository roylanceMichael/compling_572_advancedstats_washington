#!/bin/bash
# Mike Roylance (roylance@uw.edu) & Olga Whelan (olgaw@uw.edu)

trainingData=$1
outputFile=$2

if [ -z "$1" ]; then
	trainingData="examples/train2.vectors.txt"
fi
if [ -z "$2" ]; then
	outputFile="output_file"
fi

python2.7 source/main_q4.py $trainingData $outputFile