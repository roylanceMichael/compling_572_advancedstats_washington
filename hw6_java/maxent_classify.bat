call mvn clean -q
call mvn compile -q
call mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.Main" -Dexec.args="examples/test2.vectors.txt q1/m1.txt q2/res" -q