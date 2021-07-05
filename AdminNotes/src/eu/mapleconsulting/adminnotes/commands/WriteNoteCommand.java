package eu.mapleconsulting.adminnotes.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.util.FileUtilities;
import eu.mapleconsulting.adminnotes.util.NoteHandler;
import eu.mapleconsulting.adminnotes.util.UUIDFetcher;
import eu.mapleconsulting.adminnotes.util.Utils;

public class WriteNoteCommand extends CommandPattern {

	private String notesFolderPath;
	private int maxNoteLength;

	public WriteNoteCommand(AdminNotes plugin) {
		super("note","WriteNote");
		this.notesFolderPath=plugin.getConfigManager().getNotesFolderPath();
		this.maxNoteLength=plugin.getConfigManager().getMaxNoteLength();
		setDescription("Write a note on player <player>");
		setUsage("/note write <player> <note>, max " + plugin.getConfigManager().getMaxNoteLength() + " chars");
		setArgumentRange(3, 1000);
		setIdentifier("write");
		setPermission("note.command.write");
	}

	@Override
	public boolean execute(Player player,
			String[] args) {
		try {
			String filePath=UUIDFetcher.getUUIDFromName(args[1]).toLowerCase();
			File playerNote = new File(notesFolderPath+ filePath+".yml");
			String tmpNote=FileUtilities.argumentsAsString(2,(args.length),args);
			if(tmpNote.length()>maxNoteLength){
				player.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+
						ChatColor.DARK_RED+"Note is too long, max: " + maxNoteLength + " chars");
				return true;
			}
				NoteHandler noteHandler=new NoteHandler(playerNote);
				noteHandler.writeNewNote(player, args);
				return true;
		}catch(Exception e){
			player.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+
					ChatColor.DARK_RED+"An error occurred during the write, player not found.");
		}
		return true;
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}

}
