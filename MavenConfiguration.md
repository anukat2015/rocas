# Introduction #

This is a short summary on how to configure Maven to use with Hadoop.


# Details #
  1. Start the HADOOP instance
```
$HADOOP_HOME/bin/start-all.sh
```
  1. oad input data in HDFS. For instance "input" is the directory with the input files
```
$HADOOP_HOME/bin/hadoop fs -put input
```
  1. Compile and package with Maven
```
maven assembly:assembly
```
  1. Run with Hadoop
```
$HADOOP_HOME/bin/hadoop jar target/rocas-hadoop-prototype-0.1-SNAPSHOT-job.jar input output
```
  1. Check the results
```
$HADOOP_HOME/bin/hadoop fs -cat output/part-r-00000
```

# Hints #
  1. Show Maven files (in the repository): pom.xml and src/main/assembly/
  1. Configure the Job conf
```
JobConf conf = new JobConf(WordCount.class);
```

# Bash script #

This is a mini-script that tries to apply all aforementioned. Nevertheless more parameters could be added to improve it.
```
#!/bin/bash
#Getting automatically names
artifactId=`grep artifactId pom.xml | head -1 | cut -d">" -f2 | cut -d"<" -f1`
version=`grep version pom.xml | head -1 | cut -d">" -f2 | cut -d"<" -f1`
input="input" #take as parameter
output="output" #take as parameter
#Compiling and packaging
mvn -o clean
mvn -o assembly:assembly -Dmaven.test.skip=true
#Preparing dirs
$HADOOP_HOME/bin/hadoop fs -put "${input}"
$HADOOP_HOME/bin/hadoop fs -rmr "${output}"
#Executing
$HADOOP_HOME/bin/hadoop jar target/"${artifactId}-${version}"-job.jar "${input}" "${output}"
```