package org.weso.rocas.utils;

import java.util.List;

import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

public class RuleUtils {
	
	public static void show(List<Rule> rules){
		for(Rule rule:rules){
			show(rule);
		}
	}

	public static void show(Rule rule){
		ClauseEntry[] head = rule.getHead();
		ClauseEntry[] body = rule.getBody();
		for(int i = 0; i< head.length;i++){
			System.out.println(rule.getHeadElement(i));
		}
		for(int i = 0; i< body.length;i++){
			System.out.println(rule.getBodyElement(i));
		}
	}
}
