import static org.junit.Assert.*;

import java.io.StringReader;

import junit.framework.Assert;

import org.junit.Test;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;


public class JENATests {

	@Test
	public void testReadTriple() {
		String triple = "<http://purl.org/weso/pscs/cpv/2008/resource/42141130> <http://www.w3.org/2004/02/skos/core#broader> <http://purl.org/weso/pscs/cpv/2008/resource/42141100> .";
		Model m = ModelFactory.createDefaultModel();
		m.read(new StringReader(triple), null, "TURTLE");
		Assert.assertEquals(1, m.size());
		Triple t = m.listStatements().next().asTriple();
		m.close();
		m=null;
		System.out.println(t);
	
	}

}
