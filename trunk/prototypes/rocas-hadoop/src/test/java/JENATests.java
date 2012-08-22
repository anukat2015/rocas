import static org.junit.Assert.*;

import java.io.StringReader;

import junit.framework.Assert;


import org.junit.Test;
import org.weso.rocas.utils.TripleUtils;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.TripleMatchFilter;
import com.hp.hpl.jena.graph.query.Bind;
import com.hp.hpl.jena.graph.query.Bound;
import com.hp.hpl.jena.graph.query.Domain;
import com.hp.hpl.jena.graph.query.Element;
import com.hp.hpl.jena.graph.query.Pattern;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;
import com.hp.hpl.jena.reasoner.FinderUtil;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.sparql.syntax.Template;
import com.hp.hpl.jena.sparql.syntax.TemplateTriple;
import com.hp.hpl.jena.util.PrintUtil;


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
	}
//	@Test
//	public void testFilter(){
//		String tripleSource = "<http://purl.org/weso/pscs/cpv/2008/resource/42141130> <http://www.w3.org/2004/02/skos/core#broader> <http://purl.org/weso/pscs/cpv/2008/resource/42141100> .";
//		Triple triple = TripleUtils.asTriple(tripleSource);
//		
//		
//		String tripleFilterSource = "?s <http://www.w3.org/2004/02/skos/core#broader> ?o.";
//		String  [] values = tripleFilterSource.split(" ");
//		Node s = Node.createURI(values[0]);
//		Node p = Node.createURI(values[1].replace("<", "").replace(">", ""));
//		Node o = Node.createURI(values[2]);
//		TriplePattern goal = new TriplePattern(s,p,o);		
//		TripleMatchFilter filter = new TripleMatchFilter(new Triple(s,p,o));
//		TemplateTriple tt = new TemplateTriple(goal.asTriple());
//		
//			
//		Element se = new Bound(0);
//		Element pe =new Bound(1);
//		Element oe = new Bound(2);	
//		Pattern pattern = new Pattern(se, pe, oe);
//		Node[] arg0 = new Node[]{s,p,o};
//		Domain d = new Domain(arg0);
//		TripleMatch match = pattern.asTripleMatch(d);
//		
//		
//		
//		
//		System.out.println(match);
//		System.out.println(triple);
//		
//		
//		System.out.println(pattern.match(d, triple));		
//		System.out.println(tt.equals(triple));
//		System.out.println(goal.compatibleWith(new TriplePattern(triple)));
//		System.out.println(filter.accept(triple));
//		
//		Model m = ModelFactory.createDefaultModel();
//		//m.add(new StatementImpl(triple.getSubject(),triple.getPredicate(),triple.getObject()));
//		
//	
//	}

}
