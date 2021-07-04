package eu.mapleconsulting.adminnotes;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

	private AdminNotes plugin;

	public PlayerJoinListener(AdminNotes plugin) {
		this.plugin = plugin;
	}


	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		new Notify(this.plugin, event).runTaskLaterAsynchronously(plugin, 15);
	}
}
