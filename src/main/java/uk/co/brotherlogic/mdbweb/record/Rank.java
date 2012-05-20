package uk.co.brotherlogic.mdbweb.record;

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
import uk.co.brotherlogic.mdb.record.RecordScore;

public class Rank extends TemplatePage
{

   @Override
   protected Map<String, Object> convertParams(List<String> arg0, Map<String, String> arg1)
   {
      List<Record> records = new LinkedList<Record>();
      TreeMap<String, Object> mapper = new TreeMap<String, Object>();
      try
      {
         records.addAll(GetRecords.create().getRecords(GetRecords.SHELVED, "12"));

         // Clear out the non-doubled
         List<Record> remove = new LinkedList<Record>();
         for (Record rec : records)
            if (rec.getScoreCount(User.getUser("simon")) != 2)
               remove.add(rec);

         records.removeAll(remove);
         mapper.put("togo", remove.size());
         mapper.put("done", records.size());

         // Sort the records according to their score
         Collections.sort(records, new Comparator<Record>()
         {

            @Override
            public int compare(Record arg0, Record arg1)
            {
               try
               {

                  Integer[] rec0 = RecordScore.getScores(arg0);
                  Integer[] rec1 = RecordScore.getScores(arg1);

                  for (int i = 0; i < rec0.length; i++)
                  {
                     if (rec0[i] != rec1[i])
                        return rec0[i].compareTo(rec1[i]);
                  }

                  return 0;
               }
               catch (SQLException e)
               {
                  e.printStackTrace();
               }

               return 0;
            }

         });

      }
      catch (SQLException e)
      {
         e.printStackTrace();
      }

      mapper.put("records", records);
      return mapper;
   }

   @Override
   public Class generates()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public String linkParams(Object arg0)
   {
      // TODO Auto-generated method stub
      return null;
   }

}
