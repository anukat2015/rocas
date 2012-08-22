package org.weso.rocas.utils;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.* ;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class SPARQLTripleMatch{


	public static boolean accept(Model model, String pattern) {
		String queryString = "ASK { "+pattern+" }";
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
		try{
			return qexec.execAsk();	
		}finally {qexec.close();}
	}

	public static boolean accept(String triple, String pattern) {
		Model model = TripleUtils.asModel(triple);
		String queryString = "ASK { "+pattern+" }";
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
		try{
			return qexec.execAsk();	
		}finally {qexec.close(); model.close(); model = null;}
	}


}
