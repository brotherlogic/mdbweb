package uk.co.brotherlogic.mdbweb;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.Page;
import uk.co.brotherlogic.mdb.record.Record;
import uk.co.brotherlogic.mdb.record.RecordUtils;

public class HomePage extends Page {
	@Override
	public String buildPage(Map<String, String> params) throws IOException {
		try {
			Record recToListenTo = RecordUtils
					.getRecordToListenTo(new String[] { "12", "10", "7" });
			Record cdToListenTo = RecordUtils
					.getRecordToListenTo(new String[] { "CD" });
			Map<String, Object> paramMap = new TreeMap<String, Object>();
			paramMap.put("cd", cdToListenTo);
			paramMap.put("record", recToListenTo);
			paramMap.put("rip", RecordUtils.getRecordToRip());

			return buildPageFromTemplate(paramMap);
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	public boolean tester() {
		return false;
	}
}
