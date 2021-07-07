package eu.mapleconsulting.adminnotes;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import eu.mapleconsulting.adminnotes.commands.*;
import eu.mapleconsulting.adminnotes.util.UpdateNotifier;
import eu.mapleconsulting.adminnotes.util.Utils;

public class AdminNotes extends JavaPlugin{

	private CommandHandler commandHandler;
	private ConfigManager configManager;
	private Map <String, String>toBeConfirmed;
	private EmailManager emailManager;
	private final double currentVersion=1.19;
	private final String pluginName="AdminNotes";

	public void onEnable() {
		configManager=new ConfigManager();
		emailManager=new EmailManager(this);
		toBeConfirmed=new HashMap<>();
		addCommands();
		registerTabCompleter();
		registerEvents();
		Utils.printConsoleMsg("AdminNotes booted!");	
	}

	public void onDisable(){
		Utils.printConsoleMsg("AdminNotes stopped!");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return commandHandler.deploy(sender, cmd, label, args);
	}

	private void addCommands(){
		commandHandler=new CommandHandler();
		commandHandler.addCommand(new CreateRecordCommand(this));
		commandHandler.addCommand(new LastByAuthorCommand(this));
		commandHandler.addCommand(new SeeLastNotesCommand(this));
		commandHandler.addCommand(new WriteNoteCommand(this));
		commandHandler.addCommand(new CleanCommand(this));
		commandHandler.addCommand(new AddMyEmailCommand(this));
		commandHandler.addCommand(new SendNoteByMailCommand(this));
		commandHandler.addCommand(new SendRecordByMailCommand(this));
		commandHandler.addCommand(new DisplayMailCommand(this));
		commandHandler.addCommand(new FlagExpiredCommand(this));
		commandHandler.addCommand(new ReloadCommand(this));
		commandHandler.addCommand(new DeleteNoteCommand(this));
		commandHandler.addCommand(new FlagAsNotExpiredCommand(this));
		commandHandler.addCommand(new DeleteNoteCommand(this));
		commandHandler.addCommand(new NoteAbortCommand(this));
		commandHandler.addCommand(new NoteConfirmCommand(this));
		commandHandler.addCommand(new HelpCommand(this));
	}	

	public CommandHandler getCommandHandler() {
		return commandHandler;
	}


	public ConfigManager getConfigManager() {
		return configManager;
	}


	public EmailManager getEmailManager() {
		return emailManager;
	}


	private void registerTabCompleter(){
		getCommand("note").setTabCompleter(new NoteTabCompleter(this));
	}


	private void registerEvents(){
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
	}

	public Map<String, String> getToBeConfirmed() {
		return toBeConfirmed;
	}
	
	public synchronized boolean removeExecutor(Player executor){
		toBeConfirmed.remove(executor.getUniqueId().toString());
		return true;
	}

	public double getVersion() {
		return currentVersion;
	}

	public String getPluginName() {
		return pluginName;
	}

}