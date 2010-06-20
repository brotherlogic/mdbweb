package uk.co.brotherlogic.jarpur;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class Page {
	
	public abstract String generate(List<String> paramList, Map<String,String> paramMap) throws IOException;
	
}
