package uk.co.brotherlogic.jarpur.replacers;

import java.util.Map;

public class IfReplacer extends Replacer {

	Replacer elemts;
	String param;

	public IfReplacer(String param, Replacer elements) {
		elemts = elements;
		this.param = param;

	}

	@Override
	public String process(Object ref, Map<String, Object> objectMap) {

		setRefObj(ref);
		Object obj = resolve(param.substring(5, param.length() - 2), objectMap);

		if (obj instanceof Boolean) {
			if (((Boolean) obj)) {
				return elemts.process(ref, objectMap);
			}
		}

		return super.process(ref, objectMap);
	}

	@Override
	public String toString() {
		return "IF: " + param;
	}

}
