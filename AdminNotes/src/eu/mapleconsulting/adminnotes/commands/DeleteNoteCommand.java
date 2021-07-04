package eu.mapleconsulting.adminnotes.commands;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.util.AutoAbortThread;
import eu.mapleconsulting.adminnotes.util.UUIDFetcher;

public class DeleteNoteCommand extends CommandPattern {

	private AdminNotes plugin;
	private String notesFolderPath;

	public DeleteNoteCommand(AdminNotes plugin) {
		super("note", "delete");
		this.plugin=plugin;
		notesFolderPath=plugin.getConfigManager().getNotesFolderPath();
		setDescription("Cancella un file di note su un giocatore");
		setUsage("/note delete <giocatore>");
		setArgumentRange(2, 2);
		setIdentifier("delete");
		setPermission("note.command.delete");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		try{
			checkArgs(executor, args);
		}catch(CommandFormatException c){
			return true;
		}
		String playerUUID;
		try {
			playerUUID=UUIDFetcher.getUUIDFromName(args[1]);
		} catch (Exception e) {
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Giocatore inesistente");
			return true;
		}
		this.plugin.getToBeConfirmed().put(executor.getUniqueId().toString(), playerUUID);
		new AutoAbortThread(this.plugin, executor).runTaskLater(plugin, 400);
		executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.GOLD+
				"Digita"+ ChatColor.WHITE+"/note confirm "+ChatColor.GOLD+"per confermare o "+ChatColor.WHITE+"/note abort "
				+ ChatColor.GOLD+"per annullare, in 20 secondi verra' automaticamente annullata.");
		return true;
	}


	@Override
	public void checkArgs(Player executor, String[] args) throws CommandFormatException{
		String targetUUID;
		String fileName;
		try {
			targetUUID=UUIDFetcher.getUUIDFromName(args[1]);
			fileName=notesFolderPath+targetUUID+".yml";
		} catch (Exception e) {
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Giocatore inesistente");
			throw new CommandFormatException();
		}

		File note = new File(fileName);
		if(!note.exists()) {
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+
					"Nessun file di note sul giocatore trovato");
			throw new CommandFormatException();
		}

		if(this.plugin.getToBeConfirmed().containsKey(executor.getUniqueId().toString())){
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+
					"Hai gia' selezionato un file di note su "+ChatColor.WHITE+
					Bukkit.getPlayer(UUID.fromString(plugin.getToBeConfirmed().get(executor.getUniqueId().toString()))).getName()
					+ChatColor.DARK_RED+ " da eliminare!");
			throw new CommandFormatException();
		}
		
		if(!this.plugin.getToBeConfirmed().containsKey(executor.getUniqueId().toString()) &&  this.plugin.getToBeConfirmed().containsValue(targetUUID)){
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+
					"Il file di note richiesto e' gia' stato selezionato per l'eliminazione da un altro giocatore.");
			throw new CommandFormatException();
		}

	}

}
