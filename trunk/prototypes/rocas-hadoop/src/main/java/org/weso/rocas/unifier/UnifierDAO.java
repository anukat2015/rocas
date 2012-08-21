package org.weso.rocas.unifier;

import java.util.List;

import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

public interface UnifierDAO {

	public List<Rule> match(TriplePattern pattern);
}
