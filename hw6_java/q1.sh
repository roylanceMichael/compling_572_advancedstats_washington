#!/bin/sh
rm -rf q1
mkdir q1
mallet import-svmlight --input examples/train2.vectors.txt examples/test2.vectors.txt --output q1/train2.vectors q1/test2.vectors
mallet train-classifier --input q1/train2.vectors --trainer MaxEnt --output-classifier q1/m1
mallet classify-svmlight --input examples/test2.vectors.txt --output q1/classified.txt --classifier q1/m1
classifier2info --classifier q1/m1 > q1/m1.txt
vectors2classify --training-file q1/train2.vectors --testing-file q1/test2.vectors --trainer MaxEnt --report test:raw test:accuracy test:confusion train:confusion train:accuracy> q1/out.stdout 2>q1/out.stderr
