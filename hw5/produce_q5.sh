#!/bin/bash
# Olga Whelan (olgaw@uw.edu) & Mike Roylance (roylance@uw.edu)

executeScript () 
{
cat q5/feat_list | ./filter_feat_by_scores.sh ${2} > q5/${1}_kept_feat_list
./build_kNN2.sh examples/train2.vectors.txt examples/test2.vectors.txt 10 2 q5/${1}_kept_feat_list q5/${1}_sys q5/${1}_acc
}

rm -rf q5
mkdir q5

# calculate the feature list initially
cat examples/train2.vectors.txt | ./rank_feat_by_chi_square.sh > q5/feat_list

# calculate the kept feature lists and then the kmeans
executeScript "base" 0.0
executeScript "001" 13.816
executeScript "01" 9.210
executeScript "025" 7.378
executeScript "05" 5.991
executeScript "1" 4.605