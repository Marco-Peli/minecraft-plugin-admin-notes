package eu.mapleconsulting.adminnotes;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import eu.mapleconsulting.adminnotes.exceptions.EmailNotFoundException;
import eu.mapleconsulting.adminnotes.util.Utils;

public class EmailManager {
	private File emailRegistry;
	private final String REGISTRY_NAME="EmailRegistry.yml";
	private FileConfiguration emailRegistryManager;
	private Map<String, String> emailsAndNicks;
	
	public EmailManager(AdminNotes plugin) {
		this.emailRegistry=new File(plugin.getConfigManager().getConfigFolderPath()+ REGISTRY_NAME);
		emailRegistryManager=YamlConfiguration.loadConfiguration(emailRegistry);
		
		try {
			createEmailRegistry();	
			loadEmailRegistry();	
		} catch (IOException e) {
			Utils.printConsoleMsg("Registro mail vuoto");
		}	
		
	}

	private void createEmailRegistry() throws IOException{
		if(!emailRegistry.exists()){
			emailRegistry.createNewFile();
			emailRegistryManager.set("emails."+"UUID", "email");
			emailRegistryManager.save(emailRegistry);
			Utils.printConsoleMsg("Created email database file for first boot");
		}
	}
	
	public void addEmail(Player player, String email) throws IOException {
		emailRegistryManager.set("emails."+player.getUniqueId().toString(), email);
		emailsAndNicks.put(player.getUniqueId().toString(),email);
		emailRegistryManager.save(emailRegistry);
	}
	
	public void deleteMail(Player player) throws EmailNotFoundException, IOException{
		if(emailsAndNicks.containsKey(player.getUniqueId().toString())){
		emailRegistryManager.set("emails."+player.getUniqueId().toString(), null);
		emailRegistryManager.save(emailRegistry);
		emailsAndNicks.put(player.getUniqueId().toString(),null);
		}else{
			throw new EmailNotFoundException();
		}
	}
	
	private void loadEmailRegistry(){
		emailsAndNicks=new LinkedHashMap<>();
		Set<String> uuidList=new HashSet<String>(); 
		uuidList=emailRegistryManager.getConfigurationSection("emails.").getKeys(false);
		for(String uuid: uuidList){
			String emailPath="emails."+uuid;
			String email=emailRegistryManager.getString(emailPath);
			emailsAndNicks.put(uuid,email);
		}
	}
	
	public String getPlayerEmail(Player player) throws EmailNotFoundException{
		if(emailsAndNicks.get(player.getUniqueId().toString())==null) throw new EmailNotFoundException();
		else return emailsAndNicks.get(player.getUniqueId().toString());
	}
}
