rd /S /Q q1
md q1
call mvn clean -q
call mvn compile -q
REM call mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ1" -Dexec.args="examples/train examples/test linear" -q
REM call mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ1" -Dexec.args="examples/train examples/test polynomial 1 0 2" -q
call mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ1" -Dexec.args="examples/train examples/test polynomial 0.1 0.5 2" -q
REM call mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ1" -Dexec.args="examples/train examples/test rbf 0.5" -q
REM call mvn exec:java -Dexec.mainClass="edu.washington.ling.roylance.MainQ1" -Dexec.args="examples/train examples/test sigmoid 0.5 -0.2" -q