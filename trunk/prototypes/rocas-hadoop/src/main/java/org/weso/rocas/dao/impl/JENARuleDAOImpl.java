package org.weso.rocas.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import jena.RuleMap;

import org.apache.log4j.Logger;
import org.weso.rocas.dao.RuleDAO;
import org.weso.rocas.exception.ROCASModelException;

import com.hp.hpl.jena.iri.impl.Parser;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

public class JENARuleDAOImpl implements RuleDAO {
	
	protected static Logger logger = Logger.getLogger(JENARuleDAOImpl.class);
	
	public JENARuleDAOImpl(){
		super();
	}

	@Override
	public List<Rule> parseRulesFromString(String source) {
		return Rule.parseRules(source);
	}

	@Override
	public List<Rule> parseRulesFromURL(String url) {
		return Rule.rulesFromURL(url);
	}
	
	public List<Rule> parseRulesFromClasspath (String path){
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		com.hp.hpl.jena.reasoner.rulesys.Rule.Parser parser = Rule.rulesParserFromReader(reader);
		return Rule.parseRules(parser);		
	}

	@Override
	public List<Rule> parseRulesFromFile(String path) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(path));
			com.hp.hpl.jena.reasoner.rulesys.Rule.Parser parser = Rule.rulesParserFromReader(reader);
			return Rule.parseRules(parser);	
		} catch (FileNotFoundException e) {
			throw new ROCASModelException("Unable to parse file "+path,e);
		}
		
	}

}
