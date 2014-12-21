package ca.mcgill.cs.comp303fall2013.lecture03.fixed;

import java.util.ArrayList;

public class GameStatistics
{
	private ArrayList<GameRecord> aRecords = new ArrayList<GameRecord>();
	
	// OR, consider hiding the entire record
	public void add(GameRecord pRecord)
	{
		aRecords.add(pRecord);
	}
	
	public ArrayList<GameRecord> getRecords()
	{
		return aRecords;
	}
	
	
}
