package eu.mapleconsulting.adminnotes.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.util.RecordHandler;
import eu.mapleconsulting.adminnotes.util.UUIDFetcher;
import eu.mapleconsulting.adminnotes.util.Utils;

public class CreateRecordCommand extends CommandPattern {

	private String notesRecordFolderPath;
	private File notesFolder;

	public CreateRecordCommand(AdminNotes plugin) {
		super("note","record");
		this.notesRecordFolderPath=plugin.getConfigManager().getNotesRecordFolderPath();
		this.notesFolder=plugin.getConfigManager().getNotesFolder();
		setDescription("Creates a record file with notes that have <author> as author");
        setUsage("/note record <author>");
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
			if(noteFiles.length==0){ executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED+""
					+ "Notes folder is empty");
					return true;
					}
			RecordHandler recordHandler=new RecordHandler(toBeWritten);
			recordHandler.createNewRecord(authorUUID, noteFiles);
			return recordHandler.saveFile(executor, "No notes found written by ",args[1]);			
		}catch (Exception e) {
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+
					ChatColor.DARK_RED+"Invalid player name");
			return true;
		}
		
	}

	@Override
	public void checkArgs(Player executor, String[] args) {
		// TODO Auto-generated method stub
		
	}

}
