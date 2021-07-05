package eu.mapleconsulting.adminnotes.commands;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.exceptions.EmailNotFoundException;
import eu.mapleconsulting.adminnotes.util.Utils;
import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.EmailManager;

public class AddMyEmailCommand extends CommandPattern {

	private EmailManager emailManager;

	public AddMyEmailCommand(AdminNotes plugin) {
		super("note", "addmail");
		emailManager=plugin.getEmailManager();
		setDescription("Adds <e-mail> as your personal mail to the mail database. If no <e-mail> is provided, "
				+ "your e-mail will be removed from the database, if present.");
		setUsage("/note addmail <e-mail>");
		setArgumentRange(1, 2);
		setIdentifier("addmail");
		setPermission("note.command.addmail");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		if(args.length==1){
			try{
				emailManager.deleteMail(executor);
				executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.GOLD+
						"E-mail correctly deleted");
			}catch(EmailNotFoundException em){
				executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED+
						"No personal e-mail found in database");
			}catch(IOException ioex){
				executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED+
						"Error while deleting your e-mail from the database. Retry.");
			}
		}else{
			try {
				emailManager.addEmail(executor, args[1]);
				executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.GOLD+
						"E-mail address correctly added");
			} catch (Exception e) {
				executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED+
						"Error while adding your e-mail to the database. Retry.");
			}	
		}return true;
	}

	@Override
	public void checkArgs(Player executor, String[] args) {
		// TODO Auto-generated method stub
		
	}
}
