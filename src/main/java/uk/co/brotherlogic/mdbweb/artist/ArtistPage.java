package uk.co.brotherlogic.mdbweb.artist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.Page;
import uk.co.brotherlogic.mdb.artist.Artist;
import uk.co.brotherlogic.mdb.artist.GetArtists;
import uk.co.brotherlogic.mdb.groop.GetGroops;
import uk.co.brotherlogic.mdb.groop.Groop;
import uk.co.brotherlogic.mdb.label.GetLabels;
import uk.co.brotherlogic.mdb.label.Label;

public class ArtistPage extends Page
{
	@Override
	public String buildPage(Map<String, String> params) throws IOException
	{
		try
		{
			int labelID = Integer.parseInt(params.get("id"));
			Artist artist = GetArtists.create().getArtist(labelID);

			Map<String, Object> paramMap = new TreeMap<String, Object>();
			paramMap.put("artist", artist);

			Groop groop = GetGroops.build().getGroopFromShowName(
					artist.getShowName());
			if (groop != null)
			{
				System.err.println("HAS GROOP");
				paramMap.put("has_groop", new Boolean(true));
				paramMap.put("groop", groop);
			} else
			{
				System.err.println("NO GROOP");
				paramMap.put("has_groop", new Boolean(false));
			}

			return buildPageFromTemplate(paramMap);
		} catch (SQLException e)
		{
			throw new IOException(e);
		}
	}

	public static void main(String[] args) throws Exception
	{
		Label label = GetLabels.create().getLabel(9);
		System.err.println("Storing label = " + label.getName() + " [" + label
				+ "]");
	}
}
