call mvn clean -q
call mvn compile -q
call mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ3" -Dexec.args="examples/test2.vectors.txt" -q