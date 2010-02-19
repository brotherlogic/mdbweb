package uk.co.brotherlogic.jarpur;

import java.io.IOException;

public class Route implements Comparable<Route>
{
	private final String match;
	private final String handlerClass;

	public Route(String matcher, String handler)
	{
		this.match = matcher;
		this.handlerClass = handler;
	}

	@Override
	public String toString()
	{
		return "ROUTE=" + match;
	}

	public boolean matches(String request)
	{
		return request.startsWith(match);
	}

	public String getRemaining(String request)
	{
		return request.substring(match.length(), request.length());
	}

	public Page getHandler() throws IOException
	{
		try
		{
			Class handler = Class.forName(handlerClass);
			return (Page) handler.newInstance();
		} catch (ClassNotFoundException e)
		{
			throw new IOException(e);
		} catch (InstantiationException e)
		{
			throw new IOException(e);
		} catch (IllegalAccessException e)
		{
			throw new IOException(e);
		}
	}

	@Override
	public int compareTo(Route o)
	{
		return match.compareTo(o.match);
	}

}
