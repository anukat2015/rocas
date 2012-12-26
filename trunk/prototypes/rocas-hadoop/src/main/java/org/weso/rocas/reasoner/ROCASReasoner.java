package org.weso.rocas.reasoner;

import org.weso.rocas.model.GoalFactory;
import org.weso.rocas.model.RuleNode;

public interface ROCASReasoner {

	public Agenda getAgenda();
	public GoalFactory getGoals();
	public RuleNode getDeriviationTree();
	
}
