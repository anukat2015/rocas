package org.weso.rocas.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.weso.rocas.dao.impl.JENARuleDAOImpl;
import org.weso.rocas.utils.SPARQLTripleMatch;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.util.FileManager;

public class GoalFactoryTest {

	@Test
	public void test() {
		//1-Create rulebase
		List<Rule> rules = new JENARuleDAOImpl().parseRulesFromClasspath("rules/naive-skos.rules");
		//2-Read list of facts
		Model rawModel = FileManager.get().loadModel("facts/naive-skos-facts.ttl");
		//3-Choose a random goal
		ClauseEntry clauseEntry = rules.get(0).getHead()[0];
		TriplePattern goal = (TriplePattern) clauseEntry;
		//4-Save goals
		GoalFactory factory = new TripleGoalFactory();
		factory.addGoal(goal);
		assertEquals(1, factory.listGoals().size());
		assertEquals(0, factory.getMatchesByGoal(goal).size());
		
		StmtIterator iter = rawModel.listStatements();
		while(iter.hasNext()){
			Statement stm = iter.nextStatement();
			TriplePattern fact = new TriplePattern(stm.asTriple());
			if(goal.compatibleWith(fact)){
				factory.addGoalMatch(goal, fact);		
			}
		}
		assertEquals(3, factory.getMatchesByGoal(goal).size());
		
				
	}

}
