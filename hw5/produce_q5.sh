#!/bin/bash
# Olga Whelan (olgaw@uw.edu) & Mike Roylance (roylance@uw.edu)

executeScript () 
{
cat q5/feat_list | ./filter_feat_by_scores.sh ${2} > q5/${1}_kept_feat_list
./build_kNN2.sh examples/train2.vectors.txt examples/test2.vectors.txt 10 2 q5/${1}_kept_feat_list q5/${1}_sys > q5/${1}_acc
}

rm -rf q5
mkdir q5

# calculate the feature list initially
cat examples/train2.vectors.txt | ./rank_feat_by_chi_square.sh > q5/feat_list

# calculate the kept feature lists and then the kmeans
executeScript "base" 0.0
executeScript "001" 0.001
executeScript "01" 0.01
executeScript "025" 0.025
executeScript "05" 0.05
executeScript "1" 0.1