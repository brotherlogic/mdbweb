package uk.co.brotherlogic.jarpur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public abstract class Page
{
	protected abstract String buildPage(Map<String, String> params) throws IOException;

	protected String buildPageFromTemplate(Map<String, Object> paramMap) throws IOException
	{
		String className = this.getClass().getCanonicalName();

		// Build the template
		StringBuffer template_data = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(FrontOfHouse.context.getRealPath("WEB-INF")
				+ "/" + className.replace(".", "/") + ".html"))));
		for (String line = reader.readLine(); line != null; line = reader.readLine())
			template_data.append(line);

		return template_data.toString();
	}
}
