package uk.co.brotherlogic.mdbweb.artist;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.TemplatePage;
import uk.co.brotherlogic.mdb.artist.Artist;
import uk.co.brotherlogic.mdb.artist.GetArtists;
import uk.co.brotherlogic.mdb.record.Record;

public class Default extends TemplatePage {
	@Override
	public Class generates() {
		// TODO Auto-generated method stub
		return Artist.class;
	}

	public boolean unequal(String s1, String s2) {
		return !s1.equals(s2);
	}

	@Override
	public String linkParams(Object arg0) {
		return "" + (((Artist) arg0).getId());
	}

	@Override
	protected Map<String, Object> convertParams(List<String> strParams,
			Map<String, String> params) {
		Map<String, Object> paramMap = new TreeMap<String, Object>();
		try {
			int labelID = Integer.parseInt(strParams.get(0));
			final Artist artist = GetArtists.create().getArtist(labelID);

			paramMap.put("artist", artist);

			List<Record> artRecords = new LinkedList<Record>(
					artist.getGroopMemberRecords());
			Collections.sort(artRecords, new Comparator<Record>() {
				@Override
				public int compare(Record o1, Record o2) {
					if (o1.getAuthor().equals(o2.getAuthor()))
						return 0;
					else if (o1.getAuthor().equals(artist.getSortName()))
						return -1;
					else if (o2.getAuthor().equals(artist.getSortName()))
						return 1;
					else
						return o1.getTitle().compareTo(o2.getTitle());
				}
			});
			paramMap.put("records", artRecords);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return paramMap;
	}

}
