package uk.co.brotherlogic.jarpur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Page {
	protected abstract String buildPage(Map<String, String> params)
			throws IOException;

	Pattern percMatcher = Pattern.compile("(\\%\\%.*?\\%\\%)");

	DateFormat df = DateFormat.getDateInstance();

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

	protected Object resolve(String replace, Map<String, Object> paramMap) {

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
			return obj;
		}

		return "UNABLE TO REPLACE";
	}

	Pattern forPattern = Pattern.compile("for (.*?) in (.*?)%");

	protected StringReplace replaceWithForLoop(StringBuffer buffer,
			int startPoint, int endPoint, Map<String, Object> paramMap) {
		// Find the end of the for loop
		int startOfEnd = buffer.lastIndexOf("%%endfor%%");
		String replacementText = buffer.substring(endPoint, startOfEnd);

		String replaceString = "";

		// Get the properties of the for loop
		Matcher forMatch = forPattern.matcher(buffer.substring(startPoint,
				endPoint));
		if (forMatch.find()) {
			String newParam = forMatch.group(1);
			String collection = forMatch.group(2);

			System.err.println("FROM: "
					+ buffer.substring(startPoint, endPoint));
			System.err.println("Building collection = " + collection + " and "
					+ newParam);
			Collection<Object> objects = (Collection<Object>) resolve(
					collection, paramMap);
			for (Object object : objects) {
				StringBuffer temp = new StringBuffer(replacementText);
				paramMap.put(newParam, object);
				doReplace(temp, paramMap);
				replaceString += temp.toString();
			}

			paramMap.remove(newParam);
		}

		// Do the replace
		return new StringReplace(startPoint, startOfEnd + 10, replaceString);

	}

	protected void doReplace(StringBuffer buffer, Map<String, Object> paramMap) {
		// Simple replace
		Matcher matcher = percMatcher.matcher(buffer.toString());
		Stack<StringReplace> replaceStack = new Stack<StringReplace>();
		int matchPoint = 0;
		while (matcher.find(matchPoint)) {
			int grpStart = matcher.start(1);
			int grpEnd = matcher.end(1);

			// Check that this isn't a for loop
			if (buffer.substring(grpStart + 2, grpEnd - 2).startsWith("for ")) {
				replaceStack.push(replaceWithForLoop(buffer, grpStart, grpEnd,
						paramMap));
			} else {
				String replaceText = convert(resolve(buffer.substring(
						grpStart + 2, grpEnd - 2), paramMap));
				replaceStack.push(new StringReplace(grpStart, grpEnd,
						replaceText));
			}

			matchPoint = replaceStack.peek().getEnd();
		}

		while (!replaceStack.isEmpty()) {
			replaceStack.pop().apply(buffer);
		}
	}

	protected String convert(Object obj) {
		System.err.println(obj.getClass() + " is the type");
		if (obj instanceof Calendar)
			return df.format(((Calendar) obj).getTime());
		else
			return obj.toString();
	}
}

class StringReplace {
	int start;
	int end;
	String toPlace;

	public void apply(StringBuffer buffer) {
		buffer.replace(start, end, toPlace);
	}

	public int getEnd() {
		return end;
	}

	public StringReplace(int st, int en, String pl) {
		start = st;
		end = en;
		toPlace = pl;
	}
}
