package uk.co.brotherlogic.mdbweb.track;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.TemplatePage;
import uk.co.brotherlogic.mdb.record.GetRecords;
import uk.co.brotherlogic.mdb.record.Track;

public class Search extends TemplatePage
{

   @Override
   protected Map<String, Object> convertParams(List<String> arg0, Map<String, String> params)
   {
      Map<String, Object> oMap = new TreeMap<String, Object>();

      try
      {
         String query = params.get("q");
         List<Track> tracks = new LinkedList<Track>(GetRecords.create().searchTracks(query));
         Collections.sort(tracks, new Comparator<Track>()
         {

            @Override
            public int compare(Track o1, Track o2)
            {
               return o1.getTitle().compareTo(o2.getTitle());
            }

         });
         oMap.put("tracks", tracks);
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
