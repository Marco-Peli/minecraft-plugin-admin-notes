package eu.mapleconsulting.adminnotes.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.exceptions.EmailNotFoundException;
import eu.mapleconsulting.adminnotes.util.Utils;
import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.EmailManager;

public class DisplayMailCommand extends CommandPattern {

	private EmailManager emailManager;
	
	public DisplayMailCommand(AdminNotes plugin) {
		super("note", "displaymail");
		this.emailManager=plugin.getEmailManager();
		setDescription("Displays your e-mail saved in the database.");
        setUsage("/note displaymail");
        setArgumentRange(1, 1);
        setIdentifier("displaymail");
        setPermission("note.command.displaymail");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		try{
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.GOLD+"Your e-mail is: " +
		ChatColor.WHITE+emailManager.getPlayerEmail(executor));
		}catch (EmailNotFoundException e){
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+
					ChatColor.DARK_RED+"You have no e-mail saved in database.");
		}
		return true;
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}

}
