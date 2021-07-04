package eu.mapleconsulting.adminnotes.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.ConfigManager;
import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.EmailManager;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.exceptions.EmailNotFoundException;
import eu.mapleconsulting.adminnotes.util.EmailSender;

public class SendNoteByMailCommand extends CommandPattern {

	private EmailManager emailManager;
	private ConfigManager configManager;
	private AdminNotes plugin;
	
	public SendNoteByMailCommand(AdminNotes plugin) {
		super("note", "sendmenote");
		this.plugin=plugin;
		this.emailManager = plugin.getEmailManager();
		this.configManager=plugin.getConfigManager();
		setDescription("Invia via mail il file con la note su <giocatore>");
        setUsage("/note sendmenote <giocatore>");
        setArgumentRange(2, 2);
        setIdentifier("sendmenote");
        setPermission("note.command.sendmenote");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		try{
			plugin.getEmailManager().getPlayerEmail(executor);
		}catch(EmailNotFoundException ex){
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Nessuna mail trovata");
			return true;
		}
		try{
		(new EmailSender(emailManager, configManager,
				executor, args, configManager.getNotesFolderPath(),
				"Darkworld Notes Service", "File di note sul giocatore "+
				args[1], "File di note su "+args[1],configManager.getSmtpAddress())).runTaskAsynchronously(plugin);
		return true;
		}catch(Exception e){
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Tempo scaduto per l'invio.");
			return true;
		}
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}

}
