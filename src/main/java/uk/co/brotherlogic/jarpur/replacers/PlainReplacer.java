package uk.co.brotherlogic.jarpur.replacers;

import java.util.Map;

public class PlainReplacer extends Replacer {

	private final String text;

	public PlainReplacer(String text) {
		this.text = text;
	}

	@Override
	public String process(Map<String, Object> objectMap) {
		return text;
	}

	@Override
	public String toString() {
		return "PlainText";
	}
}
