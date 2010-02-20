package uk.co.brotherlogic.mdbweb.artist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.Page;
import uk.co.brotherlogic.mdb.artist.Artist;
import uk.co.brotherlogic.mdb.artist.GetArtists;

public class ArtistSearchPage extends Page
{
	@Override
	public String buildPage(Map<String, String> params) throws IOException
	{
		try
		{
			String query = params.get("q");
			List<Artist> artists = GetArtists.create().search(query);

			Map<String, Object> paramMap = new TreeMap<String, Object>();
			paramMap.put("artists", artists);
			paramMap.put("query", query);

			return buildPageFromTemplate(paramMap);
		} catch (SQLException e)
		{
			throw new IOException(e);
		}
	}

	public static void main(String[] args) throws Exception
	{
		Artist art = GetArtists.create().getArtist(43087);
		System.err.println(art.getShowName());
		System.err.println(art.getRecords());
	}
}
