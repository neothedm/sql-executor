# sql-executor

Download:
git clone https://github.com/neothedm/sql-executor.git

Build standalone executable with dependencies:
Navigate to the source directory and execute: mvn clean compile assembly:single

An executable will be generated named something like: executor-1.0-SNAPSHOT-jar-with-dependencies.jar

You can then use it like:
java -jar executor-1.0-SNAPSHOT-jar-with-dependencies.jar -s testsql-server.local -t 1425 -d testdatabase -u testuser -p testpassword -q "SELECT count(*) FROM SOME_TABLE" -f csv
