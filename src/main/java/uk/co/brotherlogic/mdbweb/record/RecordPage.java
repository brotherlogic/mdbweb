package uk.co.brotherlogic.mdbweb.record;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.Page;
import uk.co.brotherlogic.mdb.record.GetRecords;
import uk.co.brotherlogic.mdb.record.Record;

public class RecordPage extends Page {
	@Override
	protected String buildPage(Map<String, String> params) throws IOException {
		try {
			int recordID = Integer.parseInt(params.get("id"));
			Record record = GetRecords.create().getRecord(recordID);

			Map<String, Object> paramMap = new TreeMap<String, Object>();
			paramMap.put("record", record);

			return buildPageFromTemplate(paramMap);
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}
}
