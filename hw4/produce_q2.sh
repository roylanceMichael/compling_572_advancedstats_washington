#!/bin/bash
# Mike Roylance (roylance@uw.edu) & Olga Whelan (olgaw@uw.edu)

rm -rf q2
mkdir q2

# run each script
echo "running k=1, euclidean"
./build_kNN.sh examples/train.vectors.txt examples/test.vectors.txt 1 1 q2/sys_1_euclidean q2/acc_1_euclidean
echo "running k=1, cosine"
./build_kNN.sh examples/train.vectors.txt examples/test.vectors.txt 1 2 q2/sys_1_euclidean q2/acc_1_cosine
echo "running k=5, euclidean"
./build_kNN.sh examples/train.vectors.txt examples/test.vectors.txt 5 1 q2/sys_5_euclidean q2/acc_5_euclidean
echo "running k=5, cosine"
./build_kNN.sh examples/train.vectors.txt examples/test.vectors.txt 5 2 q2/sys_5_cosine q2/acc_5_cosine
echo "running k=10, euclidean"
./build_kNN.sh examples/train.vectors.txt examples/test.vectors.txt 10 1 q2/sys_10_euclidean q2/acc_10_euclidean
echo "running k=10, cosine"
./build_kNN.sh examples/train.vectors.txt examples/test.vectors.txt 10 2 q2/sys_10_cosine q2/acc_10_cosine