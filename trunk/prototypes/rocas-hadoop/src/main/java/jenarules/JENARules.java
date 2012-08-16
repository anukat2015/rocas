package jenarules;

import java.util.List;

import org.apache.commons.math.optimization.GoalType;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.BackwardRuleInfGraphI;
import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import com.hp.hpl.jena.reasoner.rulesys.LPBackwardRuleInfGraph;
import com.hp.hpl.jena.reasoner.rulesys.LPBackwardRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.reasoner.rulesys.impl.LPBRuleEngine;
import com.hp.hpl.jena.reasoner.rulesys.impl.LPRuleStore;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.PrintUtil;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;


public class JENARules {
	public static void main(String[] args) {
		Model rawModel = FileManager.get().loadModel("file:data.n3");
	   // String rules = "[rule1: (?a eg:p ?b) (?b eg:p ?c) -> (?a eg:p ?c)]";
	    InfModel infmodel = ModelFactory.createRDFSModel(rawModel);
	    Model postRuleModel = processRules("rules.txt", infmodel);
	    Resource marla = infmodel.getResource("http://www.josemalvarez.es/#A");
	    printStatements(postRuleModel, marla, null, null);
//	    
//	    Rule rules = Rule.parseRule("[rule1: (?a eg:p ?b) (?b eg:p ?c) -> (?a eg:p ?c)]");
//	    ClauseEntry[] head = rules.getHead();
//		ClauseEntry[] body = rules.getBody();
//		for(int i = 0; i< head.length;i++){
//			System.out.println(rules.getHeadElement(i));
//		}
//		for(int i = 0; i< body.length;i++){
//			System.out.println(rules.getBodyElement(i));
//		}
		
	
	}


	public static Model processRules(String fileloc, InfModel modelIn) {
		// create a simple model; create a resource and add rules from a file
		Model m = ModelFactory.createDefaultModel();
		Resource configuration = m.createResource();
		configuration.addProperty(ReasonerVocabulary.PROPruleSet, fileloc);
		configuration.addProperty(ReasonerVocabulary.PROPruleMode, "backward");
		// Create an instance of a reasoner
		Reasoner reasoner = GenericRuleReasonerFactory.theInstance().create(configuration);
		
		LPBackwardRuleReasoner r = new LPBackwardRuleReasoner(null); 
		
		// Now with the rawdata model & the reasoner, create an InfModel
		InfModel infmodel = ModelFactory.createInfModel(reasoner, modelIn);
		System.out.println("end reasoning");
		// use the following to show everything that can be deduced based on the rules
//		infmodel.setNsPrefix("eg", "http://www.josemalvarez.es/#");
//		infmodel.write(System.out, "N3");
		
		BackwardRuleInfGraphI infGraph = null;
		LPBRuleEngine engine = new LPBRuleEngine(infGraph);
		List<Rule> rules = Rule.parseRules("[rule1: (?a eg:p ?b) (?b eg:p ?c) -> (?a eg:p ?c)]");
		LPRuleStore rs = new LPRuleStore(rules);
		LPBackwardRuleInfGraph brig = new LPBackwardRuleInfGraph(r,rs,null,null);
		return infmodel;
	}
	

	public static void printStatements(Model m, Resource s, Property p, Resource o) {
	PrintUtil.registerPrefix("eg", "http://www.josemalvarez.es/#");
	for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
		Statement stmt = i.nextStatement();
		System.out.println(" - " + PrintUtil.print(stmt));
	}
}
}
