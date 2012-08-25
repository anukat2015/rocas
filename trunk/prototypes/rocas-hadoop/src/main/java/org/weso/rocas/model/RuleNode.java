package org.weso.rocas.model;

import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

public abstract class RuleNode {
	protected Rule rule;
	protected ClauseEntry clause;
	private String name;
	
	public ClauseEntry getClause() {
		return clause;
	}

	public void setClause(ClauseEntry clause) {
		this.clause = clause;
	}

	public RuleNode() {
		super();
	}

	public RuleNode(Rule rule, ClauseEntry clause) {
		super();
		this.rule = rule;
		this.clause = clause;
	}

	public RuleNode(Rule rule, ClauseEntry clauseEntry, String name) {
		this.rule = rule;
		this.clause = clauseEntry;
		this.name = name;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	
}
