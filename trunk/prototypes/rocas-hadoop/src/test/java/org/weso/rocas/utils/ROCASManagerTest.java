package org.weso.rocas.utils;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.weso.rocas.dao.impl.JENARuleDAOImpl;
import org.weso.to.GoalTO;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.reasoner.rulesys.impl.StateFlag;

public class ROCASManagerTest {

	@Test
	public void testAllGoals() {
		//1-Create rulebase
		ROCASManager manager = new ROCASManager(new JENARuleDAOImpl().parseRulesFromClasspath("rules/naive-skos-goals-test.rules"));
		assertEquals(6, manager.listAllGoals().size());
//		manager = new ROCASManager(new JENARuleDAOImpl().parseRulesFromClasspath("rules/owl-horst.rules"));
//		assertEquals(2, manager.listAllGoals().size());
//		manager = new ROCASManager(new JENARuleDAOImpl().parseRulesFromClasspath("rules/owl-rl.rules"));
//		assertEquals(0, manager.listAllGoals().size());		
		manager = new ROCASManager(new JENARuleDAOImpl().parseRulesFromClasspath("rules/rdfs.rules"));
		assertEquals(34, manager.listAllGoals().size());	
		
	}
	
	@Test
	public void testInitialGoal() {
		Node s = Node.createVariable("a");
		Node p = Node.createURI("http://www.w3.org/2004/02/skos/core#broader");
		Node o = Node.createVariable("b");
		Triple match = new Triple(s, p, o);
		TriplePattern pattern = new TriplePattern(match);
		//1-Create rulebase
		GoalTO goal = new GoalTO(pattern, "", StateFlag.ACTIVE);
		ROCASManager manager = new ROCASManager(new JENARuleDAOImpl().
				parseRulesFromClasspath("rules/naive-skos-goals-test.rules"),goal);
		assertEquals(6, manager.listAllGoals().size());
		manager = new ROCASManager(new JENARuleDAOImpl().parseRulesFromClasspath("rules/rdfs.rules"));
		assertEquals(34, manager.listAllGoals().size());	
		
		
	}
	@Test
	public void testInitialGoalRDFS() {
		Node s = Node.createVariable("s");
		Node p = Node.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		Node o = Node.createVariable("d");
		Triple match = new Triple(s, p, o);
		TriplePattern pattern = new TriplePattern(match);
		//1-Create rulebase
		GoalTO goal = new GoalTO(pattern, "", StateFlag.ACTIVE);
		ROCASManager manager = 
				new ROCASManager(new JENARuleDAOImpl().
						parseRulesFromClasspath("rules/rdfs.rules"),goal);
		assertEquals(20, manager.listAllGoals().size());	
		
		
	}
}
