Linux:

    javac -cp *.jar *.java -d .
    java -cp .:* Main TrafficFlowDataset.csv

Windows systems (Powershell):

    javac -cp *.jar *.java -d .
    java -cp '.;*' Main .\TrafficFlowDataset.csv

Windows systems (cmd):

    javac -cp *.jar *.java -d .
    java -cp .;* Main .\TrafficFlowDataset.csv
    