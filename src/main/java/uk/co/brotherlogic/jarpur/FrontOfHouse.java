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

<<<<<<< HEAD
		LinkTable.add = req.getContextPath();

		if (baseAddress.length() == 0) {
			// Build up the base address
			String base = req.getContextPath();
			baseAddress = req.getRequestURL().substring(0,
					req.getRequestURL().indexOf(base) + base.length())
					+ "/";
		}

		String params = req.getRequestURL().toString();

		LinkTable.add = baseAddress.substring(baseAddress.indexOf("/", 7));
		String request = params
				.substring(baseAddress.length(), params.length());

		if (resourceRoute.matches(request)) {
			// We're after a resource rather than a page - just serve it
			OutputStream os = res.getOutputStream();
			byte[] buffer = new byte[256];
			InputStream is = new FileInputStream(new File(getServletContext()
					.getRealPath("WEB-INF")
					+ "/resources/" + resourceRoute.getRemaining(request)));
			int read = is.read(buffer, 0, buffer.length);
			while (read > 0) {
				os.write(buffer, 0, read);
				read = is.read(buffer, 0, buffer.length);
			}
			is.close();
			os.close();
		} else {
			// Build the routing table if we need to
			if (routingTable == null)
				buildRoutingTable();

			res.setContentType("text/html");
			PrintWriter out = res.getWriter();

			Route matcher = null;
			for (Route route : routingTable) {
				if (route.matches(request)) {
					matcher = route;
					break;
				}
			}

			if (matcher != null) {
				Page handler = matcher.getHandler();
				String remainder = matcher.getRemaining(request);
				String[] elems = remainder.split("/");
				Map<String, String> parameters = new TreeMap<String, String>();

				// Add the HTTP Parameters
				Map pMap = req.getParameterMap();
				for (Object key : pMap.keySet()) {
					String sKey = (String) key;
					String value = ((String[]) pMap.get(key))[0];
					parameters.put(sKey, value);
				}

				if (elems.length >= 2)
					for (int i = 0; i < elems.length; i += 2)
						parameters.put(elems[i], elems[i + 1]);

				long sTime = System.currentTimeMillis();
				out.println(handler.buildPage(parameters));
				System.err.println("Build time = "
						+ (System.currentTimeMillis() - sTime) + "ms");

				out.close();
			}
=======
		//Get the path and drop off the leading / and add a trailing one if not already there
		String path = req.getPathInfo().substring(1);
		if (!path.endsWith("/"))
			path = path + "/";
		
		System.err.println("SEARCH:" + path + ":");
		
		//Start searching for the appropriate class
		List<String> params = new LinkedList<String>();
		String page = search(path,params,req.getParameterMap());
		
		//Write out the result
		OutputStream os = res.getOutputStream();
		PrintStream ps = new PrintStream(os);
		ps.print(page);
		os.close();
	}
	
	private String capitalize(String in)
	{
		return Character.toUpperCase(in.charAt(0)) + in.substring(1);
	}
	
	private String search(String path, List<String> params, Map<String,String> paramMap)
	{
		String className = JarpurProperties.get("base") + "." + path.replace("/", ".");
		System.err.println(className);
		
		//Build on Default
		String defaultClass = className.trim() + "Default";
		String res = build(defaultClass,params,paramMap);
		if (res != null)
			return res;
		
		String[] pathElems = path.split("/");
		className = JarpurProperties.get("base") + "." + path.substring(0,path.length()-pathElems[pathElems.length-1].length()-1).replace("/", ".");
		String nClass = className.trim() + capitalize(pathElems[pathElems.length-1]);
		res = build(nClass,params,paramMap);
		if (res != null)
			return res;
		
		if (pathElems.length > 0)
		{
			params.add(pathElems[pathElems.length-1]);
			return search (path.substring(0,path.length()-pathElems[pathElems.length-1].length()-1),params,paramMap);
		}
		
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
			System.err.println("SKIPPING:" + className);
>>>>>>> b65f12c22586957d97c45d7b203773a3b4380fef
		}
		
		return null;
	}
}