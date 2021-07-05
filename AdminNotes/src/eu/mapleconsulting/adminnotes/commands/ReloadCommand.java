package eu.mapleconsulting.adminnotes.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;
import eu.mapleconsulting.adminnotes.util.Utils;

public class ReloadCommand extends CommandPattern {

	AdminNotes plugin;
	
	public ReloadCommand(AdminNotes plugin) {
		super("note", "reload");
		this.plugin=plugin;
		setDescription("Reloads the configuration file.");
        setUsage("/note reload");
        setArgumentRange(1, 1);
        setIdentifier("reload");
        setPermission("note.command.reload");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		plugin.onDisable();
		plugin.onEnable();
		executor.sendMessage(ChatColor.WHITE+Utils.CONSOLE_LOG_PREFIX+ChatColor.GREEN+
				"Configuration successfully reloaded.");
		return true;
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}

}
