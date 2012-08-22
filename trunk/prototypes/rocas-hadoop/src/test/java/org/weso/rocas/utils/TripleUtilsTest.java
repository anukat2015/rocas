package org.weso.rocas.utils;

import junit.framework.Assert;

import org.junit.Test;

import com.hp.hpl.jena.graph.Triple;

public class TripleUtilsTest {

	@Test
	public void test() {
		String s = "<http://purl.org/weso/pscs/cpv/2008/resource/42141130> <http://www.w3.org/2004/02/skos/core#broader> <http://purl.org/weso/pscs/cpv/2008/resource/42141100> .";
		Triple t = TripleUtils.asTriple(s);
		String s1 = TripleUtils.asString(t);
		Assert.assertEquals(s1, s);
	}

}
