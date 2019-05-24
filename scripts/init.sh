
mkdir out
javac src/main/enums/* -d out
javac src/main/checker/* src/main/container/* src/main/algorithm/* src/main/Daifugo.java -d out -classpath ./out
