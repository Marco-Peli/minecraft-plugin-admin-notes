package eu.mapleconsulting.adminnotes.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.util.ExpireCheck;

public class FlagAsNotExpiredCommand extends CommandPattern {

	private File notesFolder;
	private AdminNotes plugin;
	
	public FlagAsNotExpiredCommand(AdminNotes plugin) {
		super("note", "flagnotexpired");
		this.plugin=plugin;
		setDescription("Contrassegna le vecchie note come scadute, pronte per essere eliminate");
        setUsage("/note flagnotexpired");
        setArgumentRange(1, 1);
        setIdentifier("flagnotexpired");
        setPermission("note.command.flagnotexpired");
        notesFolder=plugin.getConfigManager().getNotesFolder();
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		File[] notesList=notesFolder.listFiles();
		if(notesList.length==0){
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"La cartella note e' vuota");
			return true;
		}
		new ExpireCheck(plugin, false, executor).runTaskAsynchronously(this.plugin);
		return true;
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		File[] notesList=notesFolder.listFiles();
		if(notesList.length==0){
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"La cartella note e' vuota");
			throw new CommandFormatException();
		}
	}
}
