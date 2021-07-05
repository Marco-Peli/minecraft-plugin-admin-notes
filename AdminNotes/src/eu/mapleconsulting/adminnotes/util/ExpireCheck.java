package eu.mapleconsulting.adminnotes.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mapleconsulting.adminnotes.AdminNotes;

public class ExpireCheck extends BukkitRunnable {

	private AdminNotes plugin;
	private boolean choice;
	private Player executor;
	private final long DAYFACTOR=86400000;
	private final String expiredNoteFileFlagPath = "expired_note";
	
	public ExpireCheck(AdminNotes plugin, boolean choice, Player executor) {
		this.plugin=plugin;
		this.choice=choice;
		this.executor=executor;
	}

	@Override
	public void run() {
		NoteHandler noteToCheck;
		if(choice){
			//flagga le note vecchie come scadute
			int notesExpired=0;
			for(File f: plugin.getConfigManager().getNotesFolder().listFiles()){
				noteToCheck= new NoteHandler(f);
				if((System.currentTimeMillis()-f.lastModified())>(plugin.getConfigManager().getNotesExpiringTime()*DAYFACTOR)){
					noteToCheck.getToBeWrittenHandler().set(expiredNoteFileFlagPath,true);
					try {
						noteToCheck.getToBeWrittenHandler().save(f);
					} catch (IOException e) {
						executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED+
								"An error occurred during the save of the file!");
					}
					notesExpired++;
				}
			}
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.RED+""+notesExpired+
					ChatColor.GOLD+" notes have been flagged as expired!");
		}
		else{
			//flagga le note scadute come NON scadute
			for(File f: plugin.getConfigManager().getNotesFolder().listFiles()){
				noteToCheck= new NoteHandler(f);
				    if(noteToCheck.getToBeWrittenHandler().getBoolean(expiredNoteFileFlagPath)==true){
				    	noteToCheck.getToBeWrittenHandler().set(expiredNoteFileFlagPath,false);
				    	try {
							noteToCheck.getToBeWrittenHandler().save(f);
						} catch (IOException e) {
							executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.DARK_RED+"An error occurred during the save of the file!");
						}
				    }
					
				}
			executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.GOLD+"All notes have been flagged as NOT expired!");
		}

	}

}
