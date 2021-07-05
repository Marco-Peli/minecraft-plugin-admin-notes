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
import eu.mapleconsulting.adminnotes.util.Utils;

public class DeleteNoteCommand extends CommandPattern {

	private AdminNotes plugin;
	private String notesFolderPath;

	public DeleteNoteCommand(AdminNotes plugin) {
		super("note", "delete");
		this.plugin=plugin;
		notesFolderPath=plugin.getConfigManager().getNotesFolderPath();
		setDescription("Deletes a note file about player <player>");
		setUsage("/note delete <player>");
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
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+
					ChatColor.DARK_RED+"Invalid player name");
			return true;
		}
		this.plugin.getToBeConfirmed().put(executor.getUniqueId().toString(), playerUUID);
		new AutoAbortThread(this.plugin, executor).runTaskLater(plugin, 400);
		executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.GOLD+
				"Type"+ ChatColor.WHITE+"/note confirm "+ChatColor.GOLD+"to confirm or "+ChatColor.WHITE+"/note abort "
				+ ChatColor.GOLD+"to abort. If you don't confirm, operation will be aborted in 20 seconds.");
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
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+
					ChatColor.DARK_RED+"Invalid player");
			throw new CommandFormatException();
		}

		File note = new File(fileName);
		if(!note.exists()) {
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED+
					"No note file found on the selected player.");
			throw new CommandFormatException();
		}

		if(this.plugin.getToBeConfirmed().containsKey(executor.getUniqueId().toString())){
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED+
					"you already selected a note file on "+ChatColor.WHITE+
					Bukkit.getPlayer(UUID.fromString(plugin.getToBeConfirmed().get(executor.getUniqueId().toString()))).getName()
					+ChatColor.DARK_RED+ " to be deleted!");
			throw new CommandFormatException();
		}
		
		if(!this.plugin.getToBeConfirmed().containsKey(executor.getUniqueId().toString()) &&  this.plugin.getToBeConfirmed().containsValue(targetUUID)){
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED+
					"The requested note file is already being deleted by another player.");
			throw new CommandFormatException();
		}

	}

}
