package eu.mapleconsulting.adminnotes.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.util.ExpireCheck;

public class FlagExpiredCommand extends CommandPattern{

	private File notesFolder;
	private AdminNotes plugin;
	
	public FlagExpiredCommand(AdminNotes plugin) {
		super("note", "flagexpired");
		this.plugin=plugin;
		this.notesFolder=plugin.getConfigManager().getNotesFolder();
		setDescription("Contrassegna le vecchie note come scadute, pronte per essere eliminate");
        setUsage("/note flagexpired");
        setArgumentRange(1, 1);
        setIdentifier("flagexpired");
        setPermission("note.command.flagexpired");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		File[] notesList=notesFolder.listFiles();
		if(notesList.length==0){
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"La cartella note e' vuota");
			return true;
		}
		new ExpireCheck(plugin, true, executor).runTaskAsynchronously(this.plugin);
		return true;
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}

}
