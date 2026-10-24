default:
    mvn clean compile
exec:
    mvn exec:java -Dexec.mainClass="vs.Main"
clean:
    mvn clean
compile:
    mvn compile