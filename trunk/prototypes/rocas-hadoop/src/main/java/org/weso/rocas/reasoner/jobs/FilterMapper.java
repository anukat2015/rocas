package org.weso.rocas.reasoner.jobs;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.weso.rocas.utils.SPARQLTripleMatch;

public class FilterMapper extends Mapper<LongWritable, Text, Text, Text>{
	public String filter;
	public FilterMapper(){
		this.filter = "?s <http://www.w3.org/2004/02/skos/core#broader> ?o.";
	}
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		if(SPARQLTripleMatch.accept(value.toString(),this.filter)){
				context.write(value,value);
			}
		}
}
