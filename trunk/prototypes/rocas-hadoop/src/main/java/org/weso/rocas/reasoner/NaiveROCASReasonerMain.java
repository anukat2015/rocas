package org.weso.rocas.reasoner;

import java.util.List;

import org.weso.rocas.dao.impl.JENARuleDAOImpl;
import org.weso.rocas.model.GoalFactory;
import org.weso.rocas.model.TripleGoalFactory;
import org.weso.rocas.utils.SPARQLTripleMatch;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.reasoner.rulesys.impl.LPRuleStore;
import com.hp.hpl.jena.util.FileManager;

public class NaiveROCASReasonerMain {

	public static void main(String []args){
		//Input goal
		Node s = Node.createVariable("b");
		Node p = Node.createURI("http://www.w3.org/2004/02/skos/core#broader");
		Node o = Node.createVariable("a");
		TriplePattern goalPattern = new TriplePattern(s, p, o);
		GoalFactory goalFactory = new TripleGoalFactory();
		goalFactory.addGoal(goalPattern);
		//Common and static parts
		List<Rule> rules = new JENARuleDAOImpl().parseRulesFromClasspath("rules/naive-basic-reasoner.rules");
		LPRuleStore rs = new LPRuleStore(rules);
		//First M/R: see if the goal matches facts
		Model rawModel = FileManager.get().loadModel("facts/naive-basic-reasoner-skos-facts.ttl");
		//Dynamic parts (file to be processed) in map/reduce jobs
		//Map execution
		checkGoalsAndFacts(goalFactory, rawModel);
		//Reduce execution
		//if not add new goals and repeat
		List<Rule> matchedRules = rs.rulesFor(goalPattern);
		for(Rule rule: matchedRules){
			System.out.println("Matched rule "+rule);
			//RuleNode derivationTree = RuleNode.extractCollectClauses(rs,rule,goalPattern,0);
			for(ClauseEntry newGoal:rule.getBody()){
				System.out.println("Adding new goal "+newGoal);
				goalFactory.addGoal((TriplePattern) newGoal);
			}
		}
		checkGoalsAndFacts(goalFactory, rawModel);

		
	}

	private static void checkGoalsAndFacts(GoalFactory goalFactory,
			Model rawModel) {
		StmtIterator iter = rawModel.listStatements();
		while(iter.hasNext()){
			Statement stm = iter.nextStatement();
			TriplePattern factPattern = new TriplePattern(stm.asTriple());			
			for(TriplePattern goal:goalFactory.listGoals()){
				if(factPattern.compatibleWith(goal)){
					System.out.println("Compatible");
					QuerySolution[] result = SPARQLTripleMatch.getSubstitutions(goal, stm);
				}
			}
		}
	}
}