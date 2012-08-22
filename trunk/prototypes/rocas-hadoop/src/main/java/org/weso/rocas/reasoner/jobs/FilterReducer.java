package org.weso.rocas.reasoner.jobs;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FilterReducer extends Reducer<Text, Text, Text, Text>{

	protected void reduce(Text key, Iterable<Text> values,
			org.apache.hadoop.mapreduce.Reducer.Context context)
			throws IOException, InterruptedException {

		super.reduce(key, values, context);
	}

}
