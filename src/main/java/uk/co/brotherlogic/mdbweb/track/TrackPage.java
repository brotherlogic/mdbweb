package uk.co.brotherlogic.mdbweb.track;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.Page;
import uk.co.brotherlogic.mdb.record.GetRecords;

public class TrackPage extends Page {

	@Override
	public String buildPage(Map<String, String> params) throws IOException {
		String trackname = URLDecoder.decode(params.get("name"), "utf-8");

		Map<String, Object> paramMap = new TreeMap<String, Object>();
		paramMap.put("trackname", trackname);
		try {
			paramMap.put("records", GetRecords.create().getRecordsWithTrack(
					trackname));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return buildPageFromTemplate(paramMap);
	}

}
