Execution (http://hadoop.apache.org/common/docs/r0.20.2/quickstart.html)

Format a new distributed-filesystem:
$ bin/hadoop namenode -format

Start the hadoop daemons:
$ bin/start-all.sh

The hadoop daemon log output is written to the ${HADOOP\_LOG\_DIR} directory (defaults to ${HADOOP\_HOME}/logs).

Browse the web interface for the NameNode and the JobTracker; by default they are available at:

  * NameNode - http://localhost:50070/
  * JobTracker - http://localhost:50030/
  * Copy the input files into the distributed filesystem:

> `$ bin/hadoop fs -put conf input`

Run some of the examples provided:

```

$ bin/hadoop jar hadoop-*-examples.jar grep input output 'dfs[a-z.]+'

```




Copy the output files from the distributed filesystem to the local filesytem and examine them:
```

$ bin/hadoop fs -get output output 
$ cat output/*
```

or

View the output files on the distributed filesystem:

```

$ bin/hadoop fs -cat output/*

```

When you're done, stop the daemons with:
```

$ bin/stop-all.sh

```



# Tips #
  * http://www.michael-noll.com/tutorials/running-hadoop-on-ubuntu-linux-multi-node-cluster/#java-io-ioexception-incompatible-namespaceids