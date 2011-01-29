package uk.co.brotherlogic.mdbweb.label;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.TemplatePage;
import uk.co.brotherlogic.mdb.label.GetLabels;
import uk.co.brotherlogic.mdb.label.Label;
import uk.co.brotherlogic.mdb.record.Record;

public class Default extends TemplatePage
{

   @Override
   public Class generates()
   {
      // TODO Auto-generated method stub
      return Label.class;
   }

   @Override
   public String linkParams(Object arg0)
   {
      // TODO Auto-generated method stub
      return "" + ((Label) arg0).getNumber();
   }

   public List<Record> sort(List<Record> records)
   {
      Collections.sort(records, new Comparator<Record>()
      {

         @Override
         public int compare(Record o1, Record o2)
         {
            try
            {
               return o1.getCatNoString().compareTo(o2.getCatNoString());
            }
            catch (SQLException e)
            {
               e.printStackTrace();
            }

            return 0;
         }

      });

      return records;
   }

   @Override
   protected Map<String, Object> convertParams(List<String> elems, Map<String, String> params)
   {
      Map<String, Object> paramMap = new TreeMap<String, Object>();
      try
      {
         System.err.println("ELEMS = " + elems.get(0));
         int labelID = Integer.parseInt(elems.get(0));
         Label label = GetLabels.create().getLabel(labelID);

         paramMap.put("label", label);

      }
      catch (SQLException e)
      {
         e.printStackTrace();
      }

      return paramMap;
   }
}
