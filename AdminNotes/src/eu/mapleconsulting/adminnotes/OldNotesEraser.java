package eu.mapleconsulting.adminnotes;

import java.io.File;
import java.io.IOException;

import org.bukkit.scheduler.BukkitRunnable;

import eu.mapleconsulting.adminnotes.util.NoteHandler;
import eu.mapleconsulting.adminnotes.util.Utils;

public class OldNotesEraser extends BukkitRunnable{

	private final long expiringTime;
	private final File notesFolder;
	private final long DAYFACTOR=1728000;
	private final long HOURFACTOR=72000;

	public OldNotesEraser(int expiringTime, 
			File notesFolder){
		this.expiringTime=expiringTime;
		this.notesFolder=notesFolder;
	}
	@Override
	public void run() {
			Utils.printConsoleMsg("Looking for expired notes files to deleted...");
			try {
				eraseOldNotes(notesFolder.listFiles());
			} catch (IOException ioex){
				ioex.printStackTrace();
			}
	}
	
	private void eraseOldNotes(File[] notesList) throws IOException{
		int filesDeleted=0;
		for(File f: notesList){
			NoteHandler noteToCheck= new NoteHandler(f);
			if(((System.currentTimeMillis()-f.lastModified())>expiringTime*DAYFACTOR)
					&& noteToCheck.isExpired()){
				f.delete();
				filesDeleted++;
			}
		}
		Utils.printConsoleMsg(filesDeleted+" expired notes files deleted!");
		
	}
	
	public long getHOURFACTOR() {
		return HOURFACTOR;
	}
}