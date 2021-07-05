package eu.mapleconsulting.adminnotes.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mapleconsulting.adminnotes.AdminNotes;

public class AutoAbortThread extends BukkitRunnable {

	private AdminNotes plugin;
	private Player executor;
	
	public AutoAbortThread(AdminNotes plugin, Player executor) {
		this.plugin = plugin;
		this.executor = executor;
	}

	@Override
	public void run() {
		if(plugin.getToBeConfirmed().containsKey(executor.getUniqueId().toString())){
			 executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.GOLD+
						"The note deletion on player "+ChatColor.WHITE+
						Bukkit.getPlayer(UUID.fromString(plugin.getToBeConfirmed().get(executor.getUniqueId().toString()))).getName()
						+ChatColor.GOLD+ " has been automatically aborted.");
			 plugin.removeExecutor(executor);
		}

	}

}
