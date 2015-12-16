# SentimAnalysis - HADOOP (@ENIS Project)

## Deployment
```
$ javac WCount*java && mkdir -p enis/hadoop/wordcount && mv *.class enis/hadoop/wordcount && jar -cvf enis_wcount.jar -C . enis
$ hadoop jar enis_wcount.jar enis.hadoop.wordcount.WCount -libjars java-json.jar,$HBASE_CLASSES  /SentimentData.txt /result
```
## Logged Errors:
```
$ cat /usr/local/hadoop/logs/*.log | grep ERROR
```
## Packaging
```
$ javac WCount*java && mkdir -p enis/hadoop/wordcount && mv *.class enis/hadoop/wordcount && jar -cvf enis_wcount.jar -C . enis
```
## STDOUT Logging
$ dir=/usr/local/hadoop/logs/userlogs/$(ls -t /usr/local/hadoop/logs/userlogs/ | head -n 1) ; cat $dir/* $dir/*/*