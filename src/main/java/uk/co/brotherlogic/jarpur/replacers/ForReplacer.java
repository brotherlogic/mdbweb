package uk.co.brotherlogic.jarpur.replacers;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForReplacer extends Replacer {

	String forName;
	String forResolve;

	Pattern forPattern = Pattern.compile("for (.*) in (.*?)%");

	@Override
	public String process(Object ref, Map<String, Object> objectMap) {
		setRefObj(ref);
		Collection col = (Collection) resolve(forResolve, objectMap);
		StringBuffer buffer = new StringBuffer();
		int count = 0;
		for (Object obj : col) {
			objectMap.put(forName, obj);
			for (Replacer repl : getReplacers())
				buffer.append(repl.process(ref, objectMap));
			objectMap.remove(forName);
			count++;
		}

		return buffer.toString();
	}

	public ForReplacer(String param, Replacer allReplacements) {

		Matcher matcher = forPattern.matcher(param);

		if (matcher.find()) {
			forName = matcher.group(1);
			forResolve = matcher.group(2);
		}

		addReplacer(allReplacements);
	}

	@Override
	public String toString() {
		return "For";
	}
}