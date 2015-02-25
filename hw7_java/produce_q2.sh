#!/bin/sh

echo "calculating beamsize:0 topN:1 topK:1"
rm -rf q2_0_1_1
mkdir q2_0_1_1
{ time $(./beamsearch_maxent.sh examples/sec19_21.txt examples/sec19_21.boundary examples/m1.txt q2_0_1_1/sys 0 1 1 > q2_0_1_1/acc) ; } 2> q2_0_1_1/time

echo "calculating beamsize:1 topN:3 topK:5"
rm -rf q2_1_3_5
mkdir q2_1_3_5
{ time $(./beamsearch_maxent.sh examples/sec19_21.txt examples/sec19_21.boundary examples/m1.txt q2_1_3_5/sys 1 3 5 > q2_1_3_5/acc) ; } 2> q2_1_3_5/time

echo "calculating beamsize:2 topN:5 topK:10"
rm -rf q2_2_5_10
mkdir q2_2_5_10
{ time $(./beamsearch_maxent.sh examples/sec19_21.txt examples/sec19_21.boundary examples/m1.txt q2_2_5_10/sys 2 5 10 > q2_2_5_10/acc) ; } 2> q2_2_5_10/time

echo "calculating beamsize:3 topN:10 topK:100"
rm -rf q2_3_10_100
mkdir q2_3_10_100
{ time $(./beamsearch_maxent.sh examples/sec19_21.txt examples/sec19_21.boundary examples/m1.txt q2_3_10_100/sys 3 10 100 > q2_3_10_100/acc) ; } 2> q2_3_10_100/time

