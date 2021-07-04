package eu.mapleconsulting.adminnotes.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class NoteHandler extends FileHandler{
	
	private final String expiredPath="nota_scaduta";
	
	public NoteHandler(File toBeWritten){
		super(toBeWritten);
	}
	
	public synchronized void writeNewNote(Player executor, String args[]){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();	
		DateFormat preciseDateFormat = new SimpleDateFormat("HH:mm");
		Date preciseDate = new Date();		
		List<String> availableNotes;		
		String targetPlayerNick=args[1];
		String dateString=dateFormat.format(date);
		String preciseDateString=preciseDateFormat.format(preciseDate);

		String notePath="notes."
				+executor.getUniqueId().toString()+"."
				+executor.getName()+"."+targetPlayerNick+"."
				+dateString+"."+preciseDateString;		
		availableNotes=toBeWrittenHandler.getStringList(notePath);	
		String realNote="["+executor.getWorld().getName().toUpperCase()+"]"+" "+FileUtilities.argumentsAsString(2,(args.length),args).trim();		
		availableNotes.add(realNote);	
		toBeWrittenHandler.set(notePath, availableNotes);
		toBeWrittenHandler.set(expiredPath,false);

		
		try {
			toBeWrittenHandler.save(toBeWritten);
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.GOLD+"Nota creata sul giocatore " + ChatColor.WHITE+ args[1]);
		} catch (IOException e) {
			
		}

	}
	
	public synchronized boolean seeLast(){
		try{
			Set<String> authorUUIDSet; 
			authorUUIDSet=toBeWrittenHandler.getConfigurationSection("notes").getKeys(false);
		
			for(String uuid: authorUUIDSet){
				
				String tmpUUIDPath="notes"+"."+uuid;
				Set<String> authorsNickSet; 
				authorsNickSet=toBeWrittenHandler.getConfigurationSection(tmpUUIDPath).getKeys(false);
				for(String authorNick: authorsNickSet){
					
					String tmpAuthorNickPath=tmpUUIDPath+"."+authorNick; 
					String tmpintestation1=ChatColor.RED+authorNick+ChatColor.WHITE+", il ";
					Set<String> targetPlayerNicks;
					targetPlayerNicks=toBeWrittenHandler.
							getConfigurationSection(tmpAuthorNickPath).getKeys(false);
					
					for(String targetPlayerNick: targetPlayerNicks){
						
						String targetPlayerPath=tmpAuthorNickPath+"."+targetPlayerNick;
						Set<String> dateSet; 
						dateSet=toBeWrittenHandler.
								getConfigurationSection(targetPlayerPath).getKeys(false);
						
						for(String date: dateSet){
							
							String datePath=targetPlayerPath +"."+date;
							String tmpIntestation2=tmpintestation1+date;
							Set<String> preciseDateSet; 
							preciseDateSet=toBeWrittenHandler.
									getConfigurationSection(datePath).getKeys(false);
							
							for(String preciseDate: preciseDateSet){
								
								String preciseDatePath=datePath+"."+preciseDate;
								String finalIntestation=tmpIntestation2+" "+preciseDate+", scrive: ";
								List<String> partialNotes=toBeWrittenHandler.getStringList(preciseDatePath);
								addNotes(finalIntestation,partialNotes);
							}
						}
					}
				}
			}
		} catch(NullPointerException e){
			
		}
		return true;
	}
	
	public synchronized void writeAuthors(){
		
		Set<String> authorUUIDSet; 
		authorUUIDSet=toBeWrittenHandler.getConfigurationSection("notes").getKeys(false);
		
		for(String uuid: authorUUIDSet){
			
			String tmpUUIDPath="notes"+"."+uuid;
			Set<String> authorsNickSet; 
			authorsNickSet=toBeWrittenHandler.getConfigurationSection(tmpUUIDPath).getKeys(false);
			String authors="";
			for(String author: authorsNickSet){
				authors= author+" ";
			}
			notesToBeDisplayed.add(uuid+ ": "+ authors);
		}
	}
	
	public synchronized boolean isExpired(){
		return toBeWrittenHandler.getBoolean(expiredPath);
	}

}
