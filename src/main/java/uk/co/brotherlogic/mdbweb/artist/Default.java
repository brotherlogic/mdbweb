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
import uk.co.brotherlogic.mdb.record.Record;

public class Default extends TemplatePage {
	@Override
	public Class generates() {
		// TODO Auto-generated method stub
		return Artist.class;
	}

	@Override
	public String linkParams(Object arg0) {
		return "" + (((Artist)arg0).getId());
	}


	@Override
	protected Map<String,Object> convertParams(List<String> strParams,Map<String, String> params)  {
		Map<String, Object> paramMap = new TreeMap<String, Object>();
		try {
			int labelID = Integer.parseInt(strParams.get(0));
			Artist artist = GetArtists.create().getArtist(labelID);

			paramMap.put("artist", artist);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return paramMap;
	}

}
