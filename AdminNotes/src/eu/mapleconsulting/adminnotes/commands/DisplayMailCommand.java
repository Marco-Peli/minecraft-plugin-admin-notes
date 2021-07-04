package eu.mapleconsulting.adminnotes.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.exceptions.EmailNotFoundException;
import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.EmailManager;

public class DisplayMailCommand extends CommandPattern {

	private EmailManager emailManager;
	
	public DisplayMailCommand(AdminNotes plugin) {
		super("note", "displaymail");
		this.emailManager=plugin.getEmailManager();
		setDescription("Visualizza la tua mail");
        setUsage("/note displaymail");
        setArgumentRange(1, 1);
        setIdentifier("displaymail");
        setPermission("note.command.displaymail");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		try{
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.GOLD+"La tua mail e' " +ChatColor.WHITE+emailManager.getPlayerEmail(executor));
		}catch (EmailNotFoundException e){
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Nessuna mail trovata");
		}
		return true;
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}

}
