package eu.mapleconsulting.adminnotes.util;

public class Utils {
	
	public static final String CONSOLE_LOG_PREFIX = "[AdminNotes] ";
	
	public static void printConsoleMsg(String msg)
	{
		System.out.println(CONSOLE_LOG_PREFIX + msg);
	}
}
