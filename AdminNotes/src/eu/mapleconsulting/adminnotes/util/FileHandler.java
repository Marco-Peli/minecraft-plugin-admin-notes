package eu.mapleconsulting.adminnotes.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public abstract class FileHandler {

	protected List<String> notesToBeDisplayed;
	protected File toBeWritten;
	protected FileConfiguration toBeWrittenHandler;
	
	public FileHandler(File fileToBeWritten) {
		toBeWritten=fileToBeWritten;
		toBeWrittenHandler=YamlConfiguration.loadConfiguration(fileToBeWritten);
		notesToBeDisplayed=new ArrayList<>();
	}
		
	
	public boolean displayNotes(Player executor, int notesToDisplay,
			String authorName, String error, String message, String messageLowerCase){
		if(notesToBeDisplayed.size()==0){
			executor.sendMessage(ChatColor.DARK_RED+error+ authorName);
			return true;
		}
		try{
			int index=notesToBeDisplayed.size()-notesToDisplay;
			if(notesToDisplay>notesToBeDisplayed.size()) throw new NullPointerException();
			executor.sendMessage("ULTIME " + notesToDisplay
					+message + ChatColor.RED+ authorName+":");
			for(int i=index; i<notesToBeDisplayed.size();i++){
				int displayedIndex=i+1;
				executor.sendMessage(ChatColor.GOLD+
						""+displayedIndex+") "+ChatColor.WHITE+
							notesToBeDisplayed.get(i));
			}
			
			}catch(NullPointerException e){
				executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+
						"Hai inserito un range di ultime note non valido, sono presenti "+ChatColor.GOLD+""+
						+ notesToBeDisplayed.size()+ChatColor.DARK_RED+messageLowerCase+ ChatColor.GOLD+authorName);
			}
			return true;
	}
	
	public boolean notesWithAuthorAndTarget(Player executor, int notesToDisplay, String error,String author,String target){
		if(notesToBeDisplayed.size()==0){
			executor.sendMessage(ChatColor.DARK_RED+error+
					ChatColor.GOLD+author+ChatColor.DARK_RED
					+" su "+ChatColor.GOLD+target);
			return true;
		}
		try{
			int index=notesToBeDisplayed.size()-notesToDisplay;
			if(notesToDisplay>notesToBeDisplayed.size()) throw new NullPointerException();
			executor.sendMessage("ULTIME " + notesToDisplay
					+" SCRITTE DA " + ChatColor.RED+ author+" SU "+
						target+ChatColor.WHITE+":");
			for(int i=index; i<notesToBeDisplayed.size();i++){
				int displayedIndex=i+1;
				executor.sendMessage(ChatColor.GOLD+
						""+displayedIndex+") "+ChatColor.WHITE+
							notesToBeDisplayed.get(i));
			}
			
			}catch(NullPointerException e){
				executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+
						"Hai inserito un range di ultime note non valido, sono presenti "+ChatColor.GOLD+""+
						+ notesToBeDisplayed.size()+ChatColor.DARK_RED+" note scritte da "+
							ChatColor.GOLD+author+ChatColor.DARK_RED+" su "+ ChatColor.GOLD+target);
			}
			return true;
	}
	
	
	
	public boolean displayNotes(Player executor, String author, String error, 
			String message,String playerTarget){
		if(notesToBeDisplayed.size()==0){
			executor.sendMessage(ChatColor.DARK_RED+error+ author);
			return true;
		}
		int index=1;
		executor.sendMessage(message + ChatColor.RED+ author + playerTarget+":");
		for(String note: notesToBeDisplayed){
			executor.sendMessage(ChatColor.GOLD+""+index+") "+ChatColor.WHITE+note);
			index++;
		}
		return true;
	}
	
	public boolean saveFile(Player executor, String error, String author){
		if(notesToBeDisplayed.size()==0){
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+error+ author);
			return true;
		}
		try {
			toBeWrittenHandler.save(toBeWritten);
		} catch (IOException e) {
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Impossibile scrivere su file");
			return true;
		}
		executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.GOLD+"Registro di note scritto da "
				+ ChatColor.WHITE+ author+ChatColor.GOLD+" creato correttamente");
		return true;
	}
	
	public void addNotes(String intestation,List<String> partialNotes){
		for(String p: partialNotes){
			notesToBeDisplayed.add(intestation+ChatColor.GREEN+p);
		}
	}
	
	public boolean showAuthors(Player executor, String target){
		executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+"LISTA DI AUTORI SU " + ChatColor.RED+target+ChatColor.WHITE+":");
		int index=1;
		for(String authors: notesToBeDisplayed){
			executor.sendMessage(ChatColor.GOLD+""+index+") "+ChatColor.WHITE+authors);
			index++;
		}
		return true;
	}


	/**
	 * @return the toBeWrittenHandler
	 */
	public FileConfiguration getToBeWrittenHandler() {
		return toBeWrittenHandler;
	}


	/**
	 * @return the notesToBeDisplayed
	 */
	public List<String> getNotesToBeDisplayed() {
		return notesToBeDisplayed;
	}

	
}
