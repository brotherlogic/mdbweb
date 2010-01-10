package uk.co.brotherlogic.jarpur;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Front of house deals with request and routes them to the relevant controller
 * 
 * @author sat
 * 
 */
public class FrontOfHouse extends HttpServlet
{
	List<Route> routingTable;
	String baseAddress = "http://localhost:8080/jarpur/";

	public FrontOfHouse()
	{

	}

	public static void main(String[] args)
	{
		FrontOfHouse foh = new FrontOfHouse();
	}

	private void buildRoutingTable()
	{
		routingTable = new LinkedList<Route>();
		Properties props = new Properties();

		try
		{
			props.load(new FileInputStream(new File(getServletContext().getRealPath("WEB-INF") + "/routing.properties")));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		for (Object matcher : props.keySet())
		{
			routingTable.add(new Route(matcher.toString(), props.get(matcher).toString()));
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	{
		res.setContentType("text/html");

		PrintWriter out = res.getWriter();

		/* Display some response to the user */
		String params = req.getRequestURL().toString();
		String request = params.substring(baseAddress.length(), params.length());

		// Build the routing table if we need to
		if (routingTable == null)
			buildRoutingTable();

		Route matcher = null;
		for (Route route : routingTable)
		{
			if (route.matches(request))
			{
				matcher = route;
				break;
			}
		}

		if (matcher != null)
		{
			Page handler = matcher.getHandler();
			String remainder = matcher.getRemaining(request);
			String[] elems = remainder.split("/");
			Map<String, String> parameters = new TreeMap<String, String>();
			for (int i = 0; i < elems.length; i += 2)
				parameters.put(elems[i], elems[i + 1]);

			out.println(handler.buildPage(parameters));

			out.close();
		}
	}
}