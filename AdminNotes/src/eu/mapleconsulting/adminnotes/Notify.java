package eu.mapleconsulting.adminnotes;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mapleconsulting.adminnotes.util.NoteHandler;

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
					event.getPlayer().sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.GOLD+"Hai più di "+
							ChatColor.WHITE+""+plugin.getConfigManager().getWarnings()+ChatColor.GOLD+" richiami a tuo carico,"
							+ " comportati correttamente! Contatta lo staff per maggiori informazioni.");
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
				p.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.GOLD+"Il giocatore "+
						ChatColor.WHITE+event.getPlayer().getName()+ChatColor.GOLD+" appena loggato ha più di "+
						ChatColor.WHITE+""+plugin.getConfigManager().getWarnings()+ChatColor.GOLD+" richiami all'attivo.");
			}
		}
	}
}
