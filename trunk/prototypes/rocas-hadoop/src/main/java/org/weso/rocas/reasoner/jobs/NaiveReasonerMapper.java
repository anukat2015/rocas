package org.weso.rocas.reasoner.jobs;

import java.io.IOException;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.weso.rocas.model.mapper.N3Mapper;
import org.weso.rocas.utils.SPARQLTripleMatch;
import org.weso.rocas.utils.TripleUtils;

import com.hp.hpl.jena.graph.Triple;

public class NaiveReasonerMapper extends N3Mapper{
	public String filter;
	public NaiveReasonerMapper(){
		this.filter = "?s <http://www.w3.org/2004/02/skos/core#broader> ?o.";
	}
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		//Each line is a triple
		String tripleStr = value.toString();
		Triple t = TripleUtils.asTriple(tripleStr);
		//Search if this triple matches any active goal
		//if matches then create  (rule-name; value | n_antecedent | AND, OR)
		if(SPARQLTripleMatch.accept(value.toString(),this.filter)){
			
				context.write(value,value);
			}
		}

}
