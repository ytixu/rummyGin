package ca.mcgill.cs.comp303fall2013.lecture03.fixed;

public final class Person
{
	private String aName;
	private int aAge;
	
	public Person( String pName, int pAge)
	{
		aName = pName;
		aAge = pAge;
	}

	/**
	 * @return the aName
	 */
	public String getaName()
	{
		return aName;
	}

	/**
	 * @return the aAge
	 */
	public int getaAge()
	{
		return aAge;
	}
	
	public String toString()
	{
		return aName;
	}
	
	public static void main(String[] args)
	{
		Person jerry = new Person("Jerry", 23);
		
		Person alice = jerry;
		
		System.out.println(alice);
	}
	
}
