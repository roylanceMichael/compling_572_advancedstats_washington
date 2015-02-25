#!/bin/sh
mvn clean -q
mvn compile -q
mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.Main" -Dexec.args="examples/ex/test.txt examples/ex/boundary.txt examples/m1.txt q1/sys 0 1 1" -q