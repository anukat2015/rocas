package org.weso.rocas.reasoner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.weso.rocas.dao.impl.JENARuleDAOImpl;
import org.weso.rocas.utils.JENAUnderlyingTriples;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.reasoner.rulesys.impl.LPRuleStore;
import com.hp.hpl.jena.util.FileManager;

public class NaiveReasoner {

	public static void main(String []args) throws IOException{
		//Create rulebase
		List<Rule> rules = new JENARuleDAOImpl().parseRulesFromClasspath("rules/naive-skos.rules");
		LPRuleStore rs = new LPRuleStore(rules);
		//Read list of facts
		Model rawModel = FileManager.get().loadModel("facts/naive-skos-facts.ttl");
		//Match rules for facts
		StmtIterator iter = rawModel.listStatements();
		while(iter.hasNext()){
			Statement stm = iter.nextStatement();
			TriplePattern clauseEntry = new TriplePattern(stm.asTriple());
			List<Rule> matchedRules = rs.rulesFor(clauseEntry);
			System.out.println("Triple: "+stm.asTriple());			
			System.out.println("Matched Rules: "+matchedRules);
		}
	}
}
