package eu.mapleconsulting.adminnotes.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;
import eu.mapleconsulting.adminnotes.exceptions.CommandFormatException;

public class HelpCommand extends CommandPattern {

	private List<CommandInterface> availableCommands;
	private final int commandsPerPage=7;
	private int pageNumber;
	
	public HelpCommand(AdminNotes plugin) {
		super("note", "help");
		availableCommands=plugin.getCommandHandler().getCommands();
		setDescription("Displays the commands of AdminNotes");
        setUsage("/note help #page[2 pages]");
        setArgumentRange(1, 2);
        setIdentifier("help");
        setPermission("note.command.help");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		try{
			if(args.length==1){
				pageNumber=1;
			}else{
			pageNumber=Integer.parseInt(args[1].trim());
			}
			if(!(pageNumber==1||pageNumber==2)){
				executor.sendMessage(ChatColor.DARK_RED+
						"Invalid page number.");
				return true;
			}
			executor.sendMessage(ChatColor.RED+"ADMINNOTES HELP PAGE NUMBER " +
					ChatColor.WHITE+""+pageNumber +""+ChatColor.GOLD+":");
			executor.sendMessage("-------------------------------");
			
			return displayHelpCommand(executor);
		}catch(NumberFormatException e){
			executor.sendMessage(ChatColor.DARK_RED+
					"Page number has to be an integer");
		}
		return true;
		
	
	}

	private boolean displayHelpCommand(Player executor){
		if(pageNumber==1){
			for(int i=0; i<commandsPerPage;i++){
				if(!excludedCommand(i)){
					executor.sendMessage(i+1+") "+ChatColor.GOLD+availableCommands.get(i).getUsage()
							+ " "+ ChatColor.WHITE+availableCommands.get(i).getDescription());
				}
			}
			}else if(pageNumber==2){
				for(int i=commandsPerPage; i<availableCommands.size();i++){
					if(!excludedCommand(i)){
						executor.sendMessage(i+1+") "+ChatColor.GOLD+availableCommands.get(i).getUsage()
								+ " "+ ChatColor.WHITE+availableCommands.get(i).getDescription());
					}
				}
			}
		return true;
	}
	
	private boolean excludedCommand(int i){
		return (availableCommands.get(i).getIdentifier().equalsIgnoreCase("help") || 
				availableCommands.get(i).getIdentifier().equalsIgnoreCase("confirm") ||
				availableCommands.get(i).getIdentifier().equalsIgnoreCase("abort"));
	}

	@Override
	public void checkArgs(Player executor, String[] args)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		
	}
	
}
