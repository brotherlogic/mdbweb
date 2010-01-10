package uk.co.brotherlogic.jarpur;

import java.io.IOException;
import java.util.Map;

public abstract class Page
{
	protected abstract String buildPage(Map<String, String> params) throws IOException;

	protected String buildPageFromTemplate(Map<String, Object> paramMap) throws IOException
	{
		return "Page Built";
	}
}
