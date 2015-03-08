#!/usr/bin/env bash
rm -rf q2
mkdir q2

./svm_classify.sh examples/test q1/model.1 q2/sys.1 q2/acc.1
./svm_classify.sh examples/test q1/model.2 q2/sys.2 q2/acc.2
./svm_classify.sh examples/test q1/model.3 q2/sys.3 q2/acc.3
./svm_classify.sh examples/test q1/model.4 q2/sys.4 q2/acc.4
./svm_classify.sh examples/test q1/model.5 q2/sys.5 q2/acc.5