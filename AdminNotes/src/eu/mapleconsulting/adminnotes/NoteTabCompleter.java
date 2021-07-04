package eu.mapleconsulting.adminnotes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import eu.mapleconsulting.adminnotes.commands.CommandInterface;


public class NoteTabCompleter implements TabCompleter {

	private AdminNotes plugin;
	
	public NoteTabCompleter(AdminNotes plugin) {
		this.plugin = plugin;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1,
			String arg2, String[] arg3) {
		if(arg3.length==1){
			List<String> completers=new ArrayList<>();
			for(CommandInterface cmd: plugin.getCommandHandler().getCommands()){
				if(cmd.getIdentifier().toLowerCase().startsWith(arg3[0].toLowerCase()) && arg0.hasPermission(cmd.getPermission())){
					completers.add(cmd.getIdentifier());		
				}
			}
			return completers;
		}else if(arg3.length==3){
			List<String> completers=new ArrayList<>();
			if("authors".toLowerCase().startsWith(arg3[2].toLowerCase())&&
					arg3[0].equalsIgnoreCase("seelast")){
				completers.add("authors");
				return completers;
			}
			return null;
		}
		return null;	
	}

}