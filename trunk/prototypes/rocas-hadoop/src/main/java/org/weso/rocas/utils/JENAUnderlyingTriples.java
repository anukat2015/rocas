package org.weso.rocas.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class JENAUnderlyingTriples {

	public static void serializeAsUnderlyingTriples(String source, String target, String lang) throws FileNotFoundException{
		Model m = ModelFactory.createDefaultModel();
		m.read(new FileInputStream(source), null, TripleUtils.INPUT_TRIPLE_FORMAT);
		Map<String, String> prefixes = m.getNsPrefixMap();
		for(String prefix:prefixes.keySet()){
			m.removeNsPrefix(prefix);
		}
		FileOutputStream os = new FileOutputStream(new File(target));
		m.write(os, (lang == null?TripleUtils.OUTPUT_TRIPLE_FORMAT:lang));
		m.close();
		m = null;		
	}
	public static Model serializeAsUnderlyingTriples(Model source){
		Model m = ModelFactory.createDefaultModel();
		m.add(source);
		Map<String, String> prefixes = m.getNsPrefixMap();
		for(String prefix:prefixes.keySet()){
			m.removeNsPrefix(prefix);
		}
		return m;		
	}
	
	public static void main(String []args) throws FileNotFoundException{
		String source = "/home/chema/datasets/cpv-2008/cpv-2008-mini.ttl";
		String target = "/home/chema/datasets/cpv-2008/cpv-2008-mini-ut.ttl";
		serializeAsUnderlyingTriples(source, target, null);
	}
}
