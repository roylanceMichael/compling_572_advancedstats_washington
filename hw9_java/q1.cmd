call mvn clean -q
call mvn compile -q
call mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ1" -Dexec.args="examples/train2.txt m1.txt 1" -q