package org.weso.rocas.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.weso.rocas.dao.RuleDAO;

import com.hp.hpl.jena.reasoner.rulesys.Rule;

public class JENARuleDAOImplTest {

	@Test
	public void testParseRulesFromString() {
		String rule = "[rule1: (?a eg:p ?b) (?b eg:p ?c) -> (?a eg:p ?c)]";
		RuleDAO dao = new JENARuleDAOImpl();
		Assert.assertEquals(1,dao.parseRulesFromString(rule).size());
	}

	@Test
	public void testParseRulesFromFile() {
		String file = "src/test/resources/org/weso/rocas/dao/impl/rules-test.txt";
		RuleDAO dao = new JENARuleDAOImpl();
		Assert.assertEquals(2,dao.parseRulesFromFile(file).size());
	}

	@Test
	public void testParseRulesFromClassPath() {
		String file = "org/weso/rocas/dao/impl/rules-test.txt";
		RuleDAO dao = new JENARuleDAOImpl();
		Assert.assertEquals(2,dao.parseRulesFromClasspath(file).size());
	}

	@Test
	public void testParseRulesFromURL() {
		String url = "/home/chema/projects/weso/rocas/trunk/prototypes/rocas-hadoop/src/test/resources/org/weso/rocas/dao/impl/rules-test.txt";
		RuleDAO dao = new JENARuleDAOImpl();
		Assert.assertEquals(2,dao.parseRulesFromFile(url).size());
	}
	

	
}
