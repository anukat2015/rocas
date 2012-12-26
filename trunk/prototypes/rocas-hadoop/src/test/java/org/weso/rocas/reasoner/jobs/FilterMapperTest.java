package org.weso.rocas.reasoner.jobs;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.junit.Test;

public class FilterMapperTest {

	@Test
	public void test() throws IOException, InterruptedException {
		FilterMapper mapper = new FilterMapper();
		
		Text value = new Text("<http://example.org/a> <http://www.w3.org/2004/02/skos/core#broader> <http://example.org/b>.");
		LongWritable key = new LongWritable(1);
		mapper.map(key , value, null);
	}

}
