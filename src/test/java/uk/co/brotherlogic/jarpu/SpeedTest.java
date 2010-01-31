package uk.co.brotherlogic.jarpu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import junit.framework.TestCase;
import uk.co.brotherlogic.jarpur.LinkTable;
import uk.co.brotherlogic.jarpur.Page;
import uk.co.brotherlogic.jarpur.replacers.SimpleReplacer;
import uk.co.brotherlogic.mdb.Connect;
import uk.co.brotherlogic.mdbweb.record.RecordPage;

public class SpeedTest extends TestCase {

	public void testSpeed() {

		try {
			// Build the record page for the Girl Group Sound - Lost and Found
			long sTime = System.currentTimeMillis();
			Properties lProps = new Properties();
			lProps.load(new FileInputStream(new File("src/main/webapp/WEB-INF"
					+ "/links.properties")));
			SimpleReplacer.setLinkTable(new LinkTable(lProps));

			Page rPage = new RecordPage();
			Map<String, String> objMap = new TreeMap<String, String>();
			objMap.put("id", "6398");
			rPage.buildPage(objMap);

			long eTime = System.currentTimeMillis() - sTime;

			long dTime = Connect.getConnection().getTQueryTime();

			Connect.getConnection().printStats();
			System.err.println("Database calls = " + dTime + " ("
					+ Connect.getConnection().getSCount() + ")");
			System.err.println("Time to build page = " + (eTime - dTime));
		} catch (IOException e) {
			e.printStackTrace();
			assert (false);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assert (true);

	}
}
