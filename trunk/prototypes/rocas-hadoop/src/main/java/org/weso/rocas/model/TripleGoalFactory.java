package org.weso.rocas.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.reasoner.TriplePattern;

public class TripleGoalFactory implements GoalFactory{

	private Map<TriplePattern, List<TriplePattern>> goals;
	
	public TripleGoalFactory(){
		this.goals = new HashMap<TriplePattern, List<TriplePattern>>();
	}
	
	@Override
	public void addGoal(TriplePattern triplePattern) {
		this.goals.put(triplePattern, new LinkedList<TriplePattern>());		
	}

	@Override
	public void removeGoal(TriplePattern triplePattern) {
		this.goals.remove(triplePattern);
		
	}

	@Override
	public List<TriplePattern> listGoals() {
		return new ArrayList<TriplePattern>(this.goals.keySet());
	}

	@Override
	public void addGoalMatch(TriplePattern triplePattern, TriplePattern triple) {
		this.goals.get(triplePattern).add(triple);
		
	}

	@Override
	public List<TriplePattern> getMatchesByGoal(TriplePattern triplePattern) {
		return this.goals.get(triplePattern);
	}

}
