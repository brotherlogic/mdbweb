package uk.co.brotherlogic.testbed;

public class TestObject {

	int number;
	
	public TestObject(String numberIn)
	{
		number = Integer.parseInt(numberIn);
	}
	
	public String getName()
	{
		return "Test Object poo : " + number;
	}
	
}
