package eu.mapleconsulting.adminnotes;

import java.io.File;
import java.io.IOException;

import org.bukkit.scheduler.BukkitRunnable;

import eu.mapleconsulting.adminnotes.util.Utils;

public class OldRecordNotesEraser extends BukkitRunnable {

	private long expiringTime;
	private File notesRecordFolder;
	private final long DAYFACTOR=1728000;
	private final long HOURFACTOR=72000;

	public OldRecordNotesEraser(int expiringTime, File notesRecordFolder) {
		this.expiringTime = expiringTime;
		this.notesRecordFolder=notesRecordFolder;
	}

	public void run() {
		Utils.printConsoleMsg("Looking for expired records files to be deleted...");
		try {
			eraseOldRecordNotes(notesRecordFolder.listFiles());
		} catch (IOException ioex){
			ioex.printStackTrace();
		}
	}

	private synchronized void eraseOldRecordNotes(File[] notesList) throws IOException{
		int filesDeleted=0;
		for(File f: notesList){
			if((System.currentTimeMillis()-f.lastModified())>=expiringTime*DAYFACTOR){
				f.delete();
				filesDeleted++;
			}
		}
		Utils.printConsoleMsg(filesDeleted+" expired record files have been deleted!");
	}

	public long getHOURFACTOR() {
		return HOURFACTOR;
	}
}