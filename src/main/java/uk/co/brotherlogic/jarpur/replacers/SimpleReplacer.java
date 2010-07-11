package uk.co.brotherlogic.jarpur.replacers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import uk.co.brotherlogic.jarpur.LinkTable;

public class SimpleReplacer extends Replacer {

	private static LinkTable lTable;

	public static void setLinkTable(LinkTable table) {
		lTable = table;
	}

	@Override
	public String process(Object ref, Map<String, Object> objectMap) {

		if (replacement.startsWith("link:resource"))
			return LinkTable.add + replacement.substring(5);

		if (replacement.startsWith("link")) {
			return lTable.resolveLink(resolve(replacement.substring(5),
					objectMap));
		}

		Object obj = resolve(replacement, objectMap);

		if (obj instanceof Calendar) {
			DateFormat df = new SimpleDateFormat("dd/MM/yy");
			return df.format(((Calendar) obj).getTime());
		}

		return obj.toString();
	}

	private final String replacement;

	public SimpleReplacer(Object pg, String replacerText) {
		setRefObj(pg);
		replacement = replacerText;
	}

	@Override
	public String toString() {
		return "Simple: " + replacement;
	}
}
