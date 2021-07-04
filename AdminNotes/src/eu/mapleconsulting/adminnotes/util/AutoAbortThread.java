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
			 executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.GOLD+
						"L'eliminazione del file di note su "+ChatColor.WHITE+
						Bukkit.getPlayer(UUID.fromString(plugin.getToBeConfirmed().get(executor.getUniqueId().toString()))).getName()
						+ChatColor.GOLD+ " e' stata automaticamente annullata.");
			 plugin.removeExecutor(executor);
		}

	}

}
