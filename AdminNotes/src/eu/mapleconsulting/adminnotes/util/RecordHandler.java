package eu.mapleconsulting.adminnotes.util;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class RecordHandler extends FileHandler{
		
	public RecordHandler(File toBeWritten){
		super(toBeWritten);
	}
	

	public void createNewRecord(String targetUUID, File[] noteFiles){
		String UUIDPath="notes"+"."+targetUUID;	
		createNotes(UUIDPath, noteFiles);	
	}
	
	private void createNotes(String UUIDPath, File noteFiles[]){
		for(File f: noteFiles){
			FileConfiguration noteHandler=YamlConfiguration.loadConfiguration(f);
			
			try{
				Set<String> authorsNickSet; 
				authorsNickSet=noteHandler.getConfigurationSection(UUIDPath).getKeys(false);		
				for(String authorNick: authorsNickSet){	
					
					String tmpAuthorNickPath=UUIDPath+"."+authorNick; 				
					Set<String> targetPlayerNicks;
					targetPlayerNicks=noteHandler.getConfigurationSection(tmpAuthorNickPath)
							.getKeys(false);	
					
					for(String targetPlayerNick: targetPlayerNicks){
						
					String tmpIntestation1="About "+targetPlayerNick+", on ";
					String targetPlayerPath=tmpAuthorNickPath+"."+targetPlayerNick;
					Set<String> dateSet; 
					dateSet=noteHandler.getConfigurationSection(targetPlayerPath).getKeys(false);
					
					for(String date: dateSet){
						String datePath=targetPlayerPath +"."+date;
						String tmpIntestation2=tmpIntestation1+date;
						Set<String> preciseDateSet;
						preciseDateSet=noteHandler.getConfigurationSection(datePath).getKeys(false);	
						for(String preciseDate: preciseDateSet){			
							String preciseDatePath=datePath+"."+preciseDate;
							String finalIntestation=tmpIntestation2+" "+preciseDate+", writes: ";
							List<String> partialNotes=noteHandler.getStringList(preciseDatePath);
							toBeWrittenHandler.set(preciseDatePath, partialNotes);
							addNotes(finalIntestation,partialNotes);			
						}
					}
					}
				}
			}catch(NullPointerException e){
				
			}
		}
	}
	
	
	
	
}
