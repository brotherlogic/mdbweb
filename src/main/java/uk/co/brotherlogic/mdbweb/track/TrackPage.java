package uk.co.brotherlogic.mdbweb.track;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.Page;
import uk.co.brotherlogic.jarpur.TemplatePage;
import uk.co.brotherlogic.mdb.record.GetRecords;

public class TrackPage extends TemplatePage {

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
	protected Map<String,Object> convertParams(List<String> elems, Map<String, String> params) {
		
		
		Map<String, Object> paramMap = new TreeMap<String, Object>();
		try {
			String trackname = URLDecoder.decode(params.get("name"), "utf-8");
			paramMap.put("trackname", trackname);
			paramMap.put("records", GetRecords.create().getRecordsWithTrack(
					trackname));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return paramMap;
	}

}
