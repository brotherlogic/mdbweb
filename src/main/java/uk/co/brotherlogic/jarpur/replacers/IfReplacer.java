package uk.co.brotherlogic.jarpur.replacers;

import java.util.Map;

public class IfReplacer extends Replacer {

	Replacer elemts;
	String param;

	public IfReplacer(String param, Replacer elements) {
		elemts = elements;
		this.param = param;

		System.err.println("IF: " + param);

	}

	@Override
	public String process(Map<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return super.process(objectMap);
	}

}
