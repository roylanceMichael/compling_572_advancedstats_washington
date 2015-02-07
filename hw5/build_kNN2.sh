#!/bin/bash
# Mike Roylance (roylance@uw.edu) & Olga Whelan (olgaw@uw.edu)

trainFile=$1
testFile=$2
kval=$3
similarity_func=$4
kept_feat_list=$5
sysOutput=$6
accOutput=$7

if [ -z "$1" ]; then
	trainFile="examples/train.vectors.txt"
fi
if [ -z "$2" ]; then
	testFile="examples/test.vectors.txt"
fi
if [ -z "$3" ]; then
	kval=1
fi
if [ -z "$4" ]; then
	similarity_func=2
fi
if [ -z "$5" ]; then
	kept_feat_list="kept_feat_list"
fi 
if [ -z "$6" ]; then
	sysOutput="sys_default"
fi
if [ -z "$7" ]; then
	accOutput="acc_default"
fi

python2.7 source/main_q3.py $trainFile $testFile $kval $similarity_func $kept_feat_list $sysOutput > $accOutput