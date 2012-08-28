package org.weso.rocas.model;

import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

public class RuleORNode extends RuleCompositeNode {

	public RuleORNode(Rule rule, ClauseEntry clause, String name) {
		super(rule,clause,name);
	}

	public RuleORNode() {
		// TODO Auto-generated constructor stub
	}

}
