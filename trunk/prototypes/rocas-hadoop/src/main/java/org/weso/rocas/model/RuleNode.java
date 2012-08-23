package org.weso.rocas.model;

import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

public class RuleNode {
	private Rule rule;
	private ClauseEntry clause;
	
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

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clause == null) ? 0 : clause.hashCode());
		result = prime * result + ((rule == null) ? 0 : rule.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuleNode other = (RuleNode) obj;
		if (clause == null) {
			if (other.clause != null)
				return false;
		} else if (!clause.equals(other.clause))
			return false;
		if (rule == null) {
			if (other.rule != null)
				return false;
		} else if (!rule.equals(other.rule))
			return false;
		return true;
	}
	

	
}
