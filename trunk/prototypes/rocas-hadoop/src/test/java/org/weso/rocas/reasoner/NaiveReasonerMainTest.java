package org.weso.rocas.reasoner;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.weso.rocas.dao.impl.JENARuleDAOImpl;
import org.weso.rocas.utils.SPARQLTripleMatch;

import com.hp.hpl.jena.graph.TripleMatchFilter;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.reasoner.rulesys.impl.LPRuleStore;
import com.hp.hpl.jena.reasoner.rulesys.impl.TripleMatchFrame;
import com.hp.hpl.jena.util.FileManager;

public class NaiveReasonerMainTest {

	@Test
	public void testOneGoalInFacts() {
		//1-Create rulebase
		List<Rule> rules = new JENARuleDAOImpl().parseRulesFromClasspath("rules/naive-skos.rules");
		LPRuleStore rs = new LPRuleStore(rules);				
		//2-Read list of facts
		Model rawModel = FileManager.get().loadModel("facts/naive-skos-facts.ttl");
		//3-Choose a random goal
		ClauseEntry clauseEntry = rules.get(0).getHead()[0];
		TriplePattern goal = (TriplePattern) clauseEntry;
		StmtIterator iter = rawModel.listStatements();
		while(iter.hasNext()){
			Statement stm = iter.nextStatement();
			if(goal.compatibleWith(new TriplePattern(stm.asTriple()))){
				QuerySolution[] result = SPARQLTripleMatch.getSubstitutions(goal, stm);
				assertEquals(1, result.length);
//				for(int i = 0; i<result.length;i++){
//					System.out.println(result[i]);
//				}
			}
		}
	}

}
