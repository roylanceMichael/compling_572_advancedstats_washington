call mvn clean -q
call mvn compile -q
call mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ1" -Dexec.args="examples/test2.txt q1.txt 0.01" -q