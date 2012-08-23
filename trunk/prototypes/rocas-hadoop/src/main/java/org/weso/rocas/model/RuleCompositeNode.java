package org.weso.rocas.model;

import java.util.LinkedList;
import java.util.List;

import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

public class RuleCompositeNode extends RuleNode{

	List<RuleNode> children;

	public RuleCompositeNode(Rule rule, ClauseEntry clause) {
		super(rule, clause);
	}

	public RuleCompositeNode() {
		// TODO Auto-generated constructor stub
	}

	public List<RuleNode> getChildren() {
		if(children == null){
			this.children = new LinkedList<RuleNode>();
		}
		return children;
	}

	public void setChildren(List<RuleNode> children) {
		this.children = children;
	}
	
	
}
