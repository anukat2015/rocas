package org.weso.rocas.unifier;

import java.util.List;

import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.reasoner.rulesys.impl.RuleStore;

public class JENAUnifierDAOImpl implements UnifierDAO {

	protected RuleStore ruleStore;
	
	public JENAUnifierDAOImpl(){
		this.ruleStore = new RuleStore();
	}
	
	public JENAUnifierDAOImpl(RuleStore ruleStore){
		this.ruleStore = ruleStore;
	}

	@Override
	public List<Rule> match(TriplePattern pattern) {
		return this.ruleStore.rulesFor(pattern);
	}

}
