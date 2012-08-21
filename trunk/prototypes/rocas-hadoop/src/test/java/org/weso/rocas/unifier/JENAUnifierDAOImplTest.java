package org.weso.rocas.unifier;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.weso.rocas.dao.RuleDAO;
import org.weso.rocas.dao.impl.JENARuleDAOImpl;
import org.weso.rocas.dao.impl.JENARuleDAOImplTest;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.reasoner.rulesys.impl.RuleStore;

public class JENAUnifierDAOImplTest {

	@Test
	public void testMatch() {
		String rule = "[rule1: (?a <http://example.org#p> ?b) (?b <http://example.org#p> ?c) -> (?a <http://example.org#p> ?c)]";
		RuleDAO dao = new JENARuleDAOImpl();
		List<Rule> rules = dao.parseRulesFromString(rule);
		RuleStore ruleStore = new RuleStore(rules);
		UnifierDAO unifier = new JENAUnifierDAOImpl(ruleStore);
		Node s = Node.createURI("?a");
		Node p = Node.createURI("http://example.org#p");
		Node o = Node.createURI("?c");
		Triple match = new Triple(s, p, o);
		TriplePattern pattern = new TriplePattern(match);
		Assert.assertEquals(1,unifier.match(pattern).size());	
	}

}
