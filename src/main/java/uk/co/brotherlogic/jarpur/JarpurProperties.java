package uk.co.brotherlogic.jarpur;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class JarpurProperties {
	private static Properties properties;
	public static String get(String key)
	{
		if (properties == null)
			buildProperties();
		if (properties != null)
			return properties.getProperty(key);
		else
			return "NULL";
	}
	
	private static void buildProperties()
	{
		System.err.println("FOUND = " + new File("web.properties").exists());
		properties = new Properties();
		try
		{
			properties.load(new FileInputStream(new File("web.properties")));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			properties = null;
		}
	}
}
