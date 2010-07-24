package uk.co.brotherlogic.mdbweb.label;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.Page;
import uk.co.brotherlogic.jarpur.TemplatePage;
import uk.co.brotherlogic.mdb.label.GetLabels;
import uk.co.brotherlogic.mdb.label.Label;
import uk.co.brotherlogic.mdb.record.Record;

public class LabelPage extends TemplatePage {
	

	@Override
	protected Map<String,Object> convertParams(List<String> elems, Map<String, String> params) {
		Map<String, Object> paramMap = new TreeMap<String, Object>();
		try {
			int labelID = Integer.parseInt(params.get("id"));
			Label label = GetLabels.create().getLabel(labelID);

			paramMap.put("label", label);
			System.err.println("Storing label = " + label.getName() + " ["
					+ labelID + "]");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return paramMap;
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
