package uk.co.brotherlogic.jarpur.replacers;

import java.util.Map;

public class SimpleReplacer extends Replacer {

	@Override
	public String process(Map<String, Object> objectMap) {
		return resolve(replacement, objectMap).toString();
	}

	private final String replacement;

	public SimpleReplacer(String replacerText) {
		replacement = replacerText;
	}

	@Override
	public String toString() {
		return "Simple: " + replacement;
	}
}
