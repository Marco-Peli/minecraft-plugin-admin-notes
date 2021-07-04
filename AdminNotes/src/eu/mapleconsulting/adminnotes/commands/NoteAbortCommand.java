package eu.mapleconsulting.adminnotes.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;

public class NoteAbortCommand extends CommandPattern {

	private AdminNotes plugin;

	public NoteAbortCommand(AdminNotes plugin) {
		super("note", "abort");
		this.plugin=plugin;
		setDescription("Annulla l'eliminazione di un file di note");
		setUsage("/note abort");
		setArgumentRange(1, 1);
		setIdentifier("abort");
		setPermission("note.command.abort");
	}
	
	@Override
	public boolean execute(Player executor, String[] args) {
		if(plugin.getToBeConfirmed().containsKey(executor.getUniqueId().toString())){
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.GOLD+
					"Hai hai correttamente annullato la cancellazione del file di note su "+ChatColor.WHITE+
					Bukkit.getPlayer(UUID.fromString(plugin.getToBeConfirmed().get(executor.getUniqueId().toString()))).getName()
					+ChatColor.GOLD+ "!");
			plugin.removeExecutor(executor);
			return true;
		}else{
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Non hai "
					+ "ancora selezionato nessuna nota da eliminare");
			return true;
		}
		
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}
}


