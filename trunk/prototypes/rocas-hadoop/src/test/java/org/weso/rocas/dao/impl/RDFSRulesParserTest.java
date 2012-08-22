package org.weso.rocas.dao.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.weso.rocas.dao.RuleDAO;

public class RDFSRulesParserTest {

	@Test
	public void testParseRulesFromFile() {
		String file = "src/test/resources/rules/rdfs.rules";
		RuleDAO dao = new JENARuleDAOImpl();
		Assert.assertEquals(13,dao.parseRulesFromFile(file).size());
	}
}
