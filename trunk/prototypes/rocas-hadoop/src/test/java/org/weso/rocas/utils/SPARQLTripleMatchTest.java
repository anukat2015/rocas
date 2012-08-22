package org.weso.rocas.utils;

import junit.framework.Assert;

import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;

public class SPARQLTripleMatchTest {

	@Test
	public void testAccept() {
		String tripleSource = "<http://purl.org/weso/pscs/cpv/2008/resource/42141130> <http://www.w3.org/2004/02/skos/core#broader> <http://purl.org/weso/pscs/cpv/2008/resource/42141100> .";
		String tripleFilterSource = "?s <http://www.w3.org/2004/02/skos/core#broader> ?o.";
		Model m = TripleUtils.asModel(tripleSource);
		Assert.assertEquals(true, SPARQLTripleMatch.accept(m, tripleFilterSource));
		Assert.assertEquals(true, SPARQLTripleMatch.accept(tripleSource, tripleFilterSource));
	}

}
