package me.novus.locks;

import java.util.List;

public class Util 
{
	/**
	 * Convert a string list to upper case.
	 * 
	 * @param list - The list to convert
	 * @return The list with all elements capitalized.
	 */
	public static List<String> toUpperCase(List<String> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			list.set(i, list.get(i).toUpperCase());
		}
		
		return list;
	}
	
	/**
	 * Checks if a list contains a string, ignoring case.
	 * 
	 * @param bLOCKS - The list to check
	 * @param string - The string to look for
	 * @return true if this list contains the specified element, ignoring case
	 */
	public static boolean containsIgnoreCase(List<String> list, String string)
	{
		return toUpperCase(list).contains(string.toUpperCase());
	}
}
