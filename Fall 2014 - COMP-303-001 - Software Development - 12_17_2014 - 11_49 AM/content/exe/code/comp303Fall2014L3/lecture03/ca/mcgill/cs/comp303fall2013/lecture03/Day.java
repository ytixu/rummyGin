package ca.mcgill.cs.comp303fall2013.lecture03;

/**
 * Skeleton (interface) describing the functionality of a 
 * class that can represents days. There are different ways
 * to implement this functionality. See Section 3.3 for
 * a discussion.
 */
public class Day
{
	private int aDay;
	private int aMonth;
	private int aYear;
	private int aJulian;
	
	/**
	 * Constructs a days with a given year, month, and day
	 * of the Julian/Gregorian calendar. The Julian calendar
	 * is used for all days before October 15, 1582.
	 * @param pYear a year != 0
	 * @param pMonth a month between 1 and 12
	 * @param pDate a date between 1 and 31
	 */
	public Day(int pYear, int pMonth, int pDate) {}
	
	public int getyear() { return 0; }
	
	public int getMonth() { return 0; }
	
	public int getDate() { return 0; }
	
	/**
	 * Returns a days that is a certain number of days away from this day.
	 * @param n the number of days, can be negative.
	 * @return a day that is n days away from this one.
	 */
	public Day addDays(int n) { return new Day(0,0,0); }
	
	/**
	 * Returns the number of days between this day and another day.
	 * @param other the other day
	 * @return the number of days that this day is away from
	 * the other (> 0 if this day comes later)
	 */
	public int daysFrom(Day other) { return 0; }
}
