#!/bin/bash
# Mike Roylance (roylance@uw.edu) & Olga Whelan (olgaw@uw.edu)

rm -rf q3
mkdir q3

# run each script
echo "running cond_prob_delta=0.1"
./build_NB1.sh examples/train.vectors.txt examples/test.vectors.txt 0 0.1 q3/model_0_1 q3/sys_0_1 q3/acc_0_1 
echo "running cond_prob_delta=0.5"
./build_NB1.sh examples/train.vectors.txt examples/test.vectors.txt 0 0.5 q3/model_0_5 q3/sys_0_5 q3/acc_0_5
echo "running cond_prob_delta=1.0"
./build_NB1.sh examples/train.vectors.txt examples/test.vectors.txt 0 1.0 q3/model_1_0 q3/sys_1_0 q3/acc_1_0
echo "running cond_prob_delta=2.0"
./build_NB1.sh examples/train.vectors.txt examples/test.vectors.txt 0 2.0 q3/model_2_0 q3/sys_2_0 q3/acc_2_0