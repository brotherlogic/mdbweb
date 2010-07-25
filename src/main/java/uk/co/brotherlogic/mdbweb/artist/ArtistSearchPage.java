package uk.co.brotherlogic.mdbweb.artist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.Page;
import uk.co.brotherlogic.jarpur.TemplatePage;
import uk.co.brotherlogic.mdb.artist.Artist;
import uk.co.brotherlogic.mdb.artist.GetArtists;

public class ArtistSearchPage extends TemplatePage
{
	@Override
	public Class generates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String linkParams(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<String,Object> convertParams(List<String> elems, Map<String, String> params) 
	{
		Map<String, Object> paramMap = new TreeMap<String, Object>();
		try
		{
			String query = params.get("q");
			List<Artist> artists = GetArtists.create().search(query);

			paramMap.put("artists", artists);
			paramMap.put("query", query);

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return paramMap;
	}

	public static void main(String[] args) throws Exception
	{
		Artist art = GetArtists.create().getArtist(43087);
		System.err.println(art.getShowName());
		System.err.println(art.getRecords());
	}
}
