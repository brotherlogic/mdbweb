package uk.co.brotherlogic.jarpur;

import java.io.IOException;
import java.io.PrintWriter;

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
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	{
		res.setContentType("text/html");

		PrintWriter out = res.getWriter();

		/* Display some response to the user */
		String params = req.getRequestURL().toString();
		out.println("RESULT = " + params);
	}
}