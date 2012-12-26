package org.weso.rocas.model;

import java.util.List;

import com.hp.hpl.jena.reasoner.TriplePattern;

public interface GoalFactory {

	public void addGoal(TriplePattern triplePattern);
	public void removeGoal(TriplePattern triplePattern);
	public List<TriplePattern> listGoals();
	public void addGoalMatch(TriplePattern triplePattern, TriplePattern triple);
	public List<TriplePattern> getMatchesByGoal(TriplePattern triplePattern);
}
