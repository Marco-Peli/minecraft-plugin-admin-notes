package eu.mapleconsulting.adminnotes.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.util.FileUtilities;
import eu.mapleconsulting.adminnotes.util.NoteHandler;
import eu.mapleconsulting.adminnotes.util.UUIDFetcher;

public class WriteNoteCommand extends CommandPattern {

	private String notesFolderPath;
	private int maxNoteLength;

	public WriteNoteCommand(AdminNotes plugin) {
		super("note","WriteNote");
		this.notesFolderPath=plugin.getConfigManager().getNotesFolderPath();
		this.maxNoteLength=plugin.getConfigManager().getMaxNoteLength();
		setDescription("Scrivi una nota a carico di un giocatore");
		setUsage("/note write <giocatore> [nota], max " + plugin.getConfigManager().getMaxNoteLength() + " chars");
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
				player.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Nota troppo lunga, max: " +
						maxNoteLength + " chars");
				return true;
			}
				NoteHandler noteHandler=new NoteHandler(playerNote);
				noteHandler.writeNewNote(player, args);
				return true;
		}catch(Exception e){
			player.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Errore durante la scrittura su file,"
					+ " giocatore inesistente");
		}
		return true;
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}

}
