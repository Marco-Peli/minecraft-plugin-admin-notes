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
					noteToCheck.getToBeWrittenHandler().set("nota_scaduta",true);
					try {
						noteToCheck.getToBeWrittenHandler().save(f);
					} catch (IOException e) {
						executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"E' avvenuto un problema durante il salvataggio del file :/");
					}
					notesExpired++;
				}
			}
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.RED+""+notesExpired+
					ChatColor.GOLD+" Note sono state flaggate come scadute!");
		}
		else{
			//flagga le note scadute come NON scadute
			for(File f: plugin.getConfigManager().getNotesFolder().listFiles()){
				noteToCheck= new NoteHandler(f);
				    if(noteToCheck.getToBeWrittenHandler().getBoolean("nota_scaduta")==true){
				    	noteToCheck.getToBeWrittenHandler().set("nota_scaduta",false);
				    	try {
							noteToCheck.getToBeWrittenHandler().save(f);
						} catch (IOException e) {
							executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"E' avvenuto un problema durante il salvataggio del file :/");
						}
				    }
					
				}
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.GOLD+"Tutte le note sono state flaggate come NON scadute!");
		}

	}

}
