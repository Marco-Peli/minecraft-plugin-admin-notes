package eu.mapleconsulting.adminnotes.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.adminnotes.AdminNotes;

public class UpdateNotifier extends Thread {

	private double currentVersion;
	private String pluginName;
	private String updateServer;
	private int port;
	private Socket pluginSocket;
	private BufferedReader in;
	private PrintWriter out;
	private AdminNotes plugin;
	
	public UpdateNotifier(AdminNotes plugin) {
		this.plugin=plugin;
		this.currentVersion=plugin.getVersion();
		this.pluginName=plugin.getPluginName();
		this.updateServer=plugin.getConfigManager().getUpdateServer();
		this.port=plugin.getConfigManager().getPort();
	}

	@Override
	public void run(){	
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}	
		try {
			estabilishConnection();
			checkForUpdates();
		} catch (UnknownHostException e) {
			Utils.printConsoleMsg("Update server address invalid");
		} catch (IOException e) {
			System.out.println("[DevilNotes] Errore durante la connessione al server update.");
		}
	    try {
			Thread.sleep(86400000);
		} catch (InterruptedException e) {
			
		}
	}
	
	private void estabilishConnection() throws UnknownHostException, IOException{
		System.out.println("[DevilNotes] Ricerca nuove versioni di "+ pluginName+"...");
		pluginSocket = new Socket(updateServer, port);
		System.out.println("[DevilNotes] Connesso, ricerca nuove versioni di "+ pluginName+"...");
		in =new BufferedReader(new InputStreamReader(pluginSocket.getInputStream()));
		out=new PrintWriter(pluginSocket.getOutputStream(), true);
		out.println(pluginName);
	}
	
	private void checkForUpdates() throws NumberFormatException, IOException{
		String line;
		double updatedVersion;
		while(!((line=in.readLine()).equals("END"))){
			updatedVersion=Double.parseDouble(line);
			if(updatedVersion>currentVersion){
				for(Player p: Bukkit.getServer().getOnlinePlayers()){
					if(p.hasPermission("note.command.update")){
						p.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ ChatColor.GOLD+"Nuova versione di " 
								+ ChatColor.WHITE+ pluginName + ChatColor.GOLD+ " disponibile!");
						p.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ ChatColor.GOLD+ "Versione corrente: "+
								ChatColor.AQUA+ currentVersion + ChatColor.GOLD+ ", versione aggiornata: "+
								ChatColor.AQUA+ updatedVersion);
					}
				}
				plugin.getConfigManager().setUpdateAvailable(true);
				System.out.println("[DevilNotes] Nuova versione " + updatedVersion+" disponibile!");
			}else{
				System.out.println("[DevilNotes] E' gia' installata l'ultima versione di  " + pluginName);
			}
			out.println("OK");
		}
	}

}
