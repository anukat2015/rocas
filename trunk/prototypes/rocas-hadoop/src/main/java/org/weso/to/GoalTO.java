package org.weso.to;

import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.impl.StateFlag;
import com.hp.hpl.jena.reasoner.rulesys.impl.oldCode.inherited.GoalState;

public class GoalTO {

	private TriplePattern goal;
	private String idRule;
	private StateFlag state;
	public TriplePattern getGoal() {
		return goal;
	}
	public void setGoal(TriplePattern goal) {
		this.goal = goal;
	}
	public String getIdRule() {
		return idRule;
	}
	public void setIdRule(String idRule) {
		this.idRule = idRule;
	}
	public StateFlag getState() {
		return state;
	}
	public void setState(StateFlag state) {
		this.state = state;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goal == null) ? 0 : goal.hashCode());
		result = prime * result + ((idRule == null) ? 0 : idRule.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		GoalTO other = (GoalTO) obj;
		if (goal == null) {
			if (other.goal != null)
				return false; //FIXME: equals ?a @rdf:type Concept to ?b @rdf:type Concept
		} else if (!goal.toString().equals(other.goal.toString()))
			return false;
		if (idRule == null) {
			if (other.idRule != null)
				return false;
		} else if (!idRule.equals(other.idRule))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	public GoalTO(TriplePattern goal, String idRule, StateFlag state) {
		super();
		this.goal = goal;
		this.idRule = idRule;
		this.state = state;
	}
	public GoalTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "GoalTO [goal=" + goal + ", idRule=" + idRule + ", state="
				+ state + "]";
	}
	
}
