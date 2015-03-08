#!/usr/bin/env bash
rm -rf q1
mkdir q1

mvn clean -q
mvn compile -q

mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ1" -Dexec.args="examples/train examples/test linear" -q > q1/acc.1
mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ1" -Dexec.args="examples/train examples/test polynomial 1 0 2" -q > q1/acc.2
mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ1" -Dexec.args="examples/train examples/test polynomial 0.1 0.5 2" -q > q1/acc.3
mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ1" -Dexec.args="examples/train examples/test rbf 0.5" -q > q1/acc.4
mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ1" -Dexec.args="examples/train examples/test sigmoid 0.5 -0.2" -q > q1/acc.5