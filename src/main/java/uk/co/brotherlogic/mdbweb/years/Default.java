package uk.co.brotherlogic.mdbweb.years;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.TemplatePage;
import uk.co.brotherlogic.mdb.record.GetRecords;
import uk.co.brotherlogic.mdb.record.Record;

public class Default extends TemplatePage{

	@Override
	protected Map<String, Object> convertParams(List<String> arg0,
			Map<String, String> arg1) {
		Map<String,Object> resMap = new TreeMap<String, Object>();
		resMap.put("Person", "Jeanette");
		resMap.put("Year",new Integer(2010));
		
		List<Record> records = new LinkedList<Record>();
		try
		{
			for(Integer recNum: GetRecords.create().getRecordNumbers())
			{
				Record rec = GetRecords.create().getRecord(recNum);
				if (rec.getOwner() == 2 && rec.getReleaseYear() == 2010)
					records.add(rec);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		//Sort records by bought date
		Collections.sort(records,new Comparator<Record>() {

			@Override
			public int compare(Record arg0, Record arg1) {
				return -arg0.getDate().compareTo(arg1.getDate());
			}
		});
		
		resMap.put("records",records);
		
		return resMap;
	}

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

}
