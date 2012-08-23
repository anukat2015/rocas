package org.weso.rocas.walker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.weso.rocas.dao.impl.JENARuleDAOImpl;
import org.weso.rocas.model.RuleCompositeNode;
import org.weso.rocas.model.RuleNode;
import org.weso.rocas.model.RuleNodeLeaf;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.reasoner.rulesys.impl.LPRuleStore;

public class RulePrintTreeVisitor extends RuleVisitor{
	
	public static void print(Object o){
		System.out.print(o);
	}
	
	private void show(Object o){
		if(o == null){
			System.out.println("[NULL]");	
		}else{
			System.out.println("Class: "+o.getClass()+" ");
		}
		
	}
	
	public Object visit(RuleCompositeNode node, int level) throws Exception{
		createBlanks (level);
		print(node.getClause().toString()+" ("+node.getChildren().size()+")\n");		
		for(RuleNode child: node.getChildren()){
			if(child instanceof RuleCompositeNode){
				this.visit((RuleCompositeNode) child, level+1);
			}else if (child instanceof RuleNodeLeaf){
				this.visit((RuleNodeLeaf) child, level+1);
			}else{
				this.visit(child, level+1);
			}
			print("\n");
		}
		return null;		
	}
	
	
	public Object visit(RuleNode node, int level) throws Exception{
		createBlanks (level);
		print(node.getClause().toString()+" from rule "+node.getRule());
		return null;		
	}
	
	public Object visit(RuleNodeLeaf leaf, int level) throws Exception{
		return visit((RuleNode) leaf, level);		
	}
	

	private void createBlanks(int level) {
		for(int i = 0; i<level; i++){
			System.out.print(" ");
		}
		
	}

	

	public static void main(String []args) throws Exception{
		List<Rule> rules = new JENARuleDAOImpl().parseRulesFromClasspath("rules/naive-jena.rules");
		System.out.println("Loaded "+rules.size());
		LPRuleStore rs = new LPRuleStore(rules);
		//Create derivation tree of the first rule		
		Rule rule = rules.get(0);
		
		RuleCompositeNode root = new RuleCompositeNode(rule, rule.getHeadElement(0));
		//extractClauses(root, rs, rule.getHeadElement(0));
		root.getChildren().addAll(extractCollectClauses(rs,  rule.getHeadElement(0)));

		RulePrintTreeVisitor visitor = new RulePrintTreeVisitor();
		visitor.visit(root,0);
	}

	private static void extractClausesSecondaryEffects(RuleNode parent, LPRuleStore rs, ClauseEntry clauseEntry) {
		List<Rule> children = rs.rulesFor((TriplePattern) clauseEntry);
		for(Rule childRule:children){
			for(int i = 0; i<childRule.getBody().length;i++){
				RuleNode child = new RuleCompositeNode(childRule, childRule.getBodyElement(i));
				extractClausesSecondaryEffects(child, rs, child.getClause());
				((RuleCompositeNode) parent).getChildren().add(child);
			}			
		}		
	}
	
	private static List<RuleNode> extractCollectClauses(LPRuleStore rs, ClauseEntry clauseEntry) {
		List<Rule> children = rs.rulesFor((TriplePattern) clauseEntry);	
		List<RuleNode> childrenNodes = new LinkedList<RuleNode>();
		for(Rule childRule:children){
			List<RuleNode> currentChildrenNodes = new LinkedList<RuleNode>();
			for(int i = 0; i<childRule.getBody().length;i++){		
				RuleNode child = new RuleCompositeNode(childRule, childRule.getBodyElement(i));
				((RuleCompositeNode) child).getChildren().addAll(
						extractCollectClauses(rs, child.getClause()));
				currentChildrenNodes.add(child);
			}	
			childrenNodes.addAll(currentChildrenNodes);
		}
		return childrenNodes;
	}
	
//	public static void showGoals(LPRuleStore rs, TriplePattern goal, int level, Map<Rule, Boolean> printed){
//		System.out.println(goal);
//		List<Rule> rules = rs.rulesFor(goal);
//		for(Rule rule: rules){
//			if(!printed.containsKey(rule) || !printed.get(rule)){
//				printed.put(rule, Boolean.TRUE);
//				for(int i = 0; i<level ; i++){
//					System.out.print(" ");
//				}
//				System.out.println(rules.size()+" "+level+"  "+rule);			
//				showGoals(rs, (TriplePattern) rule.getHeadElement(0), level+1, printed);				
//			}			
//		}
//	
//	}
	
}
