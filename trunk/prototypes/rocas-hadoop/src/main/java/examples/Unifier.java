package examples;

import java.util.List;

import org.apache.commons.math.optimization.GoalType;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.reasoner.rulesys.impl.RuleStore;

public class Unifier {

	public static void main(String []args){
		String rule = "[rule1: (?a <http://example.org#p> ?b) (?b <http://example.org#p> ?c) -> (?a <http://example.org#p> ?c)]";
		List<Rule> rules = Rule.parseRules(rule);
		show(rules.get(0));
		RuleStore store = new RuleStore(rules);
		Node s = Node.createURI("?a");
		Node p = Node.createURI("http://example.org#p");
		Node o = Node.createURI("?c");
		Triple match = new Triple(s, p, o);
		System.out.println("Match: "+match);
		TriplePattern goal = new TriplePattern(match);
		List<Rule> result = store.rulesFor(goal);		
		System.out.println(result.size());		
		show(store.getAllRules().get(0));
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
