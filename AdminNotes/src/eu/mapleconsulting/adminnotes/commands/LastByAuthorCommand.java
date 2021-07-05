package eu.mapleconsulting.adminnotes.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.util.RecordHandler;
import eu.mapleconsulting.adminnotes.util.UUIDFetcher;
import eu.mapleconsulting.adminnotes.util.Utils;

public class LastByAuthorCommand extends CommandPattern {

	private File notesFolder;
	private String notesFolderPath;
	private final String errorMessage="No notes written by ";
	private final String message=" NOTES WRITTEN BY ";
	
	public LastByAuthorCommand(AdminNotes plugin) {
		super("note","lastbyauthor");
		this.notesFolder=plugin.getConfigManager().getNotesFolder();
		this.notesFolderPath=plugin.getConfigManager().getNotesFolderPath();
		setDescription("Displays the last (or all) #notes "
				+ "that have as author <author> and as target <target>");
        setUsage("/note lastbyauthor <author> <#note/target> <#note>");
        setArgumentRange(2, 4);
        setIdentifier("lastbyauthor");
        setPermission("note.command.lastbyauthor");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		String authorUUID;
		
		try{
		authorUUID=UUIDFetcher.getUUIDFromName(args[1]);
		}catch(Exception e){
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED+
					"The selected author does not exist");
			return true;
		}
		try{
			File[] noteFiles=notesFolder.listFiles();
			if(noteFiles.length==0){ executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED+""
					+ "Notes folder is empty");
					return true;
					}
			RecordHandler recordHandler=new RecordHandler(new File("useless.yml"));
			recordHandler.createNewRecord(authorUUID, noteFiles);
			
			if(args.length==2){
				return recordHandler.displayNotes(executor, 
						args[1], errorMessage, "ALL"+message,"");
			}else{
				int notesToDisplay=Integer.parseInt(args[2].trim());
				return recordHandler.displayNotes(executor, 
						notesToDisplay, args[1], errorMessage, 
							message, message.toLowerCase());
			}
		}catch(NumberFormatException e){
			String targetPlayerUUID;
			try{
			targetPlayerUUID=UUIDFetcher.getUUIDFromName(args[2]).toLowerCase();
			}catch(Exception ex){
				executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED
						+"The selected player does not exist");
				return true;
			}
			File targetPlayerNote=new File(notesFolderPath+targetPlayerUUID+".yml");
			if(!targetPlayerNote.exists()){
				executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED+
						"No note found about"+ args[2]);
				return true;
			}
			File[] noteFiles={targetPlayerNote};
			RecordHandler recordHandler=new RecordHandler(new File("useless.yml"));
			if(args.length==3){
			recordHandler.createNewRecord(authorUUID, noteFiles);
			return recordHandler.displayNotes(executor, 
					args[1], errorMessage, "ALL"+message," SU "+ args[2]);
			}else{
				try{
					int notesToDisplay=Integer.parseInt(args[3].trim());
					recordHandler.createNewRecord(authorUUID, noteFiles);
					
					return recordHandler.notesWithAuthorAndTarget(executor, 
							notesToDisplay, errorMessage, args[1],args[2]);
					
				}catch(NumberFormatException en){
					executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED
							+"You have to insert a integer number of notes");
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
