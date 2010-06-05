package uk.co.brotherlogic.jarpur;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkTable {

	public static String add = "/jarpur/";
	Map<String, String> links = new TreeMap<String, String>();

	public LinkTable(Properties props) {

		for (Object key : props.keySet()) {
			String classname = key.toString();
			links.put(classname, props.getProperty(classname));
		}
	}

	public String resolveLink(Object o) {
		String classname = o.getClass().getCanonicalName();

		if (links.containsKey(classname)) {
			return resolveLink(o, links.get(classname));
		}
		return null;
	}

	Pattern objPattern = Pattern.compile("\\%\\%(.*?)\\%\\%");

	private String resolveLink(Object o, String ref) {
		StringBuffer buffer = new StringBuffer(ref);

		Matcher matcher = objPattern.matcher(ref);
		if (matcher.find()) {
			int start = matcher.start(1);
			int end = matcher.end(1);

			String methodName = ref.substring(start, end);
			String rep = resolveMethod(o, methodName);
			buffer.replace(start - 2, end + 2, rep);
			return add + buffer.toString();
		} 
		return null;
	}

	protected String resolveMethod(Object obj, String methodName) {

		try {
			Class cls = obj.getClass();
			Method m = cls.getMethod(methodName, new Class[0]);
			Object ret = m.invoke(obj, new Object[0]);
			return ret.toString();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}
}
