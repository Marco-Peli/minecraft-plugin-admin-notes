package eu.mapleconsulting.adminnotes.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.util.RecordHandler;
import eu.mapleconsulting.adminnotes.util.UUIDFetcher;

public class CreateRecordCommand extends CommandPattern {

	private String notesRecordFolderPath;
	private File notesFolder;

	public CreateRecordCommand(AdminNotes plugin) {
		super("note","record");
		this.notesRecordFolderPath=plugin.getConfigManager().getNotesRecordFolderPath();
		this.notesFolder=plugin.getConfigManager().getNotesFolder();
		setDescription("Crea un registro con le note che hanno come autore <autore>");
        setUsage("/note record <autore>");
        setArgumentRange(2, 2);
        setIdentifier("record");
        setPermission("note.command.record");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		try{
			String authorUUID=UUIDFetcher.getUUIDFromName(args[1]).toLowerCase();
			File toBeWritten=new File(notesRecordFolderPath+authorUUID+".yml");
			File[] noteFiles=notesFolder.listFiles();
			if(noteFiles.length==0){ executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+""
					+ "Cartella note vuota");
					return true;
					}
			RecordHandler recordHandler=new RecordHandler(toBeWritten);
			recordHandler.createNewRecord(authorUUID, noteFiles);
			return recordHandler.saveFile(executor, "Nessuna nota scritta da ",args[1]);			
		}catch (Exception e) {
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Giocatore inesistente");
			return true;
		}
		
	}

	@Override
	public void checkArgs(Player executor, String[] args) {
		// TODO Auto-generated method stub
		
	}

}
