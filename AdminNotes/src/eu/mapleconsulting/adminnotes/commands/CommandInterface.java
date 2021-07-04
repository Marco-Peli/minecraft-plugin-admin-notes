package eu.mapleconsulting.adminnotes.commands;

import org.bukkit.entity.Player;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;

public interface CommandInterface {
	
	boolean execute(Player executor, String[] args);

    String getDescription();

    String getIdentifier();

    int getMaxArguments();
    
    String getCommandIntestation();

    int getMinArguments();

    String getName();

    String getPermission();

    String getUsage();

    boolean isThisCommand(String cmdName, String inputIdentifier);
    
    boolean isValidArgsRange(int argslength);
    
    void checkArgs(Player executor, String[] args) throws CommandFormatException;
}
