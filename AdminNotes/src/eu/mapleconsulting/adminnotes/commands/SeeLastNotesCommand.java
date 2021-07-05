package eu.mapleconsulting.adminnotes.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.util.NoteHandler;
import eu.mapleconsulting.adminnotes.util.UUIDFetcher;
import eu.mapleconsulting.adminnotes.util.Utils;

public class SeeLastNotesCommand extends CommandPattern {

	private String notesFolderPath;
	private final String errorMessage="No note on ";
	private final String message=" NOTES ON ";
	
	public SeeLastNotesCommand(AdminNotes plugin) {
		super("note","seelast");
		this.notesFolderPath=plugin.getConfigManager().getNotesFolderPath();
		setDescription("Displayes the last <#notes_number> on player <player>, all of them if <#notes_number> is missing");
        setUsage("/note seelast <player> <#notes_number/authors>");
        setArgumentRange(2, 3);
        setIdentifier("seelast");
        setPermission("note.command.seelast");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		String authorUUID;
		try {
			authorUUID=UUIDFetcher.getUUIDFromName(args[1]).toLowerCase();
			
		} catch (Exception e) {
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+
					ChatColor.DARK_RED+"Player not found.");
			return true;
		} 
		File note = new File(notesFolderPath+authorUUID+".yml");
			if(!note.exists()) {
				executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED+
						"No note on player found.");
				return true;
			}
			NoteHandler noteHandler=new NoteHandler(note);
			
			if(args.length==2){
				noteHandler.seeLast();
				return noteHandler.displayNotes(executor, 
						args[1], errorMessage, "ALL"+message,"");
			}else{
				try{
					
				int notesToDisplay=Integer.parseInt(args[2].trim());
				noteHandler.seeLast();
				return noteHandler.displayNotes(executor, 
						notesToDisplay, args[1], errorMessage, 
							message, message.toLowerCase());
				}catch(NumberFormatException e){
					if(args[2].equalsIgnoreCase("authors")){
						noteHandler.writeAuthors();
						return noteHandler.showAuthors(executor,args[1]);
					}else{
					executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED+
							"Invalid command, see /hote help.");
					}
				}
			}	
			return true;
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}

	
}
