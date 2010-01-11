package uk.co.brotherlogic.jarpur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Page {
	protected abstract String buildPage(Map<String, String> params)
			throws IOException;

	Pattern percMatcher = Pattern.compile("(\\%\\%.*?\\%\\%)");

	protected String buildPageFromTemplate(Map<String, Object> paramMap)
			throws IOException {
		String className = this.getClass().getCanonicalName();

		// Build the template
		StringBuffer template_data = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(FrontOfHouse.context
						.getRealPath("WEB-INF")
						+ "/" + className.replace(".", "/") + ".html"))));
		for (String line = reader.readLine(); line != null; line = reader
				.readLine())
			template_data.append(line);

		// Apply the template
		doReplace(template_data, paramMap);

		return template_data.toString();
	}

	protected Object resolveMethod(Object obj, String methodName) {
		try {
			Class cls = obj.getClass();
			Method m = cls.getMethod(methodName, new Class[0]);
			Object ret = m.invoke(obj, new Object[0]);
			return ret;
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

	protected String resolve(String replace, Map<String, Object> paramMap) {

		String[] elems = replace.split("\\.");
		if (paramMap.containsKey(elems[0])) {
			Object obj = paramMap.get(elems[0]);
			for (int i = 1; i < elems.length; i++) {
				System.err.println("START = " + obj + "(" + obj.getClass()
						+ ")");
				obj = resolveMethod(obj, elems[i]);
				System.err.println("END = " + obj + "(" + obj.getClass() + ")");
			}
			System.err.println("RESOLVING TO: " + obj);
			return obj.toString();
		}

		return "UNABLE TO REPLACE";
	}

	protected void doReplace(StringBuffer buffer, Map<String, Object> paramMap) {
		// Simple replace
		Matcher matcher = percMatcher.matcher(buffer.toString());
		Stack<StringReplace> replaceStack = new Stack<StringReplace>();
		while (matcher.find()) {

			System.err.println("FOUND A MATCH");

			int grpStart = matcher.start(1);
			int grpEnd = matcher.end(1);

			String replaceText = resolve(buffer.substring(grpStart + 2,
					grpEnd - 2), paramMap);
			replaceStack.push(new StringReplace(grpStart, grpEnd, replaceText));
		}
		System.err.println("MATCHES DONE");

		for (StringReplace stringReplace : replaceStack) {
			stringReplace.apply(buffer);
		}
	}
}

class StringReplace {
	int start;
	int end;
	String toPlace;

	public void apply(StringBuffer buffer) {
		buffer.replace(start, end, toPlace);
	}

	public StringReplace(int st, int en, String pl) {
		start = st;
		end = en;
		toPlace = pl;
	}
}
