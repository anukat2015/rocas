package com.hp.hpl.jena.reasoner.rulesys.impl.oldCode.inherited;

import java.util.List;

import org.weso.rocas.dao.impl.JENARuleDAOImpl;
import org.weso.rocas.utils.TripleUtils;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import com.hp.hpl.jena.reasoner.rulesys.LPBackwardRuleInfGraph;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.reasoner.rulesys.impl.LPRuleStore;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

public class MainBRuleEngine {

	public static void main (String []args){

		List<Rule> rules = getRules();
		
		Model rawModel = FileManager.get().loadModel("file:model/cpv-2008-mini-ut.ttl");
//		System.out.println("High-level implementation");
//		Model configurationModel = ModelFactory.createDefaultModel();
//		Resource configuration = configurationModel.createResource();
//		configuration.addProperty(ReasonerVocabulary.PROPruleSet, "rules/naive-jena.rules");
//		configuration.addProperty(ReasonerVocabulary.PROPruleMode, "backward");
//		
//		
//		Reasoner reasoner = GenericRuleReasonerFactory.theInstance().create(configuration);
//		InfModel infModel = ModelFactory.createInfModel(reasoner, rawModel);
//		infModel.write(System.out,TripleUtils.OUTPUT_TRIPLE_FORMAT);
//		
//		System.out.println("Mid-level implementation");
		Reasoner myReasoner = new com.hp.hpl.jena.reasoner.rulesys.impl.oldCode.inherited.LPBackwardRuleReasoner(rules);
//		InfModel myInfModel = ModelFactory.createInfModel(myReasoner, rawModel);
//		myInfModel.write(System.out,TripleUtils.OUTPUT_TRIPLE_FORMAT);
//		
//	
		System.out.println("Low-level implementation");
		LPRuleStore ruleStore = new LPRuleStore(rules);
		com.hp.hpl.jena.reasoner.rulesys.impl.oldCode.inherited.LPBackwardRuleInfGraph graph = 
				new com.hp.hpl.jena.reasoner.rulesys.impl.oldCode.inherited.LPBackwardRuleInfGraph
					(myReasoner, ruleStore, rawModel.getGraph(), null);
		InfModel lowModel = ModelFactory.createInfModel(graph);
		lowModel.write(System.out,TripleUtils.OUTPUT_TRIPLE_FORMAT);

		
		
	}

	private static List<Rule> getRules() {
		return new JENARuleDAOImpl().parseRulesFromClasspath("rules/naive-jena.rules");
	}
	private static Model getDataModel() {
		Model model = ModelFactory.createDefaultModel();
		model.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("model/cpv-2008-mini-ut.ttl"), 
				TripleUtils.INPUT_TRIPLE_FORMAT);
		return model;
	}
}
