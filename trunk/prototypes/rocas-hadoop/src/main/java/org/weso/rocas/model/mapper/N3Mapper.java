package org.weso.rocas.model.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.weso.rocas.io.N3Triple;

public class N3Mapper extends MapReduceBase implements Mapper<LongWritable, N3Triple, LongWritable, N3Triple> {


	@Override
	public void configure(JobConf arg0) {
		// TODO Auto-generated method stub
		super.configure(arg0);
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		super.close();

	}

	@Override
	public void map(LongWritable key, N3Triple value,
			OutputCollector<LongWritable, N3Triple> output, Reporter reporter)
					throws IOException {
		output.collect(key, value);
	}

}
