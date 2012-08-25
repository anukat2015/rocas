package org.weso.rocas.walker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.weso.rocas.dao.impl.JENARuleDAOImpl;
import org.weso.rocas.model.RuleANDNode;
import org.weso.rocas.model.RuleCompositeNode;
import org.weso.rocas.model.RuleNode;
import org.weso.rocas.model.RuleNodeLeaf;
import org.weso.rocas.model.RuleORNode;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.reasoner.rulesys.impl.LPRuleStore;
import com.hp.hpl.jena.reasoner.rulesys.impl.oldCode.inherited.RuleInstance;

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


	public Object visit(RuleNode node, int level) throws Exception{
		if (node instanceof RuleORNode){
			this.visit((RuleORNode) node, level+1);
		}else if (node instanceof RuleANDNode){
			this.visit((RuleANDNode) node, level+1);
		} else if(node instanceof RuleCompositeNode){
			this.visit((RuleCompositeNode) node, level+1);
		}else if (node instanceof RuleNodeLeaf){
			this.visit((RuleNodeLeaf) node, level+1);
		}

		return null;		
	}

	public Object visit(RuleCompositeNode node, int level) throws Exception{
		for(RuleNode child: node.getChildren()){
			System.out.print(" ");
			this.visit(child, level+1);
			System.out.print(", ");
		}
		return null;		
	}


	public Object visit(RuleORNode orNode, int level) throws Exception{
		System.out.print("\n");
		createBlanks (level);
		System.out.print("OR (");
		this.visit((RuleCompositeNode)orNode, level);
		System.out.print(").");
		System.out.print("\n");
		createBlanks (level);
		
		return null;
	}

	public Object visit(RuleANDNode andNode, int level) throws Exception{
		System.out.print("\n");
		createBlanks (level);
		System.out.print("AND (");
		this.visit((RuleCompositeNode)andNode, level);
		System.out.print(").");
		System.out.print("\n");
		createBlanks (level);
		
		return null;	
	}

	public Object visit(RuleNodeLeaf leaf, int level) throws Exception{
		print(leaf.getName()+"-["+leaf.getClause()+"]");
		return null;
	}


	public static void createBlanks(int level) {
		for(int i = 0; i<level; i++){
			System.out.print(" ");
		}
	}


	public static String createBlanks2(int level) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i<level; i++){
			sb.append("\t");
		}
		return sb.toString();
	}





	public static void main(String []args) throws Exception{
		List<Rule> rules = new JENARuleDAOImpl().parseRulesFromClasspath("rules/naive-jena.rules");
		System.out.println("Loaded "+rules.size());
		LPRuleStore rs = new LPRuleStore(rules);
		//Create derivation tree of the first rule		
		Rule rule = rules.get(0);

		RuleNode root =	extractCollectClauses(rs, rule, rule.getHeadElement(0), 0);
		RulePrintTreeVisitor visitor = new RulePrintTreeVisitor();
		visitor.visit(root,0);
	}

	private static int getCount(){
		return count++;
	}

	private static int count = 0;
	
	private static RuleNode extractCollectClauses(LPRuleStore rs, Rule rule, ClauseEntry clauseEntry, int level) {
		RuleNode parent = null;
		List<Rule> rulesFor = rs.rulesFor((TriplePattern) clauseEntry);
		if(rulesFor.size()==0){
			parent = new RuleNodeLeaf(rule, clauseEntry, "l"+getCount());
		}else {		
			parent = new RuleORNode(rule,clauseEntry, "o"+getCount());
			for(Rule childRule:rulesFor){
				RuleNode child = new RuleANDNode(childRule, clauseEntry,"a"+getCount());
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
