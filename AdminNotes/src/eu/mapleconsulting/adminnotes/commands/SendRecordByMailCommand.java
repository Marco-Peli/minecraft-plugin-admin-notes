package eu.mapleconsulting.adminnotes.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.ConfigManager;
import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.EmailManager;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.exceptions.EmailNotFoundException;
import eu.mapleconsulting.adminnotes.util.EmailSender;

public class SendRecordByMailCommand extends CommandPattern {

	private EmailManager emailManager;
	private ConfigManager configManager;
	private AdminNotes plugin;

	public SendRecordByMailCommand(AdminNotes plugin) {
		super("note", "sendmerecord");
		this.plugin=plugin;
		this.emailManager = plugin.getEmailManager();
		this.configManager=plugin.getConfigManager();
		setDescription("Invia via mail il file con il registro di note scritte da <autore>");
        setUsage("/note sendmerecord <autore>");
        setArgumentRange(2, 2);
        setIdentifier("sendmerecord");
        setPermission("note.command.sendmerecord");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		try{
			plugin.getEmailManager().getPlayerEmail(executor);
		}catch(EmailNotFoundException ex){
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Nessuna mail trovata");
			return true;
		}
		(new EmailSender(emailManager, configManager,
				executor, args, configManager.getNotesFolderPath(),
				"Darkworld RecordNotes Service", "Registro di note scritto da "+
				args[1], "Registro di note scritto da "+args[1], configManager.getSmtpAddress())).runTaskAsynchronously(plugin);
		return true;
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}

}
