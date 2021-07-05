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

public class SendRecordByMailCommand extends CommandPattern {

	private EmailManager emailManager;
	private ConfigManager configManager;
	private AdminNotes plugin;

	public SendRecordByMailCommand(AdminNotes plugin) {
		super("note", "sendmerecord");
		this.plugin=plugin;
		this.emailManager = plugin.getEmailManager();
		this.configManager=plugin.getConfigManager();
		setDescription("Send to your saved e-mail the record with notes written by <author>");
        setUsage("/note sendmerecord <author>");
        setArgumentRange(2, 2);
        setIdentifier("sendmerecord");
        setPermission("note.command.sendmerecord");
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
		(new EmailSender(emailManager, configManager,
				executor, args, configManager.getNotesFolderPath(),
				"AdminNotes", "Note record of notes written by "+
				args[1], "Note record of notes written by "+args[1], configManager.getSmtpAddress())).runTaskAsynchronously(plugin);
		return true;
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}

}
