package org.weso.rocas.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.weso.to.GoalTO;

import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.reasoner.rulesys.impl.LPRuleStore;
import com.hp.hpl.jena.reasoner.rulesys.impl.StateFlag;

public class ROCASManager {

	Map<GoalTO,List<TriplePattern>> goals ;
	private List<Rule> rules;
	private LPRuleStore rs;
	private Map<String,Rule> tableRules;
	private GoalTO initialGoal = null;
	
	public ROCASManager(List<Rule> rules){
		this.goals = new HashMap<GoalTO, List<TriplePattern>>();
		this.rules = rules;
		this.tableRules = new HashMap<String, Rule>();
		for(Rule rule:this.rules){
			this.tableRules.put(rule.getName(), rule);
		}
		this.rs = new LPRuleStore(rules);
		createGoalsTable();
	}
	
	public ROCASManager(List<Rule> rules, GoalTO initialGoal){
		this.goals = new HashMap<GoalTO, List<TriplePattern>>();
		this.rules = rules;
		this.rs = new LPRuleStore(rules);
		this.initialGoal = initialGoal;
		createGoalsTable(initialGoal);
	}
	
	private void createGoalsTable(GoalTO initialGoal) {
		if(!this.goals.containsKey(initialGoal)){		
			List<Rule> goalRules = this.rs.rulesFor(initialGoal.getGoal());
			for(Rule rule:goalRules){
				this.addGoals(rule.getHead(),rule.getName());
				ClauseEntry[] body = rule.getBody();
				this.addGoals(body,rule.getName());
				for(int i = 0; i<body.length;i++){
					GoalTO goal = new GoalTO((TriplePattern)body[i], rule.getName(), StateFlag.ACTIVE);
					this.createGoalsTable(goal);
				}
			}
		}
	}

	private void createGoalsTable() {
		for(Rule rule:this.rules){
			if(initialGoal == null){
				this.initialGoal = 
						new GoalTO((TriplePattern)rule.getHead()[0], rule.getName(), 
								StateFlag.ACTIVE);
			}
			this.addGoals(rule.getHead(),rule.getName());
			this.addGoals (rule.getBody(),rule.getName());			
		}
	}
	
	private void addGoals(ClauseEntry[] clauses, String idRule){
		for(int i = 0;i<clauses.length;i++){
			GoalTO goal = new GoalTO((TriplePattern)clauses[i], idRule, StateFlag.ACTIVE);
			this.goals.put(goal, new LinkedList<TriplePattern>());
		}
	}

	public Set<GoalTO> listAllGoals(){
		return this.goals.keySet();
	}

	public Map<String, Rule> getTableRules() {
		return tableRules;
	}

	
}
