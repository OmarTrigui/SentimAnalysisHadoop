package enis.hadoop.wordcount;

import java.io.IOException;
import java.util.Iterator;

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
	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {

		Configuration config = new Configuration();
		config = HBaseConfiguration.create(config);

		// TODO : Must be Singleton -_-
		
		Connection connection = ConnectionFactory.createConnection(config);

		try {

			Table table = connection.getTable(TableName.valueOf("Loc"));
			try {

				Iterator<IntWritable> i = values.iterator();
				int count = 0;
				while (i.hasNext())
					count += i.next().get();
				context.write(key, new Text(count + " likes."));

				String locationKey = key.toString().trim().equals("") ? "Others"
						: key.toString();

				Put p = new Put(Bytes.toBytes(locationKey));

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