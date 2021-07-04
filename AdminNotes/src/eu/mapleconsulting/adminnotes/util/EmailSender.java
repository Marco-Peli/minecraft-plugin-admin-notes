package eu.mapleconsulting.adminnotes.util;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mapleconsulting.adminnotes.ConfigManager;
import eu.mapleconsulting.adminnotes.EmailManager;

public class EmailSender extends BukkitRunnable {
	
	private EmailManager emailManager;
	private ConfigManager configManager;
	private Player executor;
	private String args[];
	private String filesFolder;
	private String senderName;
	private String subject;
	private String body;
	private String smtpAddress;
		
	public EmailSender(EmailManager emailManager, ConfigManager configManager,
			Player executor, String[] args, String filesFolder,
			String senderName, String subject, String body, String smtpAddress) {
		super();
		this.emailManager = emailManager;
		this.configManager = configManager;
		this.executor = executor;
		this.args = args;
		this.filesFolder = filesFolder;
		this.senderName = senderName;
		this.subject = subject;
		this.body = body;
		this.smtpAddress=smtpAddress;
	}


	@Override
	public void run(){
		try {
			
			String playerTargetUUID=UUIDFetcher.getUUIDFromName(args[1]).toLowerCase();
			String noteToBeSentPath=filesFolder+playerTargetUUID +".yml";
			File noteToBeSent=new File(noteToBeSentPath);
		
			String playerMail=emailManager.getPlayerEmail(executor);
			if(!noteToBeSent.exists()) {
				executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+
						"Il file di note sul giocatore richiesto non esiste");
				return;
			}
			//creazione mail
			String darkMail=configManager.getDarkworldMail();
			String darkPass=configManager.getMailPassword();
			
			SMTP.Email email = SMTP.createEmptyEmail();
			email.add("Content-Type", "text/html");
			email.from(senderName, darkMail); 
			email.to("Me", playerMail); 
			email.subject(subject); 
			email.body(body);
			SMTP.addFileAttachment(email, noteToBeSent);
			
			executor.sendMessage(ChatColor.GOLD+"Sara' ora inviato alla mail " + ChatColor.WHITE+playerMail);
			SMTP.sendEmail(smtpAddress, darkMail, 
					darkPass, email, false); 
			executor.sendMessage(ChatColor.GOLD+"File inviato!");
			
		} catch (Exception e) {
			executor.sendMessage(ChatColor.WHITE+"[DevilNotes] "+ChatColor.DARK_RED+"Errore durante l'invio");
		} 
	}
}
