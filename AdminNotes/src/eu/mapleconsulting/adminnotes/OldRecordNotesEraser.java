package eu.mapleconsulting.adminnotes;

import java.io.File;
import java.io.IOException;

import org.bukkit.scheduler.BukkitRunnable;

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
			System.out.println("Cercando registri di note scaduti da cancellare...");
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
		System.out.println("[DevilNotes] "+filesDeleted+" vecchi registri sono stati cancellati!");
	}

	public long getHOURFACTOR() {
		return HOURFACTOR;
	}
}