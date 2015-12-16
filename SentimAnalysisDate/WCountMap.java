package enis.hadoop.wordcount;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.JSONException;
import org.json.JSONObject;

class WCountMap extends Mapper<Object, Text, Text, IntWritable> {

	static Log logger = LogFactory.getLog(WCountMap.class);

	private int getMark(String word) {
		switch (word) {
		case "negative":
			return -1;
		case "positive":
			return 1;
		default:
			return 0;
		}
	}

	private HashMap<String, Integer> getDictionnary()
			throws FileNotFoundException, IOException {

		Path pt = new Path("/dictionary.txt");
		FileSystem fs = FileSystem.get(new Configuration());
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				fs.open(pt)));
		String line;
		StringTokenizer st;

		while ((line = br.readLine()) != null) {

			st = new StringTokenizer(line);

			st.nextToken();
			st.nextToken();
			String word = st.nextToken();
			st.nextToken();
			st.nextToken();
			String vote = st.nextToken();
			int mark = getMark(vote);

			data.put(word.toLowerCase(), mark);
		}

		return data;

	}

	protected void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {

		Scanner scanner = new Scanner(value.toString());
		HashMap<String, Integer> data = getDictionnary();

		while (scanner.hasNext()) {

			String line = scanner.nextLine();

			String text = "";
			String creationDate = "";

			try {
				JSONObject obj = new JSONObject(line);
				creationDate = obj.getString("created_at");
				text = obj.getString("text");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			Text date = new Text(WCountDateConverter.getMonth(creationDate));

			StringTokenizer tok = new StringTokenizer(text, " ");

			while (tok.hasMoreTokens()) {

				String wordToTest = tok.nextToken().toLowerCase();

				IntWritable rate = new IntWritable(0);

				if (data.containsKey(wordToTest)) {
					rate = new IntWritable(data.get(wordToTest));
				}

				context.write(date, rate);
			}

		}
		scanner.close();
	}
}
