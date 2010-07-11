package uk.co.brotherlogic.testbed;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.TemplatePage;

public class Tester extends TemplatePage{

	@Override
	protected Map<String, Object> convertParams(List<String> paramList,
			Map<String, String> paramMap) {
		Map<String,Object> objMap = new TreeMap<String, Object>();
		objMap.put("test", new TestObject(paramList.get(0)));
		return objMap;
	}

}
