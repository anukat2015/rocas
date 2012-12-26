package org.weso.rocas.model;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.weso.rocas.dao.impl.JENARuleDAOImpl;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.reasoner.rulesys.impl.LPRuleStore;

public class RuleNodeTest {

	@Test
	public void test() throws Exception {
		List<Rule> rules = new JENARuleDAOImpl().parseRulesFromClasspath("rules/naive-jena-test-one-rule.rules");
		LPRuleStore rs = new LPRuleStore(rules);
		Rule rule = rules.get(0);

		RuleNode root =	RuleNode.extractCollectClauses(rs, rule, rule.getHeadElement(0), 0);
		assertEquals(1, root.getRule().getBody().length);
		assertEquals(1, root.getRule().getHead().length);
	}

	@Test
	public void testMultipleBody() throws Exception {
		List<Rule> rules = new JENARuleDAOImpl().parseRulesFromClasspath("rules/naive-jena-test-multiple-body.rules");
		LPRuleStore rs = new LPRuleStore(rules);
		//Create derivation tree of the first rule		
		Rule rule = rules.get(0);
		RuleNode root =	RuleNode.extractCollectClauses(rs, rule, rule.getHeadElement(0), 0);
		assertEquals(3, root.getRule().getBody().length);
		assertEquals(1, root.getRule().getHead().length);
	}
	
	@Test
	public void testCreateDerivationTree() throws Exception {
		List<Rule> rules = new JENARuleDAOImpl().parseRulesFromClasspath("rules/naive-jena-test-derivation-tree.rules");
		LPRuleStore rs = new LPRuleStore(rules);
		Node s = Node.createURI("?b");
		Node p = Node.createURI("http://www.w3.org/2004/02/skos/core#narrower");
		Node o = Node.createURI("?a");
		Triple match = new Triple(s, p, o);
		TriplePattern clauseEntry = new TriplePattern(match);
		List<Rule> rulesFor = rs.rulesFor((TriplePattern) clauseEntry );
		assertEquals(2, rulesFor.size());
		for(Rule rule:rulesFor){
			RuleNode derivationTree =RuleNode.extractCollectClauses(rs, rule, clauseEntry, 0);	
		}	
		
	}
	@Test
	public void testCreateDerivationTreeRDFS() throws Exception {
		List<Rule> rules = new JENARuleDAOImpl().parseRulesFromClasspath("rules/rdfs.rules");
		LPRuleStore rs = new LPRuleStore(rules);
		for(Rule rule:rs.getAllRules()){
			List<Rule> rulesFor = rs.rulesFor((TriplePattern) rule.getHeadElement(0) );
			assertEquals(1, rulesFor.size());				
		}
		
	}
	@Test
	public void testCreateDerivationTreeOWLRL() throws Exception {
		List<Rule> rules = new JENARuleDAOImpl().parseRulesFromClasspath("rules/owl-rl.rules");
		checkRules(rules);
		
	}
	
	@Test
	public void testCreateDerivationTreeOWLHorst() throws Exception {
		List<Rule> rules = new JENARuleDAOImpl().parseRulesFromClasspath("rules/owl-horst.rules");
		checkRules(rules);
		
	}

	private void checkRules(List<Rule> rules) {
		LPRuleStore rs = new LPRuleStore(rules);
		for(Rule rule:rs.getAllRules()){
			ClauseEntry clauseEntry = rule.getHeadElement(0) ;
			List<Rule> rulesFor = rs.rulesFor((TriplePattern) clauseEntry );
			assertEquals(1, rulesFor.size());				
			RuleNode derivationTree =RuleNode.extractCollectClauses(rs, rule, clauseEntry, 0);		
		}
	}
	
}
