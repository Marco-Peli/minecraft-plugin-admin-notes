package eu.mapleconsulting.adminnotes.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;

public class ReloadCommand extends CommandPattern {

	AdminNotes plugin;
	
	public ReloadCommand(AdminNotes plugin) {
		super("note", "reload");
		this.plugin=plugin;
		setDescription("Ricarica il file di configurazione");
        setUsage("/note reload");
        setArgumentRange(1, 1);
        setIdentifier("reload");
        setPermission("note.command.reload");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		plugin.onDisable();
		plugin.onEnable();
		executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.GREEN+"File di configurazione "
				+ "di DevilNotes ricaricato correttamente");
		return true;
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}

}
