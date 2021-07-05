package eu.mapleconsulting.adminnotes.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.ConfigManager;
import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.EmailManager;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.exceptions.EmailNotFoundException;
import eu.mapleconsulting.adminnotes.util.EmailSender;
import eu.mapleconsulting.adminnotes.util.Utils;

public class SendNoteByMailCommand extends CommandPattern {

	private EmailManager emailManager;
	private ConfigManager configManager;
	private AdminNotes plugin;
	
	public SendNoteByMailCommand(AdminNotes plugin) {
		super("note", "sendmenote");
		this.plugin=plugin;
		this.emailManager = plugin.getEmailManager();
		this.configManager=plugin.getConfigManager();
		setDescription("Send to your saved e-mail the note file about player <player>");
        setUsage("/note sendmenote <player>");
        setArgumentRange(2, 2);
        setIdentifier("sendmenote");
        setPermission("note.command.sendmenote");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		try{
			plugin.getEmailManager().getPlayerEmail(executor);
		}catch(EmailNotFoundException ex){
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+
					ChatColor.DARK_RED+"You have not saved your e-mail. /note addmail <e-mail> to save.");
			return true;
		}
		try{
		(new EmailSender(emailManager, configManager,
				executor, args, configManager.getNotesFolderPath(),
				"AdminNotes", "Notes file on player "+
				args[1], "Note files on player "+args[1],configManager.getSmtpAddress())).runTaskAsynchronously(plugin);
		return true;
		}catch(Exception e){
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+
					ChatColor.DARK_RED+"Error during the delivery of the e-mail.");
			return true;
		}
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}

}
