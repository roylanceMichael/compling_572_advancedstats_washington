#!/bin/sh

rm -rf q3
mkdir q3

echo "n=1"
./TBL_classify.sh examples/test2.txt q1/m1.txt q3/sysTest1 1 q3/accTest1
./TBL_classify.sh examples/train2.txt q1/m1.txt q3/sysTrain1 1 q3/accTrain1

echo "n=5"
./TBL_classify.sh examples/test2.txt q1/m1.txt q3/sysTest5 5 q3/accTest5
./TBL_classify.sh examples/train2.txt q1/m1.txt q3/sysTrain5 5 q3/accTrain5

echo "n=10"
./TBL_classify.sh examples/test2.txt q1/m1.txt q3/sysTest10 10 q3/accTest10
./TBL_classify.sh examples/train2.txt q1/m1.txt q3/sysTrain10 10 q3/accTrain10

echo "n=20"
./TBL_classify.sh examples/test2.txt q1/m1.txt q3/sysTest20 20 q3/accTest20
./TBL_classify.sh examples/train2.txt q1/m1.txt q3/sysTrain20 20 q3/accTrain20

echo "n=50"
./TBL_classify.sh examples/test2.txt q1/m1.txt q3/sysTest50 50 q3/accTest50
./TBL_classify.sh examples/train2.txt q1/m1.txt q3/sysTrain50 50 q3/accTrain50

echo "n=100"
./TBL_classify.sh examples/test2.txt q1/m1.txt q3/sysTest100 100 q3/accTest100
./TBL_classify.sh examples/train2.txt q1/m1.txt q3/sysTrain100 100 q3/accTrain100

echo "n=150"
./TBL_classify.sh examples/test2.txt q1/m1.txt q3/sysTest150 150 q3/accTest150
./TBL_classify.sh examples/train2.txt q1/m1.txt q3/sysTrain150 150 q3/accTrain150

echo "n=200"
./TBL_classify.sh examples/test2.txt q1/m1.txt q3/sysTest200 200 q3/accTest200
./TBL_classify.sh examples/train2.txt q1/m1.txt q3/sysTrain200 200 q3/accTrain200

echo "n=250"
./TBL_classify.sh examples/test2.txt q1/m1.txt q3/sysTest250 250 q3/accTest250
./TBL_classify.sh examples/train2.txt q1/m1.txt q3/sysTrain250 250 q3/accTrain250
