package eu.mapleconsulting.adminnotes.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.OldNotesEraser;
import eu.mapleconsulting.adminnotes.OldRecordNotesEraser;
import eu.mapleconsulting.adminnotes.util.Utils;

public class CleanCommand extends CommandPattern {
	
	private int notesExpiringTime;
	private int notesRecordExpireTime;
	private File notesFolder;
	private File notesRecordFolder;
	private OldNotesEraser oldNotesEraser=null;
	private OldRecordNotesEraser oldRecordNotesEraser=null;
	private AdminNotes plugin;
	
	public CleanCommand(AdminNotes plugin) {
		super("note","clean");
		this.plugin=plugin;
		setVariables(plugin);	
		setDescription("Deletes all note and record files marked as expired");
        setUsage("/note clean");
        setArgumentRange(1, 1);
        setIdentifier("clean");
        setPermission("note.command.clean");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.GOLD+
				"Scanning and deleting expired note and record files");
		oldNotesEraser=new OldNotesEraser(notesExpiringTime, notesFolder);
		oldRecordNotesEraser=new OldRecordNotesEraser(notesRecordExpireTime, notesRecordFolder);
		oldNotesEraser.runTaskAsynchronously(this.plugin);
		oldRecordNotesEraser.runTaskAsynchronously(this.plugin);
		return true;
	}

		
	private void setVariables(AdminNotes plugin){	
		notesExpiringTime=plugin.getConfigManager().getNotesExpiringTime();
		notesRecordExpireTime=plugin.getConfigManager().getNotesRecordExpireTime();
		notesFolder=plugin.getConfigManager().getNotesFolder();
		notesRecordFolder=plugin.getConfigManager().getNotesRecordFolder();
	}

	@Override
	public void checkArgs(Player executor, String[] args) {
		// TODO Auto-generated method stub
		
	}

}
