package eu.mapleconsulting.adminnotes.util;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import eu.mapleconsulting.adminnotes.ConfigManager;

public class FileUtilities {

	public static String argumentsAsString(int startingIndex, int endingIndex, String[] args){
		StringBuilder buffer = new StringBuilder();
		int i=startingIndex;
		for(; i < endingIndex; i++)
		{
			buffer.append(' ').append(args[i]);
		}

		return buffer.toString();
	}	


	public static void notify(PlayerJoinEvent evt, ConfigManager configManager){
		File notesOnPlayer=new File(configManager.getNotesFolderPath()+evt.getPlayer().getUniqueId().toString()+".yml");
		if(notesOnPlayer.exists()){
			NoteHandler note=new NoteHandler(notesOnPlayer);
			note.seeLast();
			boolean isWarned=note.getNotesToBeDisplayed().size()>=configManager.getWarnings();
			if(isWarned){
				if(configManager.isNotifyPlayer()){
					evt.getPlayer().sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.GOLD+"Hai più di "+
							ChatColor.WHITE+""+configManager.getWarnings()+ChatColor.GOLD+" richiami a tuo carico,"
							+ " comportati correttamente! Contatta lo staff per maggiori informazioni.");
				}
				if(configManager.isNotifyStaff()){
					notifyStaff(evt, configManager);
				}
			}
		}
	}
	
	private static void notifyStaff(PlayerJoinEvent evt, ConfigManager configManager){
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if(p.hasPermission("note.command.notify")){
				p.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.GOLD+"Il giocatore "+
						ChatColor.WHITE+evt.getPlayer().getName()+ChatColor.GOLD+" appena loggato ha più di "+
						ChatColor.WHITE+""+configManager.getWarnings()+ChatColor.GOLD+" richiami all'attivo.");
			}
		}
	}
	
}