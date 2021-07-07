package eu.mapleconsulting.adminnotes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import eu.mapleconsulting.adminnotes.util.Utils;

public class ConfigManager {
	
	private final String CONFIG_NAME="config.yml";
	
	private final int DEFAULT_NOTE_LENGTH=50;
	private final int DEFAULT_NOTE_EXPIRING_TIME=60;
	private final int DEFAULT_RECORD_NOTE_EXPIRING_TIME=7;
	private final int DEFAULT_PORT=59666;
	private final String DEFAULT_MAIL=""; //"darkworld.infobot@gmail.com";
	private final String DEFAULT_PASSWORD=""; //"forgottenmerda";
	private final String DEFAULT_UPDATE_SERVER=""; //"devilkurt.homepc.it";
	private final boolean DEFAULT_LOOK_FOR_UPDATES=false;
	private final String DEFAULT_LANG="en";
	
	//default yml paths
	private final String DEFAULT_NOTE_LENGTH_PATH="max_note_length_chars";
	private final String DEFAULT_NOTE_EXPIRING_TIME_PATH="note_expiring_time_days";
	private final String DEFAULT_RECORD_NOTE_EXPIRING_TIME_PATH="records_expire_time_days";
	private final String DEFAULT_UPDATE_SERVER_PATH="update_server_addr";
	private final String DEFAULT_PORT_PATH="update_server_port";
	private final String DEFAULT_LOOK_FOR_UPDATES_PATH="look_for_updates";
	private final String MAIL_ACCOUNT_PATH = "mail_account_name";
	private final String MAIL_PASSWORD_PATH = "mail_account_password";
	private final String NOTIFY_PLAYER_PATH = "notify_player";
	private final String NOTIFY_STAFF_PATH = "notify_staff";
	private final String NOTES_THRESHOLD_NOTIFY_PATH = "notes_per_notify_threshold";
	
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
		String rootPlugin=path.getParent().toString();
		configFolderPath=rootPlugin+File.separator+"AdminNotes" + File.separator;
		File configFolder = new File (configFolderPath);
		if(!configFolder.exists()){
			configFolder.mkdirs();
		}
		notesFolderPath=rootPlugin+File.separator+"AdminNotes/Notes" + File.separator;
		notesRecordFolderPath=rootPlugin+File.separator+"AdminNotes/NotesRecords" + File.separator;
	}

	private synchronized void loadConfig(){
		configFile = new File(configFolderPath + CONFIG_NAME);
		customConfig = YamlConfiguration.loadConfiguration(configFile);
		if(!configFile.exists()){
			try {
				createDefaultCfg();
			} catch (IOException e) {
				Utils.printConsoleMsg("Error during the write of configuration file, check write permissions on the folder!");
			}
		}
		try {
			readConfigData();

		} catch (FileNotFoundException e) {
			Utils.printConsoleMsg("No configuration file found!");
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
		Utils.printConsoleMsg("Creating configuration file for first plugin boot...");
		customConfig.set(DEFAULT_NOTE_LENGTH_PATH, DEFAULT_NOTE_LENGTH);
		customConfig.set(DEFAULT_NOTE_EXPIRING_TIME_PATH,DEFAULT_NOTE_EXPIRING_TIME);
		customConfig.set(DEFAULT_RECORD_NOTE_EXPIRING_TIME_PATH,DEFAULT_RECORD_NOTE_EXPIRING_TIME);
		customConfig.set(DEFAULT_LOOK_FOR_UPDATES_PATH, DEFAULT_LOOK_FOR_UPDATES);
		customConfig.set(DEFAULT_UPDATE_SERVER_PATH,DEFAULT_UPDATE_SERVER);
		customConfig.set(DEFAULT_PORT_PATH, DEFAULT_PORT);
		customConfig.set(MAIL_ACCOUNT_PATH, DEFAULT_MAIL);
		customConfig.set(MAIL_PASSWORD_PATH, DEFAULT_PASSWORD);
		//customConfig.set("server_smtp", "smtp.gmail.com:587");
		customConfig.set(NOTIFY_PLAYER_PATH, true);
		customConfig.set(NOTIFY_STAFF_PATH, true);
		customConfig.set(NOTES_THRESHOLD_NOTIFY_PATH, 2);
		customConfig.save(configFile);
		Utils.printConsoleMsg("Configuration file correctly created!");
	}

	private synchronized void readConfigData() throws FileNotFoundException{
		maxNoteLength=customConfig.getInt(DEFAULT_NOTE_LENGTH_PATH);	
		notesExpiringTime=customConfig.getInt(DEFAULT_NOTE_EXPIRING_TIME_PATH);
		notesRecordExpireTime=customConfig.getInt(DEFAULT_RECORD_NOTE_EXPIRING_TIME_PATH);
		lookForUpdates=customConfig.getBoolean(DEFAULT_LOOK_FOR_UPDATES_PATH);
		updateServer=customConfig.getString(DEFAULT_UPDATE_SERVER_PATH);
		port=customConfig.getInt(DEFAULT_PORT_PATH);
		darkworldMail=customConfig.getString(MAIL_ACCOUNT_PATH);	
		mailPassword=customConfig.getString(MAIL_PASSWORD_PATH);
		smtpAddress="smtp.gmail.com:587";
		notifyPlayer=customConfig.getBoolean(NOTIFY_PLAYER_PATH);
		notifyStaff=customConfig.getBoolean(NOTIFY_STAFF_PATH);
		warnings=customConfig.getInt(NOTES_THRESHOLD_NOTIFY_PATH);
		Utils.printConsoleMsg("Configuration file correctly read");
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
