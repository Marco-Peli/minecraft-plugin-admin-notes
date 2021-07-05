package eu.mapleconsulting.adminnotes.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.util.ExpireCheck;
import eu.mapleconsulting.adminnotes.util.Utils;

public class FlagAsNotExpiredCommand extends CommandPattern {

	private File notesFolder;
	private AdminNotes plugin;
	
	public FlagAsNotExpiredCommand(AdminNotes plugin) {
		super("note", "flagnotexpired");
		this.plugin=plugin;
		setDescription("Flags expired note files as NOT expired.");
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
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+
					ChatColor.DARK_RED+"Note folder is empty.");
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
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+
					ChatColor.DARK_RED+"Note folder is empty.");
			throw new CommandFormatException();
		}
	}
}
