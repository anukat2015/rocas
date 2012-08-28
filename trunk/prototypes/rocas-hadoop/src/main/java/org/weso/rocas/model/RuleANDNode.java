package org.weso.rocas.model;

import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

public class RuleANDNode extends RuleCompositeNode {

	public RuleANDNode(Rule rule, ClauseEntry element) {
		super(rule,element);
	}

	public RuleANDNode(Rule rule, ClauseEntry clauseEntry, String name) {
		super(rule,clauseEntry,name);
	}

	public RuleANDNode() {
		// TODO Auto-generated constructor stub
	}

}
