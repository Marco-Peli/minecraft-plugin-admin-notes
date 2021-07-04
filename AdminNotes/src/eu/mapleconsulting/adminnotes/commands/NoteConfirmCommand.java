package eu.mapleconsulting.adminnotes.commands;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;

public class NoteConfirmCommand extends CommandPattern {

	private AdminNotes plugin;
	private String notesFolderPath;
	
	public NoteConfirmCommand(AdminNotes plugin) {
		super("note", "confirm");
		this.plugin=plugin;
		notesFolderPath=plugin.getConfigManager().getNotesFolderPath();
		setDescription("Conferma cancellazione nota");
        setUsage("/note confirm");
        setArgumentRange(1, 1);
        setIdentifier("confirm");
        setPermission("note.command.confirm");
	}
	@Override
	public boolean execute(Player executor, String[] args) {	
		if(plugin.getToBeConfirmed().containsKey(executor.getUniqueId().toString())){
			String targetUUID=plugin.getToBeConfirmed().get(executor.getUniqueId().toString());
			String targetName=Bukkit.getPlayer(UUID.fromString(targetUUID)).getName();
			return deleteNote(executor, targetName);
		}else{
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Non hai "
					+ "ancora selezionato nessuna nota da eliminare");
			return true;
		}
		
	}
	
	public boolean deleteNote(Player executor, String targetName){
		String fileName=notesFolderPath+plugin.getToBeConfirmed().get(executor.getUniqueId().toString())+".yml";
		File note = new File(fileName);
		note.delete();
		plugin.removeExecutor(executor);
		executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.GOLD+
				"File di note su "+ChatColor.WHITE+targetName+ChatColor.GOLD+ " eliminato");
		return true;
	}
	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}

}
