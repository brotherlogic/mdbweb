package uk.co.brotherlogic.mdbweb.label;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.Page;
import uk.co.brotherlogic.mdb.label.GetLabels;
import uk.co.brotherlogic.mdb.label.Label;
import uk.co.brotherlogic.mdb.record.Record;

public class LabelPage extends Page {
	@Override
	protected String buildPage(Map<String, String> params) throws IOException {
		try {
			int labelID = Integer.parseInt(params.get("id"));
			Label label = GetLabels.create().getLabel(labelID);

			Map<String, Object> paramMap = new TreeMap<String, Object>();
			paramMap.put("label", label);
			System.err.println("Storing label = " + label.getName() + " ["
					+ labelID + "]");

			return buildPageFromTemplate(paramMap);
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	public static void main(String[] args) throws Exception {
		Label label = GetLabels.create().getLabel(1608);
		System.err.println("Storing label = " + label.getName() + " [" + label
				+ "]");
		for (Record rec : label.getRecords())
			System.err.println(rec.getAuthor() + " - " + rec.getTitle() + " - "
					+ rec.getCatNoString());
	}
}
