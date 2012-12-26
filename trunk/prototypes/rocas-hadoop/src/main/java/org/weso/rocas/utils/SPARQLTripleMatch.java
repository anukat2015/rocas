package org.weso.rocas.utils;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.* ;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.reasoner.TriplePattern;

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


	public static QuerySolution[] getSubstitutions(TriplePattern goal, Statement fact) {
		Model model = ModelFactory.createDefaultModel();
		model.add(fact);
		String queryString = "SELECT * WHERE { "+
		formatNode(goal.getSubject())+" "+formatNode(goal.getPredicate())+" "+formatNode(goal.getObject())+
		" }";
		return SPARQLUtils.executeSimpleSparql(model, queryString);
	}

	public static String formatNode(Node node) {
		if(node.isURI()){
			return "<"+node.getURI()+">";
		} if (node.isVariable()){	
			//FIXME: hack. In rules preload ? but when manually create a node not!
			if(node.getName().startsWith("?")){
				return node.getName();	
			}else return "?"+node.getName();			
		}else	return node.toString();
		
	}
	
	
}
