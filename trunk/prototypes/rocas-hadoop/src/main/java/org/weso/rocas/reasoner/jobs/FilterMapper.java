package org.weso.rocas.reasoner.jobs;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.hp.hpl.jena.graph.Triple;

import examples.Node;

public class FilterMapper extends Mapper<Object, Text, Text, Text>{
	public void map(Object key, Text value, Context context, Triple triple)
			throws IOException, InterruptedException {
		String line = value.toString();
		StringTokenizer tokenizer = new StringTokenizer(line,";");
		if(tokenizer.hasMoreTokens()){
			Text cpvCod = new Text(tokenizer.nextToken());
			while (tokenizer.hasMoreTokens()) {
				context.write(new Text(cpvCod), new Text("1"));
			}
		}
	}


}
