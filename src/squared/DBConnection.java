package squared;

import com.db4o.ObjectContainer;
import com.db4o.Db4oEmbedded;
import com.db4o.ext.*;
import com.db4o.reflect.*;

public class DBConnection {
	private static DBConnection instance = null; 
	private static ObjectContainer db;
	private static String containerPath;
	
	private DBConnection() {
	}
	
	public static DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}
	
	public void open(String path) throws DatabaseFileLockedException, Db4oException {
		containerPath = path;
		System.out.println("[squared] OPEN object container: "+containerPath);
		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), path);
		
		String msg = "";
	    // do something with db4o
        ReflectClass[] knownClasses = db.ext().knownClasses();
        for (ReflectClass cls : knownClasses) {
            ReflectField[] fields = cls.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
            	System.out.println("fields: "+fields[i].getName());
            	msg += fields[i].getName() + ", ";
			}
        }
	}
	
	public void close() {
		System.out.println("[squared] CLOSE object container: "+containerPath);
		db.close();
		
		db = null;
		containerPath = null;
	}

}
