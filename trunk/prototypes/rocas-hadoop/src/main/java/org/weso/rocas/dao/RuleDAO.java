package org.weso.rocas.dao;

import java.io.Reader;
import java.util.List;

import com.hp.hpl.jena.reasoner.rulesys.Rule;

public interface RuleDAO {

	List<Rule> parseRulesFromString (String source);
	List<Rule> parseRulesFromURL (String url);
	List<Rule> parseRulesFromClasspath (String path);
	List<Rule> parseRulesFromFile (String path);
}
