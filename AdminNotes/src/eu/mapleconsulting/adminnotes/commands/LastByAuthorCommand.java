package eu.mapleconsulting.adminnotes.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.util.RecordHandler;
import eu.mapleconsulting.adminnotes.util.UUIDFetcher;

public class LastByAuthorCommand extends CommandPattern {

	private File notesFolder;
	private String notesFolderPath;
	private final String errorMessage="Nessuna nota scritta da ";
	private final String message=" NOTE SCRITTE DA ";
	
	public LastByAuthorCommand(AdminNotes plugin) {
		super("note","lastbyauthor");
		this.notesFolder=plugin.getConfigManager().getNotesFolder();
		this.notesFolderPath=plugin.getConfigManager().getNotesFolderPath();
		setDescription("Visualizza le ultime (o tutte) #note "
				+ "che hanno come autore <autore> e come destinatario <target>");
        setUsage("/note lastbyauthor <autore> <#note/target> <#note>");
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
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"L'autore cercato non esiste");
			return true;
		}
		try{
			File[] noteFiles=notesFolder.listFiles();
			if(noteFiles.length==0){ executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+""
					+ "Cartella note vuota");
					return true;
					}
			RecordHandler recordHandler=new RecordHandler(new File("useless.yml"));
			recordHandler.createNewRecord(authorUUID, noteFiles);
			
			if(args.length==2){
				return recordHandler.displayNotes(executor, 
						args[1], errorMessage, "TUTTE LE"+message,"");
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
				executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Il giocatore target non esiste");
				return true;
			}
			File targetPlayerNote=new File(notesFolderPath+targetPlayerUUID+".yml");
			if(!targetPlayerNote.exists()){
				executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Nessuna nota su "+ args[2] 
						+" trovata");
				return true;
			}
			File[] noteFiles={targetPlayerNote};
			RecordHandler recordHandler=new RecordHandler(new File("useless.yml"));
			if(args.length==3){
			recordHandler.createNewRecord(authorUUID, noteFiles);
			return recordHandler.displayNotes(executor, 
					args[1], errorMessage, "TUTTE LE"+message," SU "+ args[2]);
			}else{
				try{
					int notesToDisplay=Integer.parseInt(args[3].trim());
					recordHandler.createNewRecord(authorUUID, noteFiles);
					
					return recordHandler.notesWithAuthorAndTarget(executor, 
							notesToDisplay, errorMessage, args[1],args[2]);
					
				}catch(NumberFormatException en){
					executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Devi inserire un numero intero");
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
