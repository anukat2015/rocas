package com.hp.hpl.jena.reasoner.rulesys.impl.oldCode;

import java.util.List;

import org.weso.rocas.dao.impl.JENARuleDAOImpl;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.BackwardRuleInfGraphI;
import com.hp.hpl.jena.reasoner.rulesys.LPBackwardRuleInfGraph;
import com.hp.hpl.jena.reasoner.rulesys.LPBackwardRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.reasoner.rulesys.impl.LPRuleStore;

public class MainBRuleEngine {

	public static void main (String []args){
		Graph schema = null;
		List<Rule> rules = getRules();
		Reasoner reasoner = new LPBackwardRuleReasoner(rules);
		LPRuleStore ruleStore = new LPRuleStore(rules);
		Graph data = new LPBackwardRuleInfGraph(reasoner, ruleStore, null, schema);
		BackwardRuleInfGraphI infGraph = new LPBackwardRuleInfGraph(
				reasoner, ruleStore, data, schema); 
		BRuleEngine engine = new BRuleEngine(infGraph);
	
	}

	private static List<Rule> getRules() {
		return new JENARuleDAOImpl().parseRulesFromClasspath("rules/naive-jena.rules");
	}
}
