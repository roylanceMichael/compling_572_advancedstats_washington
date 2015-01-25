#!/bin/bash
# Mike Roylance (roylance@uw.edu) & Olga Whelan (olgaw@uw.edu)

rm -rf q5
mkdir q5

# run each script
echo "running cond_prob_delta=0.1"
./build_NB2.sh examples/train.vectors.txt examples/test.vectors.txt 0 0.1 q5/model_0_1 q5/sys_0_1 q5/acc_0_1 1
echo "running cond_prob_delta=0.5"
./build_NB2.sh examples/train.vectors.txt examples/test.vectors.txt 0 0.5 q5/model_0_5 q5/sys_0_5 q5/acc_0_5 1
echo "running cond_prob_delta=1.0"
./build_NB2.sh examples/train.vectors.txt examples/test.vectors.txt 0 1.0 q5/model_1_0 q5/sys_1_0 q5/acc_1_0 1
echo "running cond_prob_delta=2.0"
./build_NB2.sh examples/train.vectors.txt examples/test.vectors.txt 0 2.0 q5/model_2_0 q5/sys_2_0 q5/acc_2_0 1