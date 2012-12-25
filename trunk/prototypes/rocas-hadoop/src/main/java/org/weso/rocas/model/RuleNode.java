package org.weso.rocas.model;

import java.util.List;

import org.weso.rocas.walker.RulePrintTreeVisitor;

import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.reasoner.rulesys.impl.LPRuleStore;

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

	public static RuleNode extractCollectClauses(LPRuleStore rs, Rule rule, ClauseEntry clauseEntry, int level) {
		RuleNode parent = null;
		List<Rule> rulesFor = rs.rulesFor((TriplePattern) clauseEntry);
		if(rulesFor.size()==0){
			parent = new RuleNodeLeaf(rule, clauseEntry, "l"+RulePrintTreeVisitor.getCount());
		}else {		
			parent = new RuleORNode(rule,clauseEntry, "o"+RulePrintTreeVisitor.getCount());
			for(Rule childRule:rulesFor){
				RuleNode child = new RuleANDNode(childRule, clauseEntry,"a"+RulePrintTreeVisitor.getCount());
				for(int i = 0; i<childRule.getBody().length;i++){
					RuleNode andClause = extractCollectClauses(rs, 
											childRule, 
											childRule.getBodyElement(i), 
											level+1);
					((RuleCompositeNode) child).getChildren().add(andClause);
				}
				((RuleCompositeNode) parent).getChildren().add(child);
			}
		}	
		return parent;
	}


	
}
