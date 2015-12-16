package enis.hadoop.wordcount;

import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WCountReduce extends Reducer<Text, IntWritable, Text, Text> {

	static Log logger = LogFactory.getLog(WCountMap.class);

	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {

		Configuration config = new Configuration();
		config = HBaseConfiguration.create(config);

		// TODO : Must be Singleton -_-
		
		Connection connection = ConnectionFactory.createConnection(config);

		try {

			Table table = connection.getTable(TableName
					.valueOf("myLittleHBaseTable"));
			try {

				Iterator<IntWritable> i = values.iterator();
				int count = 0;
				
				while (i.hasNext())
					count += i.next().get();
				
				Text resultDate = new Text(
						WCountDateConverter.monthToReadableDate(key.toString()));
				
				context.write(resultDate, new Text(count + " Likes."));

				Put p = new Put(Bytes.toBytes(resultDate.toString()));

				p.addColumn(Bytes.toBytes("myLittleFamily"),
						Bytes.toBytes("someQualifier"),
						Bytes.toBytes(count + " Likes"));

				table.put(p);

			} finally {
				if (table != null)
					table.close();
			}

		} finally {

		}

	}
}