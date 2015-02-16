#!/bin/sh

rm -rf q4
mkdir q4

./calc_model_exp.sh examples/train2.vectors.txt q4/model_count q1/m1.txt
./calc_model_exp.sh examples/train2.vectors.txt q4/model_count2