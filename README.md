# SentimAnalysis - HADOOP (@ENIS Project)

## Packaging
```
$ javac WCount*java && mkdir -p enis/hadoop/wordcount && mv *.class enis/hadoop/wordcount && jar -cvf enis_wcount.jar -C . enis
```
## Deployment
```
$ hadoop jar enis_wcount.jar enis.hadoop.wordcount.WCount -libjars java-json.jar,$HBASE_CLASSES  /SentimentData.txt /result
```
## Logged ERRORS:
```
$ cat /usr/local/hadoop/logs/*.log | grep ERROR
```
## Logged STD(IN/OUT/ERR)
```
$ dir=/usr/local/hadoop/logs/userlogs/$(ls -t /usr/local/hadoop/logs/userlogs/ | head -n 1) ; cat $dir/* $dir/*/*
```
