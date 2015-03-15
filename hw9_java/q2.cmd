call mvn clean -q
call mvn compile -q
call mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ2" -Dexec.args="examples/test2.txt m1.txt sysOut" -q > acc