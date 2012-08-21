package org.weso.rocas.experiment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import org.apache.log4j.Logger;
import org.weso.rocas.utils.JENAUnderlyingTriples;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

public class NaiveJENAExperiment {

	protected static Logger logger = Logger.getLogger(NaiveJENAExperiment.class);
	public static void main(String []args) throws FileNotFoundException{
		long loadTimer = 0;
		long loadRules = 0;
		long executeRules = 0;
		//Data
		InputStream stream = new FileInputStream("/home/chema/datasets/cpv-2008/cpv-2008-mini.ttl");		
		//Rules
		String fileRules = "rules/naive-jena-rules.txt";
		//1-Load model
		loadTimer = System.currentTimeMillis();
		Model model = ModelFactory.createDefaultModel();
		model.read(stream, null, "TURTLE");
		logger.info("Loaded "+model.size()+" triples in "+(System.currentTimeMillis()-loadTimer)+" ms.");
		//2-Load rules
		loadRules = System.currentTimeMillis();
		Resource configuration = model.createResource();
		configuration.addProperty(ReasonerVocabulary.PROPruleSet, fileRules);
		configuration.addProperty(ReasonerVocabulary.PROPruleMode, "backward");
		Reasoner reasoner = GenericRuleReasonerFactory.theInstance().create(configuration);
		logger.info("Loading rules: "+(System.currentTimeMillis()-loadRules)+" ms.");
		//3-Execution and infer
		executeRules = System.currentTimeMillis();
		InfModel infmodel = ModelFactory.createInfModel(reasoner, model);
		infmodel.prepare();
		logger.info("Reasoning time: "+(System.currentTimeMillis()-executeRules)+" ms.");
		//4-Process results
		JENAUnderlyingTriples.serializeAsUnderlyingTriples(model).write(System.out,"N3-TRIPLE");
		//FIXME: For all goals: listObjectsOfPropert(extract goal property)
//		NodeIterator results = infmodel.listObjectsOfProperty(model.getProperty("http://www.w3.org/2004/02/skos/core#narrower"));
//		while(results.hasNext()){
//			//logger.info(results.next().asResource().getURI());
//			results.next();
//		}
		//infmodel.write(System.out, "N3");
	}
}
