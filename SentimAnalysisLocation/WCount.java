package enis.hadoop.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class WCount
{
	public static void main(String[] args) throws Exception
	{
		Configuration conf=new Configuration();
		String[] ourArgs=new GenericOptionsParser(conf, args).getRemainingArgs();
		Job job=Job.getInstance(conf, "SentimAnalysisLocation");

		job.setJarByClass(WCount.class);
		job.setMapperClass(WCountMap.class);
		job.setReducerClass(WCountReduce.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, new Path(ourArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(ourArgs[1]));

		if(job.waitForCompletion(true))
			System.exit(0);
		System.exit(-1);
	}
}
