call mvn clean -q
call mvn compile -q
call mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.Main" -Dexec.args="examples/test2.txt m1.txt 0.01" -q