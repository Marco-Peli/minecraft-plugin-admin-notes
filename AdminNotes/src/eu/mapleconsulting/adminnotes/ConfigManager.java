package eu.mapleconsulting.adminnotes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	
	private final String CONFIG_NAME="config.yml";
	
	private final int DEFAULT_NOTE_LENGTH=50;
	private final int DEFAULT_NOTE_EXPIRING_TIME=60;
	private final int DEFAULT_RECORD_NOTE_EXPIRING_TIME=7;
	private final int DEFAULT_PORT=59666;
	private final String DEFAULT_MAIL="darkworld.infobot@gmail.com";
	private final String DEFAULT_PASSWORD="forgottenmerda";
	private final String DEFAULT_UPDATE_SERVER="devilkurt.homepc.it";
	private final boolean DEFAULT_LOOK_FOR_UPDATES=true;
	
	//default yml paths
	private final String DEFAULT_NOTE_LENGTH_PATH="max_lunghezza_nota_chars";
	private final String DEFAULT_NOTE_EXPIRING_TIME_PATH="scadenza_nota_giorni";
	private final String DEFAULT_RECORD_NOTE_EXPIRING_TIME_PATH="scadenza_registro_note_giorni";
	private final String DEFAULT_UPDATE_SERVER_PATH="server_update";
	private final String DEFAULT_PORT_PATH="porta_server_update";
	private final String DEFAULT_LOOK_FOR_UPDATES_PATH="ricerca_aggiornamenti";
	
	//variables
	private FileConfiguration customConfig;
	private int maxNoteLength;
	private int checkingTime;
	private int port;
	private int notesExpiringTime;
	private int notesRecordExpireTime;
	private File notesFolder;
	private File notesRecordFolder;
	private File configFile;
	private String configFolderPath;
	private String notesFolderPath;
	private String notesRecordFolderPath;
	private String darkworldMail;
	private String mailPassword;
	private String smtpAddress;
	private String updateServer;
	private int warnings;
	private boolean notifyPlayer;
	private boolean notifyStaff;
	private boolean lookForUpdates;
	private boolean updateAvailable=false;

	public ConfigManager(){
		createFolderPaths();
		loadConfig();
		createFolders();
	}

	private synchronized void createFolderPaths(){
		File util=new File("util.txt");
		Path path=Paths.get(util.getAbsolutePath());
		String rootPlugin=path.getParent().getParent().toString();
		configFolderPath=rootPlugin+File.separator+"DevilNotes" + File.separator;
		File configFolder = new File (configFolderPath);
		if(!configFolder.exists()){
			configFolder.mkdirs();
		}
		notesFolderPath=rootPlugin+File.separator+"DevilNotes/Notes" + File.separator;
		notesRecordFolderPath=rootPlugin+File.separator+"DevilNotes/NotesRecords" + File.separator;
	}

	private synchronized void loadConfig(){
		configFile = new File(configFolderPath + CONFIG_NAME);
		customConfig = YamlConfiguration.loadConfiguration(configFile);
		if(!configFile.exists()){
			try {
				createDefaultCfg();
			} catch (IOException e) {
				System.out.println("[DevilNotes] Impossibile creare il file di configurazione :/");
			}
		}
		try {
			readConfigData();

		} catch (FileNotFoundException e) {
			System.out.println("[DevilNotes] Nessun file di configurazione trovato :/");
		}
	}

	private synchronized void createFolders(){
		// Creiamo l'oggetto File
		notesFolder = new File(notesFolderPath);
		if(!notesFolder.exists()){
			notesFolder.mkdirs();
		}
		notesRecordFolder = new File(notesRecordFolderPath);
		if(!notesRecordFolder.exists()){
			notesRecordFolder.mkdirs();
		}
	}

	private synchronized void createDefaultCfg() throws IOException{
		System.out.print("Creando il file di configurazione per il primo avvio del plugin...");
		customConfig.set(DEFAULT_NOTE_LENGTH_PATH, DEFAULT_NOTE_LENGTH);
		customConfig.set(DEFAULT_NOTE_EXPIRING_TIME_PATH,DEFAULT_NOTE_EXPIRING_TIME);
		customConfig.set(DEFAULT_RECORD_NOTE_EXPIRING_TIME_PATH,DEFAULT_RECORD_NOTE_EXPIRING_TIME);
		customConfig.set(DEFAULT_LOOK_FOR_UPDATES_PATH, DEFAULT_LOOK_FOR_UPDATES);
		customConfig.set(DEFAULT_UPDATE_SERVER_PATH,DEFAULT_UPDATE_SERVER);
		customConfig.set(DEFAULT_PORT_PATH, DEFAULT_PORT);
		customConfig.set("darkworld_mail", DEFAULT_MAIL);
		customConfig.set("darkworld_mail_password", DEFAULT_PASSWORD);
		customConfig.set("server_smtp", "smtp.gmail.com:587");
		customConfig.set("notifica_giocatore", true);
		customConfig.set("notifica_staff", true);
		customConfig.set("numero_note_per_notifica", 2);
		customConfig.save(configFile);
		System.out.println("[DevilNotes] File di configurazione di default creato!");
	}

	private synchronized void readConfigData() throws FileNotFoundException{
		maxNoteLength=customConfig.getInt(DEFAULT_NOTE_LENGTH_PATH);	
		notesExpiringTime=customConfig.getInt(DEFAULT_NOTE_EXPIRING_TIME_PATH);
		notesRecordExpireTime=customConfig.getInt(DEFAULT_RECORD_NOTE_EXPIRING_TIME_PATH);
		lookForUpdates=customConfig.getBoolean(DEFAULT_LOOK_FOR_UPDATES_PATH);
		updateServer=customConfig.getString(DEFAULT_UPDATE_SERVER_PATH);
		port=customConfig.getInt(DEFAULT_PORT_PATH);
		darkworldMail=customConfig.getString("darkworld_mail");	
		mailPassword=customConfig.getString("darkworld_mail_password");
		smtpAddress=customConfig.getString("server_smtp");
		notifyPlayer=customConfig.getBoolean("notifica_giocatore");
		notifyStaff=customConfig.getBoolean("notifica_staff");
		warnings=customConfig.getInt("numero_note_per_notifica");
		System.out.println("[DevilNotes] File di configurazione correttamente caricato!");
	}
	/**
	 * @return the maxNoteLength
	 */
	public int getMaxNoteLength() {
		return maxNoteLength;
	}

	/**
	 * @return the checkingTime
	 */
	public int getCheckingTime() {
		return checkingTime;
	}

	/**
	 * @return the notesFolderPath
	 */
	public String getNotesFolderPath() {
		return notesFolderPath;
	}

	/**
	 * @return the notesExpiringTime
	 */
	public int getNotesExpiringTime() {
		return notesExpiringTime;
	}

	/**
	 * @return the notesRecordFolderPath
	 */
	public String getNotesRecordFolderPath() {
		return notesRecordFolderPath;
	}

	/**
	 * @return the notesRecordExpireTime
	 */
	public int getNotesRecordExpireTime() {
		return notesRecordExpireTime;
	}
	/**
	 * @return the configFolderPath
	 */
	public String getConfigFolderPath() {
		return configFolderPath;
	}

	/**
	 * @return the notesFolder
	 */
	public File getNotesFolder() {
		return notesFolder;
	}

	/**
	 * @return the notesRecordFolder
	 */
	public File getNotesRecordFolder() {
		return notesRecordFolder;
	}

	/**
	 * @return the darkworldMail
	 */
	public String getDarkworldMail() {
		return darkworldMail;
	}

	/**
	 * @return the mailPassword
	 */
	public String getMailPassword() {
		return mailPassword;
	}

	/**
	 * @return the smtpAddress
	 */
	public String getSmtpAddress() {
		return smtpAddress;
	}

	/**
	 * @return the warnings
	 */
	public int getWarnings() {
		return warnings;
	}

	/**
	 * @return the notifyPlayer
	 */
	public boolean isNotifyPlayer() {
		return notifyPlayer;
	}

	/**
	 * @return the notifyStaff
	 */
	public boolean isNotifyStaff() {
		return notifyStaff;
	}

	public String getUpdateServer() {
		return updateServer;
	}

	public boolean isLookForUpdates() {
		return lookForUpdates;
	}

	public int getPort() {
		return port;
	}

	public boolean isUpdateAvailable() {
		return updateAvailable;
	}

	public void setUpdateAvailable(boolean updateAvailable) {
		this.updateAvailable = updateAvailable;
	}
}
