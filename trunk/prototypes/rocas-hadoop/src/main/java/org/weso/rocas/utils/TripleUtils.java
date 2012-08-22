package org.weso.rocas.utils;

import java.io.StringReader;
import java.io.StringWriter;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;

public class TripleUtils {
	private static final String OUTPUT_TRIPLE_FORMAT = "N3-TRIPLE";
	private static final String INPUT_TRIPLE_FORMAT = "TURTLE";
	public static Triple asTriple(String s){
		Model m = ModelFactory.createDefaultModel();
		m.read(new StringReader(s), null, INPUT_TRIPLE_FORMAT);
		Triple asTriple = m.listStatements().next().asTriple();
		m.close();
		m = null;		
		return asTriple;
	}
	public static String asString(Triple t){
		Model m = ModelFactory.createDefaultModel();
		Resource subject = new ResourceImpl(t.getSubject().getURI());
		Resource object = new ResourceImpl(t.getObject().getURI());
		Property predicate = new PropertyImpl(t.getPredicate().getURI());
		Statement s = new StatementImpl(subject, predicate , object);
		m.add(s);
		StringWriter str = new StringWriter();
		m.write(str,OUTPUT_TRIPLE_FORMAT);
		m.close();
		m = null;
		return str.toString().trim();		
	}
}
