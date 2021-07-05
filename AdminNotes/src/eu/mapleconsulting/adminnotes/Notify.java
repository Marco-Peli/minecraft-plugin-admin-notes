package eu.mapleconsulting.adminnotes;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mapleconsulting.adminnotes.util.NoteHandler;
import eu.mapleconsulting.adminnotes.util.Utils;

public class Notify extends BukkitRunnable {

	private AdminNotes plugin;
	private PlayerJoinEvent event;
	
	public Notify(AdminNotes plugin, PlayerJoinEvent event) {
		this.plugin = plugin;
		this.event = event;
	}

	@Override
	public void run() {
		notifyPlayers();
	}
	
	private void notifyPlayers(){
		
		File notesOnPlayer=new File(plugin.getConfigManager().getNotesFolderPath()+event.getPlayer().getUniqueId().toString()+".yml");
		if(notesOnPlayer.exists()){
			NoteHandler note=new NoteHandler(notesOnPlayer);
			note.seeLast();
			boolean isWarned=note.getNotesToBeDisplayed().size()>=plugin.getConfigManager().getWarnings();
			if(isWarned){
				if(plugin.getConfigManager().isNotifyPlayer()){
					event.getPlayer().sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.GOLD+
							"You have more than "+ ChatColor.WHITE+""+plugin.getConfigManager().getWarnings()+ChatColor.GOLD+
							" reports on you, play fairly! Contact the staff for further informations.");
				}
				if(plugin.getConfigManager().isNotifyStaff()){
					notifyStaff();
				}
			}
		}
		}
	
	
	private void notifyStaff(){
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if(p.hasPermission("note.command.notify")){
				p.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.GOLD+"Player "+
						ChatColor.WHITE+event.getPlayer().getName()+ChatColor.GOLD+" that just logged has more than "+
						ChatColor.WHITE+""+plugin.getConfigManager().getWarnings()+ChatColor.GOLD+" reports.");
			}
		}
	}
}
