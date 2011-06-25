package uk.co.brotherlogic.mdbweb.label;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.TemplatePage;
import uk.co.brotherlogic.mdb.label.GetLabels;
import uk.co.brotherlogic.mdb.label.Label;

public class Search extends TemplatePage
{

   @Override
   protected Map<String, Object> convertParams(List<String> arg0, Map<String, String> params)
   {
      Map<String, Object> oMap = new TreeMap<String, Object>();

      try
      {
         String query = params.get("q");
         Collection<Label> labels = GetLabels.create().search(query);

         oMap.put("labels", labels);
         oMap.put("query", query);
      }
      catch (SQLException e)
      {
         e.printStackTrace();
      }

      return oMap;
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
