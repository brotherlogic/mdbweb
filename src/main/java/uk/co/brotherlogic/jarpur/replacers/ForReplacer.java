package uk.co.brotherlogic.jarpur.replacers;

import java.util.Map;

public class ForReplacer extends Replacer {

	@Override
	public String process(Map<String, Object> objectMap) {
		return "FOR LOOP HERE";
	}

	public ForReplacer(String param, Replacer allReplacements) {
		addReplacer(allReplacements);
	}

	@Override
	public String toString() {
		return "For";
	}
}