package eu.mapleconsulting.adminnotes.commands;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.exceptions.EmailNotFoundException;
import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.EmailManager;

public class AddMyEmailCommand extends CommandPattern {

	private EmailManager emailManager;

	public AddMyEmailCommand(AdminNotes plugin) {
		super("note", "addmail");
		emailManager=plugin.getEmailManager();
		setDescription("Aggiunge la tua mail alla lista");
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
				executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.GOLD+"Mail correttamente eliminata");
			}catch(EmailNotFoundException em){
				executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"La tua mail non è stata cancellata"
						+ " perchè non presente");
			}catch(IOException ioex){
				executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Impossibile scrivere su file");
			}
		}else{
			try {
				emailManager.addEmail(executor, args[1]);
				executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.GOLD+"Indirizzo email correttamente aggiunto");
			} catch (Exception e) {
				executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Impossibile aggiungere la propria mail");
			}	
		}return true;
	}

	@Override
	public void checkArgs(Player executor, String[] args) {
		// TODO Auto-generated method stub
		
	}
}
