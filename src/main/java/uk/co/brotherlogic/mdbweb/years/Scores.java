package uk.co.brotherlogic.mdbweb.years;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.TemplatePage;
import uk.co.brotherlogic.mdb.User;
import uk.co.brotherlogic.mdb.record.GetRecords;
import uk.co.brotherlogic.mdb.record.Record;

public class Scores extends TemplatePage{

	@Override
	protected Map<String, Object> convertParams(List<String> arg0,
			Map<String, String> arg1) {
		Map<String,Object> resMap = new TreeMap<String, Object>();
		resMap.put("Person", "Simon");
		resMap.put("Year",new Integer(2010));
		
		List<Record> records = new LinkedList<Record>();
		try
		{
			for(Integer recNum: GetRecords.create().getRecordNumbers())
			{
				Record rec = GetRecords.create().getRecord(recNum);
				if (rec.getOwner() ==1)
					records.add(rec);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		//Sort records by their score
		Collections.sort(records,new Comparator<Record>() {

			@Override
			public int compare(Record arg0, Record arg1) {
				try
				{
					return new Double(arg0.getScore(User.getUser("Simon"))).compareTo(new Double(arg1.getScore(User.getUser("Simon"))));
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
				return 0;
			}
		});
		
		resMap.put("recordsprimary",records.subList(0, 1200));
		resMap.put("recordssecondary",records.subList(1200,records.size()));
		
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
