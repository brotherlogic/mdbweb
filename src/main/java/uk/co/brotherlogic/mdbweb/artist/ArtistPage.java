package uk.co.brotherlogic.mdbweb.artist;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.TemplatePage;
import uk.co.brotherlogic.mdb.artist.Artist;
import uk.co.brotherlogic.mdb.artist.GetArtists;
import uk.co.brotherlogic.mdb.label.GetLabels;
import uk.co.brotherlogic.mdb.label.Label;

public class ArtistPage extends TemplatePage {
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
	protected Map<String,Object> convertParams(List<String> strParams,Map<String, String> params)  {
		Map<String, Object> paramMap = new TreeMap<String, Object>();
		try {
			int labelID = Integer.parseInt(params.get("id"));
			Artist artist = GetArtists.create().getArtist(labelID);

			paramMap.put("artist", artist);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return paramMap;
	}


	public Boolean sized(Collection col) {
		System.err.println("COL: " + col);
		return col.size() > 0;
	}

	public Boolean unequal(String one, String two) {
		return !one.equalsIgnoreCase(two);
	}

	public static void main(String[] args) throws Exception {
		Label label = GetLabels.create().getLabel(9);
		System.err.println("Storing label = " + label.getName() + " [" + label
				+ "]");
	}
}
