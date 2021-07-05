package eu.mapleconsulting.adminnotes.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.util.Utils;

public class NoteAbortCommand extends CommandPattern {

	private AdminNotes plugin;

	public NoteAbortCommand(AdminNotes plugin) {
		super("note", "abort");
		this.plugin=plugin;
		setDescription("Abort the deletion of a note file.");
		setUsage("/note abort");
		setArgumentRange(1, 1);
		setIdentifier("abort");
		setPermission("note.command.abort");
	}
	
	@Override
	public boolean execute(Player executor, String[] args) {
		if(plugin.getToBeConfirmed().containsKey(executor.getUniqueId().toString())){
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.GOLD+
					"You successfully canceled the deletion of note file on player "+ChatColor.WHITE+
					Bukkit.getPlayer(UUID.fromString(plugin.getToBeConfirmed().get(executor.getUniqueId().toString()))).getName()
					+ChatColor.GOLD+ "!");
			plugin.removeExecutor(executor);
			return true;
		}else{
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED+
					"You didn't select any not to be deleted.");
			return true;
		}
		
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}
}


