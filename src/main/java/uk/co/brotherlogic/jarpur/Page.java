package uk.co.brotherlogic.jarpur;

import java.io.IOException;

public abstract class Page {
	
	public abstract String generate(PageRequest request) throws IOException;
	public abstract double acccount(PageRequest request);
	
}
