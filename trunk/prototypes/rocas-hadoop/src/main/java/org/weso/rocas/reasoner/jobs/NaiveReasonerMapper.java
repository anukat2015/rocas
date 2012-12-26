package org.weso.rocas.reasoner.jobs;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.weso.rocas.dao.impl.JENARuleDAOImpl;
import org.weso.rocas.utils.SPARQLTripleMatch;
import org.weso.rocas.utils.TripleUtils;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

public class NaiveReasonerMapper extends Mapper<LongWritable, Text, Text, Text>{
	private TriplePattern goalPattern;
	private List<Rule> rules;
	public NaiveReasonerMapper(){
		//Input goal
		Node s = Node.createVariable("b");
		Node p = Node.createURI("http://www.w3.org/2004/02/skos/core#broader");
		Node o = Node.createVariable("a");
		this.goalPattern = new TriplePattern(s, p, o);
		this.rules = new JENARuleDAOImpl().parseRulesFromClasspath("rules/naive-basic-reasoner.rules");
	}
	
	
	public NaiveReasonerMapper(TriplePattern goalPattern, List<Rule> rules) {
		super();
		this.goalPattern = goalPattern;
		this.rules = rules;
	}


	@Override
	protected void map(LongWritable key, Text value,
			org.apache.hadoop.mapreduce.Mapper.Context context)
			throws IOException, InterruptedException {
		TriplePattern factPattern = new TriplePattern(TripleUtils.asTriple(value.toString()));	
		if(factPattern.compatibleWith(this.goalPattern)){
			QuerySolution[] result = SPARQLTripleMatch.getSubstitutions(
					this.goalPattern, 
					factPattern);
			for(int i = 0; i<result.length;i++){
				context.write(new Text(this.goalPattern.toString()), 
						new Text(result[i].toString()));	
			}
			
		}
	}



}
