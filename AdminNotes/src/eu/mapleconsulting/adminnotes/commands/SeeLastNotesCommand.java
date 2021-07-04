package eu.mapleconsulting.adminnotes.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.util.NoteHandler;
import eu.mapleconsulting.adminnotes.util.UUIDFetcher;

public class SeeLastNotesCommand extends CommandPattern {

	private String notesFolderPath;
	private final String errorMessage="Nessuna nota su ";
	private final String message=" NOTE SU ";
	
	public SeeLastNotesCommand(AdminNotes plugin) {
		super("note","seelast");
		this.notesFolderPath=plugin.getConfigManager().getNotesFolderPath();
		setDescription("Vedi le ultime <#note> a carico di <giocatore>, tutte se <#note> assente");
        setUsage("/note seelast <giocatore> <#note/authors>");
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
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Giocatore non trovato");
			return true;
		} 
		File note = new File(notesFolderPath+authorUUID+".yml");
			if(!note.exists()) {
				executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+
						"Nessuna nota sul giocatore trovata");
				return true;
			}
			NoteHandler noteHandler=new NoteHandler(note);
			
			if(args.length==2){
				noteHandler.seeLast();
				return noteHandler.displayNotes(executor, 
						args[1], errorMessage, "TUTTE LE"+message,"");
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
					executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+
							"Comando non valido");
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
