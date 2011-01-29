package uk.co.brotherlogic.mdbweb.track;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringEscapeUtils;

import uk.co.brotherlogic.jarpur.TemplatePage;
import uk.co.brotherlogic.mdb.record.GetRecords;
import uk.co.brotherlogic.mdb.record.Record;
import uk.co.brotherlogic.mdb.record.Track;

public class Default extends TemplatePage {

	@Override
	public Class generates() {
		return Track.class;
	}

	@Override
	public String linkParams(Object arg0) {
		// TODO Auto-generated method stub
		return htmlConvert(((Track) arg0).getTitle());
	}

	private String htmlConvert(String in) {
		return StringEscapeUtils.escapeHtml(in);
	}

	public String getTrackAuthor(Record rec) {
		try {
			for (Track track : rec.getTracks())
				if (track.getTitle().equalsIgnoreCase(trackname))
					return track.getTrackAuthor();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "Unknown";
	}

	String trackname = "";

	@Override
	protected Map<String, Object> convertParams(List<String> elems,
			Map<String, String> params) {

		Map<String, Object> paramMap = new TreeMap<String, Object>();
		try {
			trackname = URLDecoder.decode(elems.get(0), "utf-8");
			paramMap.put("trackname", trackname);
			paramMap.put("records",
					GetRecords.create().getRecordsWithTrack(trackname));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return paramMap;
	}

}
