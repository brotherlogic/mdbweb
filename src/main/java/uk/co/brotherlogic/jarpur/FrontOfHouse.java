package uk.co.brotherlogic.jarpur;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.brotherlogic.jarpur.replacers.SimpleReplacer;

/**
 * Front of house deals with request and routes them to the relevant controller
 * 
 * @author sat
 * 
 */
public class FrontOfHouse extends HttpServlet {

	public FrontOfHouse() {
	}

	public static void main(String[] args) {
		FrontOfHouse foh = new FrontOfHouse();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		String path = req.getContextPath();
		System.err.println("PATH = " + path);

		//Knock off the starter
		String searchPath = path.substring(JarpurProperties.get("web").length(), path.length());
		System.err.println("SEARCH:" + searchPath + ":");
		
		//Start searching for the appropriate class
		List<String> params = new LinkedList<String>();
		String page = search(searchPath,params,req.getParameterMap());
		
		//Write out the result
		OutputStream os = res.getOutputStream();
		PrintStream ps = new PrintStream(os);
		ps.print(page);
		os.close();
	}
	
	private String search(String path, List<String> params, Map<String,String> paramMap)
	{
		String className = JarpurProperties.get("base") + ". " + path.replace("/", ".");
		System.err.println(className);
		
		//Build on Default
		String defaultClass = className.trim() + "Default";
		String res = build(defaultClass,params,paramMap);
		if (res != null)
			return res;
		
		//Try down a bit
		return "";
	}
	
	private String build(String className, List<String> params, Map<String,String> paramMap)
	{
		try
		{
			Class cls = Class.forName(className);
			Page pg = (Page)cls.getConstructor(new Class[0]).newInstance(new Object[0]);
			return pg.generate(params, paramMap);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}